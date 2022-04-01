package harpo.network.p2p.domain.model.node

import harpo.infrastructure.injector.ServiceLocator
import harpo.network.p2p.domain.model.Closest
import harpo.network.p2p.domain.model.Contact
import harpo.network.p2p.infrastructure.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNode
import java.math.BigInteger

sealed class Node(val id: BigInteger, val distance: BigInteger, val lastSeen: Long, val contact: Contact)

class External(id: BigInteger, distance: BigInteger = BigInteger.ONE, lastSeen: Long = 0, contact: Contact) : Node(id, distance, lastSeen, contact)

class Self(
    id: BigInteger,
    distance: BigInteger = BigInteger.ONE,
    lastSeen: Long = 0,
    contact: Contact,
    private val repository: SelfRepository
) : Node(id, distance, lastSeen, contact) {

    fun receivedShutdownSignalFrom(external: External) = repository.receivedShutdownSignalFrom(external)

    fun receivedPingFrom(external: External): ImLive = repository.receivedPingFrom(external)

    fun findClosestTo(external: External): Closest = repository.findClosestTo(external)

    companion object {
        operator fun invoke(): Self {
            val instance = ServiceLocator.getInjector().getInstance(KademliaNode::class.java)
            val repository = ServiceLocator.getInjector().getInstance(SelfRepository::class.java)
            val connectionInfo = instance.connectionInfo as ConnectionInfoImpl
            return Self(id = instance.id as BigInteger, contact = Contact(ip = connectionInfo.ip, port = connectionInfo.port), repository = repository)
        }
    }
}
