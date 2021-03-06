/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package harpo

import harpo.network.communication.p2p.infrastructure.kademlia.BootstrapNode
import harpo.network.communication.p2p.infrastructure.kademlia.ConnectionInfoImpl
import harpo.network.kserver.KServer
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.test.assertNotNull

class AppTest {
    @Test
    fun appHasAGreeting() {
        val serverOne = KServer(BigInteger("1", 10), "127.0.0.1", 9090, null)
        serverOne.start()
        val bootstrapNode =
            BootstrapNode(ConnectionInfoImpl("127.0.0.1", 9090), BigInteger("1", 10), null)

        val serverTwo = KServer(BigInteger("2", 10), "127.0.0.1", 8080, bootstrapNode)
        serverTwo.start()

        val serverThree = KServer(BigInteger("3", 10), "127.0.0.1", 8181, bootstrapNode)
        serverThree.start()

        val serverFour = KServer(BigInteger("4", 10), "127.0.0.1", 8282, bootstrapNode)
        serverFour.start()
        Thread.sleep(180000)
        //assertNotNull("", "app should have a greeting")
    }
}
