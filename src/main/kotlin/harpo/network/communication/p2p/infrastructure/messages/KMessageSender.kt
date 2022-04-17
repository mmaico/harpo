package harpo.network.communication.p2p.infrastructure.messages

import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.messages.type.Type
import io.ep2p.kademlia.connection.MessageSender
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigInteger

class KMessageSender: MessageSender<BigInteger, ConnectionInfoImpl> {

    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        receiver: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT> {

        val type = Type.valueOf(message?.type!!)
        val messageRegistered = MessageFactory.getMessageBy(type)
        return messageRegistered?.sendMessage(self, receiver, message)
            ?: message as KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT>
    }

    override fun <O : Serializable?> sendAsyncMessage(
        self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        receiver: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, O>?
    ) {
        val type = Type.valueOf(message?.type!!)

        val messageRegistered = MessageFactory.getMessageBy(type)

        //messageRegistered?.sendMessage(self, receiver, message)
        TODO("No implemented yet")
    }


}