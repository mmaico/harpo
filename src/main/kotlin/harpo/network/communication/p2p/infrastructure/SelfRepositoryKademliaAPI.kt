package harpo.network.communication.p2p.infrastructure

import com.google.inject.Singleton
import harpo.infrastructure.DateHelper
import harpo.network.communication.p2p.domain.model.Closest
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.domain.model.node.External
import harpo.network.communication.p2p.domain.model.node.ImLive
import harpo.network.communication.p2p.domain.model.node.SelfRepository
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.kademlia.ExternalNode
import io.ep2p.kademlia.model.FindNodeAnswer
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.node.external.BigIntegerExternalNode
import io.ep2p.kademlia.protocol.message.FindNodeRequestMessage
import io.ep2p.kademlia.protocol.message.PingKademliaMessage
import io.ep2p.kademlia.protocol.message.ShutdownKademliaMessage
import java.math.BigInteger
import java.util.*

@Singleton
class SelfRepositoryKademliaAPI(private val self: KademliaNode<BigInteger, ConnectionInfoImpl>) : SelfRepository {

    override fun receivedShutdownSignalFrom(external: External) {
        val shutdownMessage = ShutdownKademliaMessage<BigInteger, ConnectionInfoImpl>()
        shutdownMessage.node = BigIntegerExternalNode(
            ExternalNode(
                _connectionInfo = ConnectionInfoImpl(external.contact.ip, external.contact.port),
                _id = external.id,
                _lastSeen = DateHelper.now()
            ),
            self?.id!!.xor(external.id)
        )

        self.onMessage(shutdownMessage)
    }

    override fun receivedPingFrom(external: External): ImLive {
        val pingMessage = PingKademliaMessage<BigInteger, ConnectionInfoImpl>()
        pingMessage.node = BigIntegerExternalNode(
            ExternalNode(
                _connectionInfo = ConnectionInfoImpl(external.contact.ip, external.contact.port),
                _id = external.id,
                _lastSeen = DateHelper.now()
            ),
            self?.id!!.xor(external.id)
        )
        val answer = self.onMessage(pingMessage)
        return ImLive(isLive = answer.isAlive)
    }

    override fun findClosestTo(external: External): Closest {
        val findNodeMessage = FindNodeRequestMessage<BigInteger, ConnectionInfoImpl>()
        findNodeMessage.node = BigIntegerExternalNode(
            ExternalNode(
                _connectionInfo = ConnectionInfoImpl(external.contact.ip, external.contact.port),
                _id = external.id,
                _lastSeen = DateHelper.now()
            ),
            self?.id!!.xor(external.id)
        )

        val kademliaClosest = self.onMessage(findNodeMessage)
        val findNodeAnswer = kademliaClosest.data as FindNodeAnswer<BigInteger, ConnectionInfoImpl>

        val nodesClosestToExternal: List<External> = findNodeAnswer.nodes.map {
            External(it.id, it.distance, it.lastSeen.time, Contact(it.connectionInfo.ip, it.connectionInfo.port))
        }
        return Closest(nodesClosestToExternal, external)
    }
}
