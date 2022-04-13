package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigDecimal

class Closest(private val repository: OperationsRepository): Message {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        receiver: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT> {

        val closest = this.repository.closest(receiver, self)

        return
    }

}