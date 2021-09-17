package harpo.network.p2p.infrastructure.grpc.client


import harpo.infrastructure.base.Base
import harpo.infrastructure.base.hexToBigInteger
import harpo.network.p2p.KademliaServiceGrpc
import harpo.network.p2p.domain.Contact
import harpo.network.p2p.domain.External
import harpo.network.p2p.domain.Self
import harpo.network.p2p.Contact as ContactGRPC
import harpo.network.p2p.Node as NodeGRPC



class KademliaClient {

    fun getNodeBy(contact: Contact): External {
        val channel = ChannelPool.getBy(contact)
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        //TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(contact.ip).setPort(contact.port).build()
        val node = stub.getNodeBy(contactGRPC)
        return External(id = node.id.hexToBigInteger(Base.HEX), contact = contact)
    }

    fun ping(self: Self, external: External): Boolean {
        val channel = ChannelPool.getBy(external.contact)
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        //TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(self.contact.ip).setPort(self.contact.port).build()
        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self.id.toString(Base.HEX))
            .setContact(contactGRPC).build()

        val answer = stub.ping(grpcSelfNode)
        return answer.isAlive
    }

    fun findClosest(self: Self, external: Contact): List<External> {
        val channel = ChannelPool.getBy(external)
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        //TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(self.contact.ip).setPort(self.contact.port).build()
        val selfGRPCNode = NodeGRPC.newBuilder()
            .setId(self.id.toString(Base.HEX))
            .setContact(contactGRPC).build()
        val closest = stub.findClosest(selfGRPCNode)

        return closest.nodesList.map { External(id = it.id.toBigInteger(), contact = Contact(it.contact.ip, it.contact.port)) }
    }
}
