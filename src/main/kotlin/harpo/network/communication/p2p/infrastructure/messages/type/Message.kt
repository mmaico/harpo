package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigDecimal

interface Message {

    fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        api: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        external: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT>

}

