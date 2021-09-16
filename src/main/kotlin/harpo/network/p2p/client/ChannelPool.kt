package harpo.network.p2p.client

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class ChannelPool {

    companion object {
        /**
         * Refactory for close the channel when there is no more activity
         */
        private val pool: MutableMap<String, ManagedChannel> = mutableMapOf()

        fun getBy(contact: String): ManagedChannel? {
            return if (pool.containsKey(contact)) {
                pool[contact]
            } else {
                val channel = ManagedChannelBuilder.forTarget(contact).usePlaintext().build()
                pool[contact] = channel
                channel
            }
        }
    }


}
