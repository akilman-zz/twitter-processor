package blog

import java.util
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import blog.bootstrap.Bootstrap
import blog.examples.ScalaTwitterProcessor
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHandler

/**
 * Embedded Jetty server to connect with the twitter processor
 */
object JettyDriver {
  def main(args: Array[String]): Unit = {
    val server = new Server(8080)
    val handler = new ServletHandler
    server.setHandler(handler)

    handler.addServletWithMapping("blog.ProcessorServlet", "/")
    server.start
    server.join
  }
}

class ProcessorServlet extends HttpServlet {

  val processor = new ScalaTwitterProcessor(Bootstrap.getTwitterInstance)

  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    val sizeVsWords: util.Map[Integer, util.List[String]] = processor.homeStreamSizeVsWord()
    resp.getWriter.println("Home timeline size -> words " + sizeVsWords)
  }
}
