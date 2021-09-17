package harpo.network.p2p.infrastructure

import io.ep2p.kademlia.connection.ConnectionInfo
import io.ep2p.kademlia.connection.NodeConnectionApi
import io.ep2p.kademlia.model.FindNodeAnswer
import io.ep2p.kademlia.model.PingAnswer
import io.ep2p.kademlia.node.Node
import java.math.BigInteger

data class ConnectionInfoImpl(val ip: String, val port: Int): ConnectionInfo {
    fun getContact(): String = "${ip}:${port}"
}

class KademliaConnectionApi: NodeConnectionApi<BigInteger, ConnectionInfoImpl> {

    override fun ping(selfNode: Node<BigInteger, ConnectionInfoImpl>?, externalNode: Node<BigInteger, ConnectionInfoImpl>?): PingAnswer<BigInteger> {
        TODO("not implemented")
    }

    override fun shutdownSignal(p0: Node<BigInteger, ConnectionInfoImpl>?, p1: Node<BigInteger, ConnectionInfoImpl>?) {
        TODO("not implemented")
    }

    override fun findNode(p0: Node<BigInteger, ConnectionInfoImpl>?, p1: Node<BigInteger, ConnectionInfoImpl>?, p2: BigInteger?
    ): FindNodeAnswer<BigInteger, ConnectionInfoImpl> {
        TODO("not implemented")
    }

}
