package harpo.network.communication.p2p.view.support

import harpo.infrastructure.commons.ID
import harpo.network.communication.p2p.domain.model.Closest
import harpo.network.p2p.Closest.Builder
import harpo.network.p2p.Closest.newBuilder
import harpo.network.p2p.Contact
import harpo.network.p2p.Node

class NodeToResourceAssembler {

    companion object {
        fun buildFrom(closest: Closest): Builder {
            val closestBuilder = newBuilder()
            closest.nodes.forEach {
                closestBuilder.addNodes(
                    Node.newBuilder().setId(it.id.toString(ID.HEX))
                        .setContact(Contact.newBuilder().setIp(it.contact.ip).setPort(it.contact.port))
                        .build()
                )
            }
            return closestBuilder
        }
    }
}
