package harpo.network.communication.p2p.domain.model.node

import harpo.infrastructure.injector.SelfNodeFactory
import harpo.network.communication.p2p.domain.model.Closest
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.infrastructure.SelfRepositoryKademliaAPI
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
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

    fun findClosestTo(external: External): Closest {
        return repository.findClosestTo(external)
    }

    companion object {
        operator fun invoke(id: BigInteger): Self {
            val selfNode = SelfNodeFactory.create(id)
            val repository = SelfRepositoryKademliaAPI(selfNode)
            val connectionInfo = selfNode.connectionInfo as ConnectionInfoImpl
            return Self(id = selfNode.id as BigInteger, contact = Contact(ip = connectionInfo.ip, port = connectionInfo.port), repository = repository)
        }
    }
}
