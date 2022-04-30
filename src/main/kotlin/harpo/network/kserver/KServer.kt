package harpo.network.kserver

import java.util.*

class KServer(val id: String = UUID.randomUUID().toString(), val connection: Connection) {

    operator fun invoke(): KServer {
        // create a selfnode using the configuration
        // start the grpc server
        TODO()
    }
}

class Connection(val host: String, val post: Int)
