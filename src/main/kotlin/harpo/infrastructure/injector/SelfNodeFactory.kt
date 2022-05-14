package harpo.infrastructure.injector

import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.messages.KMessageSender
import io.ep2p.kademlia.NodeSettings
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.table.BigIntegerRoutingTable
import java.math.BigInteger

class SelfNodeFactory {

    companion object {
        /**
         * This factory helps to manage several self node instances for multi-node simulation.
         * Outside the test environment there will be only one self node.
         */
        private val nodes: MutableMap<BigInteger, KademliaNode<BigInteger, ConnectionInfoImpl>> = mutableMapOf()
        fun create(nodeId: BigInteger, host: String = "localhost", port: Int = 9393, settings: SelfNodeSettings = SelfNodeSettings.getDefault()): KademliaNode<BigInteger, ConnectionInfoImpl> {
            if (nodes.containsKey(nodeId)) return nodes[nodeId]!!

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
            val node = KademliaNode(
                nodeId, ConnectionInfoImpl(host, port),
                BigIntegerRoutingTable(nodeId, nodeSettings),
                KMessageSender(),
                nodeSettings
            )
            nodes[nodeId] = node
            return node
        }
    }
}
