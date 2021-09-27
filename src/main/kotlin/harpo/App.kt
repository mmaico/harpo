
package harpo

import com.google.inject.Key
import com.google.inject.name.Names
import harpo.client.Client
import harpo.criptography.ecdh.ECDHCipher
import harpo.infrastructure.injector.ServiceLocator
import harpo.infrastructure.template_engine.Console
import harpo.network.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.p2p.infrastructure.KademliaConnectionApi
import harpo.network.p2p.infrastructure.grpc.server.KademliaService
import io.ep2p.kademlia.node.KademliaNode
import java.math.BigInteger

fun main() {

    val instance1 = ServiceLocator.getInjector().getInstance(KademliaService::class.java)

    val context: MutableMap<String?, Any> = mutableMapOf("client" to Client())
    Console.execute(context, "/templates/client-template.vm")
}

