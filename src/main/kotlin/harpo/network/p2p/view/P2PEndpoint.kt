package harpo.network.p2p.view

import com.google.inject.Singleton
import com.google.protobuf.Empty
import harpo.network.p2p.Answer
import harpo.network.p2p.KademliaServiceGrpc
import harpo.network.p2p.domain.model.Closest
import harpo.network.p2p.domain.model.Contact
import harpo.network.p2p.domain.model.External
import harpo.network.p2p.domain.model.Self
import harpo.network.p2p.view.support.NodeToResourceAssembler.Companion.buildFrom
import io.grpc.stub.StreamObserver
import harpo.network.p2p.Closest as ClosestResource
import harpo.network.p2p.Node as NodeResource

/**
 * Class responsible for receive the network interaction and call the domain module
 */
@Singleton
class P2PEndpoint(private val self: Self = Self()): KademliaServiceGrpc.KademliaServiceImplBase() {

    override fun shutdownSignal(nodeThatWillShutdown: NodeResource?, responseObserver: StreamObserver<Empty>?) {
        val external = External(
            id = nodeThatWillShutdown?.id?.toBigInteger()!!,
            contact = Contact(ip = nodeThatWillShutdown.contact.ip, port = nodeThatWillShutdown.contact.port))

        this.self.receivedShutdownSignalFrom(external)

        responseObserver?.onNext(null)
        responseObserver?.onCompleted()
    }

    override fun ping(requester: NodeResource?, responseObserver: StreamObserver<Answer>?) {
        val external = External(
            id = requester?.id?.toBigInteger()!!,
            contact = Contact(ip = requester.contact.ip, port = requester.contact.port))

        this.self.receivedPingFrom(external)

        val answer = Answer.newBuilder().setIsAlive(true).build()
        responseObserver?.onNext(answer)
        responseObserver?.onCompleted()
    }

    override fun findClosest(requester: NodeResource?, responseObserver: StreamObserver<ClosestResource>?) {
        val external = External(
            id = requester?.id?.toBigInteger()!!,
            contact = Contact(ip = requester.contact.ip, port = requester.contact.port))

        val closest: Closest = this.self.findClosestTo(external)

        responseObserver?.onNext(buildFrom(closest).build())
        responseObserver?.onCompleted()
    }

}
