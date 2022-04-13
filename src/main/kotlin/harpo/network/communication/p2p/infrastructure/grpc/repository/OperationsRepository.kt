package harpo.network.communication.p2p.infrastructure.grpc.repository

import harpo.network.communication.p2p.Answer
import harpo.network.communication.p2p.Contact.newBuilder
import harpo.network.communication.p2p.KademliaServiceGrpc
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.infrastructure.ConnectionInfoImpl
import harpo.network.communication.p2p.infrastructure.grpc.client.ChannelPool
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import java.math.BigDecimal
import harpo.network.communication.p2p.Node as NodeGRPC

class OperationsRepository {

    fun ping(receiver: Node<BigDecimal, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?): Answer {
        val channel = ChannelPool.getBy(Contact(receiver?.connectionInfo?.ip!!, receiver.connectionInfo.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        val contactGRPC = newBuilder().setIp(self?.connectionInfo?.ip).setPort(self?.connectionInfo?.port!!).build()

        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self?.id!!.toString())
            .setContact(contactGRPC).build()

        return stub.ping(grpcSelfNode)
    }

    fun shutdown(receiver: Node<BigDecimal, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?) {
        val channel = ChannelPool.getBy(Contact(receiver?.connectionInfo?.ip!!, receiver.connectionInfo.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        val contactGRPC = newBuilder().setIp(self?.connectionInfo?.ip).setPort(self?.connectionInfo?.port!!).build()

        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self?.id!!.toString())
            .setContact(contactGRPC).build()

        stub.shutdownSignal(grpcSelfNode)
    }

    fun closest(receiver: Node<BigDecimal, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigDecimal, ConnectionInfoImpl>?): List<NodeGRPC> {
        val channel = ChannelPool.getBy(Contact(receiver?.connectionInfo?.ip!!, receiver.connectionInfo.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        val contactGRPC = newBuilder().setIp(self?.connectionInfo?.ip).setPort(self?.connectionInfo?.port!!).build()

        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self?.id!!.toString())
            .setContact(contactGRPC).build()

        val closest = stub.findClosest(grpcSelfNode)

        return closest.nodesList
    }
}