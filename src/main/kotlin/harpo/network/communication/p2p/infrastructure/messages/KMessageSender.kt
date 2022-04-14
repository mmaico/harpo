package harpo.network.communication.p2p.infrastructure.messages

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.messages.type.Type
import harpo.network.communication.p2p.infrastructure.messages.type.Type.valueOf
import io.ep2p.kademlia.connection.MessageSender
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigDecimal
import java.net.Proxy

class KMessageSender: MessageSender<BigDecimal, ConnectionInfoImpl> {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        receiver: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, INPUT> {

        valueOf(message?.type!!)
        //use the grpc to send the message to external node

        TODO("Not yet implemented")
    }

    override fun <O : Serializable?> sendAsyncMessage(
        api: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        p1: Node<BigDecimal, ConnectionInfoImpl>?,
        p2: KademliaMessage<BigDecimal, ConnectionInfoImpl, O>?) {
        TODO("Not supported yet")
    }


}