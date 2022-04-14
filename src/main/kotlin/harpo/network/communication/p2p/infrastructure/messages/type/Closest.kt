package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.infrastructure.DateHelper
import harpo.infrastructure.DateHelper.now
import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import harpo.network.communication.p2p.infrastructure.kademlia.ExternalNode
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigDecimal
import java.util.*
import io.ep2p.kademlia.protocol.message.FindNodeResponseMessage
import java.math.BigInteger

class Closest(private val repository: OperationsRepository): Message {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        receiver: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT> {

        val closest = this.repository.closest(receiver, self)
        val externalNodes = closest.parallelStream().map {
            ExternalNode(
                _connectionInfo = ConnectionInfoImpl(it.contact.ip, it.contact.port),
                _id = it.id.toBigInteger(),
                _lastSeen = now()
            )
        }

        val responseMessage = FindNodeResponseMessage<BigInteger, ConnectionInfoImpl>()
        responseMessage.

        TODO()
    }

}