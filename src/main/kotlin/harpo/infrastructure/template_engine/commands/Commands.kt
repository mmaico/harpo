package harpo.infrastructure.template_engine.commands


import harpo.infrastructure.template_engine.parser.StringTemplateHelper.Companion.render
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.BufferedReader
import java.io.InputStreamReader

class Commands {

    companion object {
        val commands = mutableMapOf<String, Command>()
        init {
            commands["print"] = object: Command {
                override fun execute(node: Node, context: MutableMap<String?, Any>) = println(node.textContent)
            }
            commands["velocity"] = object: Command {
                override fun execute(node: Node, context: MutableMap<String?, Any>) {
                    val templateRendered = render(node.textContent, context)
                    println(templateRendered)
                }

            }
            commands["command-input"] = object: Command {
                override fun execute(node: Node, context: MutableMap<String?, Any>) {
                    println(node.textContent)
                    val reader = BufferedReader(InputStreamReader(System.`in`))
                    val readed = reader.readLine()
                    val variableName = (node as Element).getAttribute("variable")
                    context[variableName] = readed
                }
            }
        }
    }

}

interface Command {
    fun execute(node: Node, context: MutableMap<String?, Any>)
}