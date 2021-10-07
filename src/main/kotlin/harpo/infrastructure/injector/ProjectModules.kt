package harpo.infrastructure.injector

import com.google.inject.*
import com.google.inject.name.Names
import harpo.criptography.ecdh.ECDHCipher
import harpo.criptography.shamir.SecretSharing
import harpo.network.p2p.domain.model.node.SelfRepository
import harpo.network.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.p2p.infrastructure.KademliaConnectionApi
import harpo.network.p2p.infrastructure.SelfRepositoryKademliaAPI
import harpo.network.p2p.view.P2PEndpoint
import io.ep2p.kademlia.NodeSettings
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.table.BigIntegerRoutingTable
import java.math.BigInteger

class ProjectModules: AbstractModule() {
    @Provides @Singleton
    fun getCipher(): ECDHCipher = ECDHCipher()

    @Provides
    fun getShamir(): SecretSharing = SecretSharing()
}


class P2PModules: AbstractModule() {

    @Provides @Singleton
    fun getKademliaAPI() = KademliaConnectionApi()

    /**
     * i tried use @Provides to create a new instance but i caught an error because the Kademlia node don't have a default constructor
     * the alternative was to do the configuration using the configure with bind and toInstance
     */
    private fun selfNode(): KademliaNode<BigInteger, ConnectionInfoImpl> {
        //TODO get the configs in the properties file
        val nodeId = BigInteger("397c80bef1514077839a3a02d4bcf1a3", 16)
        return KademliaNode(nodeId, BigIntegerRoutingTable(nodeId, NodeSettings()), KademliaConnectionApi(), ConnectionInfoImpl("localhost", 8384))
    }

    @Provides @Singleton
    fun p2pEndpoint() = P2PEndpoint()


    override fun configure() {
        bind(KademliaNode::class.java).toInstance(selfNode())
        bind(SelfRepository::class.java).toInstance(SelfRepositoryKademliaAPI(selfNode()))
    }
}

class ServiceLocator {

    companion object {
        private val injector: Injector = Guice.createInjector(ProjectModules(), P2PModules())
        fun getInjector() = injector
    }
}
