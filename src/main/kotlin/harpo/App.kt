
package harpo

import harpo.client.Client
import harpo.infrastructure.injector.ServiceLocator
import harpo.infrastructure.template_engine.Console
import harpo.network.p2p.view.P2PEndpoint
import io.ep2p.kademlia.node.KademliaNode

fun main() {

    val instance1 = ServiceLocator.getInjector().getInstance(KademliaNode::class.java)


    val context: MutableMap<String?, Any> = mutableMapOf("client" to Client())
    Console.execute(context, "/templates/client-template.vm")
}

