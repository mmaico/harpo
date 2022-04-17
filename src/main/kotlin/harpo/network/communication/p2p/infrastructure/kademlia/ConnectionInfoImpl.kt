package harpo.network.communication.p2p.infrastructure.kademlia

import io.ep2p.kademlia.connection.ConnectionInfo

data class ConnectionInfoImpl(val ip: String, val port: Int) : ConnectionInfo {
    fun getContact(): String = "$ip:$port"
}

