package harpo.infrastructure.template_engine

import harpo.infrastructure.template_engine.commands.Commands
import org.w3c.dom.Node
import java.lang.RuntimeException
import javax.xml.parsers.DocumentBuilderFactory

class Console {

    companion object {

        fun execute(context: MutableMap<String?, Any>, templatePath: String) {
            val dbf = DocumentBuilderFactory.newInstance()
            val resource = Console::class.java.getResourceAsStream(templatePath)
            val document = dbf.newDocumentBuilder().parse(resource)
            val nodeSize = document.documentElement.childNodes.length
            var nodes = mutableListOf<Node>()

            for (i in 0 until nodeSize) {
                val nodeItem = document.documentElement.childNodes.item(i)
                if (!nodeItem.nodeName.startsWith("#")) {
                    nodes.add(nodeItem)
                }
            }

            nodes.forEach {
                val command = Commands.commands[it.nodeName] ?: throw RuntimeException("Command[${it.nodeName}] not implemented")
                command.execute(it, context)
            }
        }
    }
}
