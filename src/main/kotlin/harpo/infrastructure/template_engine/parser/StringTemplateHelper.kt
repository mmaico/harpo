package harpo.infrastructure.template_engine.parser

import org.apache.velocity.Template
import org.apache.velocity.VelocityContext
import org.apache.velocity.runtime.RuntimeServices
import org.apache.velocity.runtime.RuntimeSingleton
import org.apache.velocity.runtime.parser.node.SimpleNode
import java.io.StringReader
import java.io.StringWriter

class StringTemplateHelper {
    companion object {
        fun render(templateStr: String?, params: Map<String?, Any?>?): String? {
            return try {
                val runtimeServices: RuntimeServices = RuntimeSingleton.getRuntimeServices()
                runtimeServices.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute")
                val reader = StringReader(templateStr)
                val node: SimpleNode = runtimeServices.parse(reader, "template")
                val result = StringWriter()
                val context = VelocityContext(params)
                val template = Template()
                template.setRuntimeServices(runtimeServices)
                template.data = node
                template.initDocument()
                template.merge(context, result)
                result.toString()
            } catch (e: Exception) {
                ""
            }
        }
    }
}
