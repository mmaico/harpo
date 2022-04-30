package harpo.network.communication.p2p.infrastructure.grpc.repository

import harpo.network.p2p.Answer
import harpo.network.p2p.Contact.newBuilder
import harpo.network.p2p.KademliaServiceGrpc
import harpo.network.communication.p2p.domain.model.Contact
import harpo.network.communication.p2p.infrastructure.grpc.client.ChannelPool
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNodeAPI
import io.ep2p.kademlia.node.Node
import java.math.BigInteger
import harpo.network.p2p.Node as NodeGRPC

class OperationsRepository {

    fun ping(receiver: Node<BigInteger, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?): Answer {
        val channel = ChannelPool.getBy(Contact(receiver?.connectionInfo?.ip!!, receiver.connectionInfo.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        val contactGRPC = newBuilder().setIp(self?.connectionInfo?.ip).setPort(self?.connectionInfo?.port!!).build()

        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self?.id!!.toString())
            .setContact(contactGRPC).build()

        return stub.ping(grpcSelfNode)
    }

    fun shutdown(receiver: Node<BigInteger, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?) {
        val channel = ChannelPool.getBy(Contact(receiver?.connectionInfo?.ip!!, receiver.connectionInfo.port))
        val stub = KademliaServiceGrpc.newBlockingStub(channel)
        val contactGRPC = newBuilder().setIp(self?.connectionInfo?.ip).setPort(self?.connectionInfo?.port!!).build()

        val grpcSelfNode = NodeGRPC.newBuilder()
            .setId(self?.id!!.toString())
            .setContact(contactGRPC).build()

        stub.shutdownSignal(grpcSelfNode)
    }

    fun closest(receiver: Node<BigInteger, ConnectionInfoImpl>?, self: KademliaNodeAPI<BigInteger, ConnectionInfoImpl>?): List<NodeGRPC> {
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
