package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.KademliaServiceGrpc
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.client.ChannelPool
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import io.ep2p.kademlia.protocol.message.PongKademliaMessage
import java.io.Serializable
import java.math.BigDecimal
import harpo.network.communication.p2p.Contact as ContactGRPC
import harpo.network.communication.p2p.Node as NodeGRPC

class Ping(private val repository: OperationsRepository): Message {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        receiver: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT> {

        val answer = repository.ping(receiver, self)

        val response = PongKademliaMessage<BigDecimal, ConnectionInfoImpl>()
        response.node = receiver
        response.isAlive = answer.isAlive

        return response as KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT>
    }

}