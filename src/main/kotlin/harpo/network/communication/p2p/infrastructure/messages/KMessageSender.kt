package harpo.network.communication.p2p.infrastructure

import io.ep2p.kademlia.connection.MessageSender
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigDecimal

class KMessageSender: MessageSender<BigDecimal, ConnectionInfoImpl> {

    override fun <I : Serializable?, O : Serializable?> sendMessage(
        api: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?,
        external: Node<BigDecimal, ConnectionInfoImpl>?,
        message: KademliaMessage<BigDecimal, ConnectionInfoImpl, O>?
    ): KademliaMessage<BigDecimal, ConnectionInfoImpl, I> {

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