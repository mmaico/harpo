package harpo.network.communication.p2p.domain.model

import harpo.network.communication.p2p.domain.model.node.External

data class Contact(val ip: String, val port: Int) {
    fun findNode(): External {
        TODO("Call the external node with id and port and receive all information to add in my routetable")
    }
}
