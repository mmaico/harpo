package harpo.network.kserver

import harpo.infrastructure.injector.SelfNodeFactory
import harpo.network.communication.p2p.infrastructure.kademlia.BootstrapNode
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.kserver.infrastructure.grpc.server.GRPCServer
import io.ep2p.kademlia.node.KademliaNode
import java.math.BigInteger
import java.util.*

class KServer(private val server: GRPCServer, selfNode: KademliaNode<BigInteger, ConnectionInfoImpl>) {

    companion object {
        operator fun invoke(id: BigInteger = BigInteger(UUID.randomUUID().toString(), 16),
                            ip: String, port: Int, bootstrapNode: BootstrapNode?): KServer {
            val selfNode = SelfNodeFactory.create(nodeId = id, host = ip, port = port)
            if (bootstrapNode == null) selfNode.start() else selfNode.start(bootstrapNode)

            return KServer(GRPCServer(id, port), selfNode)
        }
    }

    fun start() {
        this.server.start()
    }
}

class Connection(val host: String, val port: Int)
