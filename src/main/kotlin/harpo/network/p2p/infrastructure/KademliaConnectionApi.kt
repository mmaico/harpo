package harpo.network.p2p.infrastructure

import harpo.infrastructure.commons.ID
import harpo.network.p2p.KademliaServiceGrpc
import harpo.network.p2p.domain.model.Contact
import harpo.network.p2p.infrastructure.grpc.client.ChannelPool
import io.ep2p.kademlia.connection.ConnectionInfo
import io.ep2p.kademlia.connection.NodeConnectionApi
import io.ep2p.kademlia.model.FindNodeAnswer
import io.ep2p.kademlia.model.PingAnswer
import io.ep2p.kademlia.node.Node
import io.ep2p.kademlia.node.external.BigIntegerExternalNode
import java.math.BigInteger
import java.util.*
import harpo.network.p2p.Contact as ContactGRPC
import harpo.network.p2p.Node as NodeGRPC

data class ConnectionInfoImpl(val ip: String, val port: Int) : ConnectionInfo {
    fun getContact(): String = "$ip:$port"
}

class KademliaConnectionApi : NodeConnectionApi<BigInteger, ConnectionInfoImpl> {

    override fun ping(self: Node<BigInteger, ConnectionInfoImpl>?, external: Node<BigInteger, ConnectionInfoImpl>?): PingAnswer<BigInteger> {
        val externalContact = external!!.connectionInfo
        val selfContact = self!!.connectionInfo
        val channel = ChannelPool.getBy(Contact(externalContact.ip, externalContact.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        // TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(selfContact.ip).setPort(selfContact.port).build()
        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self.id.toString(ID.HEX))
            .setContact(contactGRPC).build()

        val answer = stub.ping(grpcSelfNode)
        return PingAnswer(external.id, answer.isAlive)
    }

    /**
     * The self node sending a signal to the contact saying it will shutdown
     */
    override fun shutdownSignal(self: Node<BigInteger, ConnectionInfoImpl>?, external: Node<BigInteger, ConnectionInfoImpl>?) {
        val externalContact = external!!.connectionInfo
        val selfContact = self!!.connectionInfo
        val channel = ChannelPool.getBy(Contact(externalContact.ip, externalContact.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        // TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(selfContact.ip).setPort(selfContact.port).build()
        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self.id.toString(ID.HEX))
            .setContact(contactGRPC).build()

        stub.shutdownSignal(grpcSelfNode)
    }

    override fun findNode(
        self: Node<BigInteger, ConnectionInfoImpl>?,
        external: Node<BigInteger, ConnectionInfoImpl>?,
        selfNodeId: BigInteger?
    ): FindNodeAnswer<BigInteger, ConnectionInfoImpl> {
        val externalContact = external!!.connectionInfo
        val selfContact = self!!.connectionInfo
        val channel = ChannelPool.getBy(Contact(externalContact.ip, externalContact.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        // TODO need to treat exception there is network failure
        val contactGRPC = ContactGRPC.newBuilder().setIp(selfContact.ip).setPort(selfContact.port).build()
        val selfGRPCNode = NodeGRPC.newBuilder()
            .setId(self.id.toString(ID.HEX))
            .setContact(contactGRPC).build()
        val closest = stub.findClosest(selfGRPCNode)
        val nodeAnswer = FindNodeAnswer<BigInteger, ConnectionInfoImpl>(self.id)

        closest.nodesList.forEach {
            val externalNode = BigIntegerExternalNode(
                Node(
                    it.id.toBigInteger(),
                    ConnectionInfoImpl(it.contact.ip, it.contact.port), Date()
                ),
                BigInteger.ONE
            )
            nodeAnswer.nodes.add(externalNode)
        }

        return nodeAnswer
    }
}
