package harpo.network.p2p.infrastructure.grpc.server

import com.google.inject.Singleton
import harpo.infrastructure.injector.ServiceLocator.Companion.getInjector
import io.grpc.ServerBuilder

@Singleton
class Server(private val gRPCServer: io.grpc.Server = ServerBuilder.forPort(8383)
    .addService(getInjector().getInstance(KademliaService::class.java)).build(), private var isRunning: Boolean = false) {

    fun start() = if (!isRunning) this.gRPCServer.start() else isRunning = true

}
