package harpo.network.p2p.client

import harpo.network.p2p.client.ChannelPool.Companion.getBy
import io.ep2p.kademlia.node.Node
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.math.BigInteger

class Client {

    companion object {
        fun client() = Client()
    }

    fun ping(node: Node<BigInteger, ConnectionInfoImpl>) {
        val channel = getBy(node.connectionInfo.getContact())

    }
}
