package harpo.network.p2p.infrastructure.grpc.server

import com.google.inject.Singleton
import io.grpc.ServerBuilder

@Singleton
class Server(private val gRPCServer: io.grpc.Server = ServerBuilder.forPort(8383)
    .addService(KademliaService()).build(), private var isRunning: Boolean = false) {

    fun start() = if (!isRunning) this.gRPCServer.start() else isRunning = true

}
