package harpo.network.communication.p2p.infrastructure.messages

import harpo.network.communication.p2p.infrastructure.messages.type.*
import harpo.network.communication.p2p.infrastructure.messages.type.Type.*

object MessageFactory {

    private val handlers = hashMapOf(
        PING to Ping(), SHUTDOWN to Shutdown(), FIND_NODE_REQ to Closest())

    fun getMessageBy(type: Type): Message? {
        return handlers[type]
    }
}