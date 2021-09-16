package harpo.network.p2p

import harpo.*
import io.grpc.*
import io.grpc.stub.StreamObserver
import java.io.Closeable
import java.util.concurrent.TimeUnit

class GRPCServer {


}
class HelloWorldClient(val channel: ManagedChannel) : Closeable {

    private val stub = KademliaLogServiceGrpc.newBlockingStub(channel)


    fun log(value: String)  {
        val kademliaConnection = KademliaConnectionGrpc.newBlockingStub(channel)


        val request = CreateLog.newBuilder().setValue(value).build()
        try {
            val response: ResponseLog = stub.log(request)
            println("Greeter client received: ${response.response}")
        } catch (e: StatusException) {
            println("RPC failed: ${e.status}")
        }
    }

    override fun close() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
fun main() {
    runServer()
}

fun runServer() {
    ServerBuilder.forPort(8383).addService(KvService()).build().start()
    Thread.sleep(5000)
    val channel = ManagedChannelBuilder.forTarget("localhost:8383").usePlaintext()
    HelloWorldClient(channel.build()).log("test")

}

class KvService: KademliaLogServiceGrpc.KademliaLogServiceImplBase() {

    override fun log(request: CreateLog?, responseObserver: StreamObserver<ResponseLog>?) {
        println("*************** Server receved this data: ${request?.value}")
        val build = ResponseLog.newBuilder().setResponse("################### SERVER PONG #######################").build()
        responseObserver?.onNext(build)
        responseObserver?.onCompleted()
    }
}
