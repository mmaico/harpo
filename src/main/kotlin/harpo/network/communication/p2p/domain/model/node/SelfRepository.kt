package harpo.network.communication.p2p.domain.model.node

import harpo.network.communication.p2p.domain.model.Closest

interface SelfRepository {
    fun receivedShutdownSignalFrom(external: External)
    fun receivedPingFrom(external: External): ImLive
    fun findClosestTo(external: External): Closest
}
