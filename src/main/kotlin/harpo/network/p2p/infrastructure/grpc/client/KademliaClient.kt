package harpo.network.p2p.infrastructure.grpc.client

import harpo.network.p2p.domain.External
import harpo.network.p2p.domain.Self


data class Contact(val ip: String, val port: Int)


class KademliaClient {


    fun getNodeBy(contact: Contact): External {
        TODO("call the gRPC service and return the external node with all information to add in the route table")
    }


    fun ping(self: Self, external: External): Boolean {
        TODO("call the gRPC service and return the state of the external node")
    }

    fun findClosest(self: Self): List<External> {
        TODO("call the gRPC service and return the closest nodes")
    }
}
