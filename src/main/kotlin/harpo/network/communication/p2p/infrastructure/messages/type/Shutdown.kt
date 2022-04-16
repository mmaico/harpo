package harpo.network.communication.p2p.infrastructure.messages.type

import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.repository.OperationsRepository
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.protocol.message.EmptyKademliaMessage
import io.ep2p.kademlia.protocol.message.KademliaMessage
import java.io.Serializable
import java.math.BigInteger

class Shutdown(private val repository: OperationsRepository = OperationsRepository()): Message {
    override fun <INPUT : Serializable?, OUTPUT : Serializable?> sendMessage(
        self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?,
        receiver: Node<BigInteger, ConnectionInfoImpl>?,
        message: KademliaMessage<BigInteger, ConnectionInfoImpl, OUTPUT>?
    ): KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT> {

        repository.shutdown(receiver, self)

        return  EmptyKademliaMessage<BigInteger, ConnectionInfoImpl>() as KademliaMessage<BigInteger, ConnectionInfoImpl, INPUT>
    }


}