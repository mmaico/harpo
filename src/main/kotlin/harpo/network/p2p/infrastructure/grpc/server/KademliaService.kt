package harpo.network.p2p.infrastructure.grpc.server

import harpo.network.p2p.*
import io.grpc.stub.StreamObserver

class KademliaService: KademliaServiceGrpc.KademliaServiceImplBase() {

    override fun getNodeBy(request: Contact?, responseObserver: StreamObserver<Node>?) {
        val nodeToResponse = Node.newBuilder().setId("idOfNode").setDistance(0).setLastSeen(0).setContact(request).build()
        responseObserver?.onNext(null)
        responseObserver?.onCompleted()
        TODO("Call kademlia api")
    }


    override fun ping(requester: Node?, responseObserver: StreamObserver<Answer>?) {
        val answer = Answer.newBuilder().setIsAlive(true).build()
        responseObserver?.onNext(answer)
        responseObserver?.onCompleted()
        TODO("Call kademlia api")
    }

    override fun findClosest(request: Node?, responseObserver: StreamObserver<Closest>?) {
        responseObserver?.onNext(null)
        responseObserver?.onCompleted()
        TODO("Call kademlia api")
    }

}
