package harpo.network.p2p.infrastructure

import harpo.network.p2p.infrastructure.grpc.client.ChannelPool.Companion.getBy
import io.ep2p.kademlia.node.Node
import java.math.BigInteger

class Client {

    companion object {
        fun client() = Client()
    }

    fun ping(node: Node<BigInteger, ConnectionInfoImpl>) {
        //val channel = getBy(node.connectionInfo.getContact())

    }
}
