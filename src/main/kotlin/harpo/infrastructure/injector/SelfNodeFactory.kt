package harpo.infrastructure.injector

import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.messages.KMessageSender
import io.ep2p.kademlia.NodeSettings
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.table.BigIntegerRoutingTable
import java.math.BigInteger

class SelfNodeFactory {

    companion object {

        fun create(nodeId: BigInteger, host: String, port: Int, settings: SelfNodeSettings = SelfNodeSettings.getDefault()): KademliaNode<BigInteger, ConnectionInfoImpl> {
            val nodeSettings = NodeSettings(
                settings.alpha,
                settings.identifierSize,
                settings.bucketSize,
                settings.findNodeSize,
                settings.maximumLastSeenAgeToConsiderAlive,
                settings.pingScheduleTimeValue,
                settings.pingScheduleTimeUnit,
                settings.dhtExecutorPoolSize,
                settings.dhtScheduleExecutorPoolSize,
                settings.maximumStoreAndLookupTimeoutValue,
                settings.maximumStoreAndLookupTimeoutTimeUnit,
                settings.enabledFirstStoreRequestForcePass
            )
            return KademliaNode(
                nodeId, ConnectionInfoImpl(host, port),
                BigIntegerRoutingTable(nodeId, nodeSettings),
                KMessageSender(),
                nodeSettings
            )
        }
    }


}