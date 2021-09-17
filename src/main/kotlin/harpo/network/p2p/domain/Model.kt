package harpo.network.p2p.domain

import java.math.BigInteger


data class Contact(val ip: String, val port: Int) {
    fun findNode(): External {
        TODO("Call the external node with id and port and receive all information to add in my routetable")
    }
}


sealed class Node(val id: BigInteger, val distance: Int = 0, val lastSeen: Long , val contact: Contact)
class External(id: BigInteger, distance: Int = 0, lastSeen: Long = 0, contact: Contact): Node(id, distance, lastSeen, contact)
class Self(id: BigInteger, distance: Int = 0, lastSeen: Long = 0 , contact: Contact): Node(id, distance, lastSeen, contact) {

    fun ping(external: External) {

    }

    fun findClosest() {

    }
}


