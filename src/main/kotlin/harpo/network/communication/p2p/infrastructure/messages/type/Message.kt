package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigInteger

interface Message {

    fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        api: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        external: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT>

}

