package harpo.network.p2p.infrastructure.grpc.server

import com.google.protobuf.Empty
import harpo.infrastructure.commons.ID
import harpo.network.p2p.*
import harpo.network.p2p.infrastructure.ConnectionInfoImpl
import io.ep2p.kademlia.node.KademliaNode
import io.ep2p.kademlia.node.external.BigIntegerExternalNode
import io.grpc.stub.StreamObserver
import java.math.BigInteger
import java.util.*

import io.ep2p.kademlia.node.Node as KNode


class KademliaService(private var selfNode: KademliaNode<BigInteger, ConnectionInfoImpl>): KademliaServiceGrpc.KademliaServiceImplBase() {


    // The method should be call the model instead of Kademlia api
    override fun shutdownSignal(nodeThatWillTurnOff: Node?, responseObserver: StreamObserver<Empty>?) {
        val kademliaExternalNode = BigIntegerExternalNode(
            KNode(
                nodeThatWillTurnOff?.id?.toBigInteger(),
                ConnectionInfoImpl(nodeThatWillTurnOff?.contact!!.ip, nodeThatWillTurnOff.contact.port), Date()
            ), BigInteger.ONE)

        selfNode.onShutdownSignal(kademliaExternalNode)

        responseObserver?.onNext(null)
        responseObserver?.onCompleted()
    }

    override fun ping(requester: Node?, responseObserver: StreamObserver<Answer>?) {
        val kademliaExternalNode = BigIntegerExternalNode(
            KNode(
                requester?.id?.toBigInteger(),
                ConnectionInfoImpl(requester?.contact!!.ip, requester.contact.port), Date()
            ), BigInteger.ONE)

        val pingAnswer = selfNode.onPing(kademliaExternalNode)

        val answer = Answer.newBuilder().setIsAlive(pingAnswer.isAlive).build()
        responseObserver?.onNext(answer)
        responseObserver?.onCompleted()
    }

    override fun findClosest(requester: Node?, responseObserver: StreamObserver<Closest>?) {

        val kademliaExternalNode = BigIntegerExternalNode(
            KNode(
                requester?.id?.toBigInteger(),
                ConnectionInfoImpl(requester?.contact!!.ip, requester.contact.port), Date()
            ), BigInteger.ONE)

        val closestNodes = selfNode.onFindNode(kademliaExternalNode, kademliaExternalNode.id)
        val closestBuilder = Closest.newBuilder()

        closestNodes.nodes.forEach { closestBuilder.addNodes(Node
                .newBuilder().setId(it.id.toString(ID.HEX))
                .setContact(Contact.newBuilder()
                    .setIp(it.connectionInfo.ip)
                    .setPort(it.connectionInfo.port))
                .build())}

        responseObserver?.onNext(closestBuilder.build())
        responseObserver?.onCompleted()
    }

}
