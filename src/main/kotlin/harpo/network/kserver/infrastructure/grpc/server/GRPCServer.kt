package harpo.network.kserver.infrastructure.grpc.server

import com.google.inject.Singleton
import harpo.infrastructure.injector.ServiceLocator.Companion.getInjector
import harpo.network.communication.p2p.view.P2PEndpoint
import io.grpc.ServerBuilder

@Singleton
class GRPCServer(private val port: Int = 9393, private var isRunning: Boolean = false) {

    private val gRPCServer: io.grpc.Server = ServerBuilder.forPort(port)
        .addService(getInjector().getInstance(P2PEndpoint::class.java)).build()

    fun start() {
        if (!isRunning) {
            this.gRPCServer.start()
            isRunning = true
        } else {
            println("The server already is running on port: $port")
        }
    }
}
