package harpo.network.communication.p2p.infrastructure.kademlia

import io.ep2p.kademlia.node.Node
import java.math.BigInteger
import java.util.*

class ExternalNode(private val _connectionInfo: ConnectionInfoImpl, private val _id: BigInteger, private var _lastSeen: Date?): Node<BigInteger, ConnectionInfoImpl> {


    override fun getConnectionInfo(): ConnectionInfoImpl = _connectionInfo

    override fun getId(): BigInteger = this._id

    override fun setLastSeen(date: Date?) {
        this._lastSeen = date
    }

    override fun getLastSeen(): Date? {
        return this.lastSeen
    }
}
