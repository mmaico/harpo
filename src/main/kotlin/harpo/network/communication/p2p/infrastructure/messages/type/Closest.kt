package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.infrastructure.DateHelper.now
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.kademlia.ExternalNode
import io.ep2p.kademlia.model.FindNodeAnswer
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.node.external.BigIntegerExternalNode
import io.ep2p.kademlia.protocol.message.FindNodeResponseMessage
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigInteger

class Closest(private val repository: OperationsRepository = OperationsRepository()) : Message {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        receiver: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT> {

        val closest = this.repository.closest(receiver, self)

        val nodeAnswer = FindNodeAnswer<BigInteger, ConnectionInfoImpl>(self?.id)

        closest.parallelStream().forEach {
            val externalNode = ExternalNode(
                _connectionInfo = ConnectionInfoImpl(it.contact.ip, it.contact.port),
                _id = it.id.toBigInteger(),
                _lastSeen = now()
            )
            nodeAnswer.add(BigIntegerExternalNode(externalNode, self?.id!!.xor(externalNode.id)))
        }

        val responseMessage = FindNodeResponseMessage<BigInteger, ConnectionInfoImpl>()
        responseMessage.data = nodeAnswer

        return responseMessage as KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT>
    }
}
