package harpo.network.p2p.domain.model

import harpo.infrastructure.injector.ServiceLocator
import harpo.network.p2p.infrastructure.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNode
import java.math.BigInteger

sealed class Node(val id: BigInteger, val distance: Int, val lastSeen: Long, val contact: Contact)

class External(id: BigInteger, distance: Int = 0, lastSeen: Long = 0, contact: Contact): Node(id, distance, lastSeen, contact)

class Self(id: BigInteger, distance: Int = 0, lastSeen: Long = 0, contact: Contact): Node(id, distance, lastSeen, contact) {
    fun receivedShutdownSignalFrom(external: External) {
        TODO("not implemented")
    }

    fun receivedPingFrom(external: External) {
        TODO("not implemented")
    }

    fun findClosestTo(external: External): Closest {
        TODO("not implemented")
    }

    companion object {
        operator fun invoke(): Self {
            val instance = ServiceLocator.getInjector().getInstance(KademliaNode::class.java)
            val connectionInfo = instance.connectionInfo as ConnectionInfoImpl
            return Self(id = instance.id as BigInteger, contact = Contact(ip = connectionInfo.ip, port = connectionInfo.port))
        }
    }
}
