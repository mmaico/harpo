package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import io.ep2p.kademlia.protocol.message.PongKademliaMessage
import java.io.Serializable
import java.math.BigDecimal
import java.math.BigInteger

class Ping(private val repository: OperationsRepository = OperationsRepository()): Message {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        receiver: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT> {

        val answer = repository.ping(receiver, self)

        val response = PongKademliaMessage<BigInteger, ConnectionInfoImpl>()
        response?.node = receiver
        response?.isAlive = answer.isAlive

        return response as KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT>
    }

}