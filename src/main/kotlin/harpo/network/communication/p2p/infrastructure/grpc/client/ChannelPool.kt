package harpo.network.communication.p2p.infrastructure.grpc.client

import harpo.network.communication.p2p.domain.model.Contact
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class ChannelPool {

    companion object {
        /**
         * Refactory for close the channel when there is no more activity
         */
        private val pool: MutableMap<String, ManagedChannel> = mutableMapOf()

        fun getBy(contact: Contact): ManagedChannel? {
            val ipPort = "${contact.ip}:${contact.port}"
            return if (pool.containsKey(ipPort)) {
                pool[ipPort]
            } else {
                val channel = ManagedChannelBuilder.forTarget(ipPort).usePlaintext().build()
                pool[ipPort] = channel
                channel
            }
        }
    }

}
