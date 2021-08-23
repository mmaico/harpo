
package harpo

import harpo.client.Client
import harpo.infrastructure.template_engine.Console

fun main() {
    val context: MutableMap<String?, Any> = mutableMapOf("client" to Client())
    Console.execute(context, "/templates/client-template.vm")
}

