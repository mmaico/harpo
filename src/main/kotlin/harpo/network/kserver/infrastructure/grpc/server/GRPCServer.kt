package harpo.network.kserver.infrastructure.grpc.server

import com.google.inject.Singleton
import harpo.network.communication.p2p.domain.model.node.Self
import harpo.network.communication.p2p.view.P2PEndpoint
import io.grpc.ServerBuilder
import java.math.BigInteger

@Singleton
class GRPCServer(id: BigInteger, private val port: Int = 9393, private var isRunning: Boolean = false) {

    private val gRPCServer: io.grpc.Server = ServerBuilder
        .forPort(port)
        .addService(P2PEndpoint(Self(id)))
        .build()

    fun start() {
        if (!isRunning) {
            this.gRPCServer.start()
            isRunning = true
        } else {
            println("The server already is running on port: $port")
        }
    }
}
