package harpo.network.communication.p2p.infrastructure

import com.google.inject.Singleton
import harpo.network.communication.p2p.domain.model.Closest
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.domain.model.node.External
import harpo.network.communication.p2p.domain.model.node.ImLive
import harpo.network.communication.p2p.domain.model.node.SelfRepository
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.node.external.BigIntegerExternalNode
import java.math.BigInteger
import java.util.*

@Singleton
class SelfRepositoryKademliaAPI(private val selfKademliaNode: KademliaNode<BigInteger, ConnectionInfoImpl>) : SelfRepository {

    override fun receivedShutdownSignalFrom(external: External) {
        selfKademliaNode.messageSender.sendMessage<>()
        val externalKademliaNode = BigIntegerExternalNode(external.id, ConnectionInfoImpl(external.contact.ip, external.contact.port), Date())
        selfKademliaNode.onShutdownSignal(externalKademliaNode)
    }

    override fun receivedPingFrom(external: External): ImLive {
        val externalKademliaNode = Node(external.id, ConnectionInfoImpl(external.contact.ip, external.contact.port), Date())
        val answer = selfKademliaNode.onPing(externalKademliaNode)
        return ImLive(isLive = answer.isAlive)
    }

    override fun findClosestTo(external: External): Closest {
        val externalKademliaNode = Node(external.id, ConnectionInfoImpl(external.contact.ip, external.contact.port), Date())
        val kademliaClosest = selfKademliaNode.onFindNode(externalKademliaNode, external.id)

        val nodesClosestToExternal: List<External> = kademliaClosest.nodes.map {
            External(it.id, it.distance, it.lastSeen.time, Contact(it.connectionInfo.ip, it.connectionInfo.port))
        }
        return Closest(nodesClosestToExternal, external)
    }
}
