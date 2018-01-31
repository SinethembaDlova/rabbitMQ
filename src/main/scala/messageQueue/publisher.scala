package messageQueue

import java.util.UUID

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer

import scala.io.StdIn

object Send extends App {

  implicit  val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routes =
    path("") {
      get {
        val result = 6
        complete(HttpEntity(ContentTypes.`application/json`,
          s"""
             |{
             |  "result": $result
              }
             |""".
            stripMargin))
      }
    }

  println(s"The server is running at http://localhost:3000/")
  println("To stop the server press Ctrl+C")
  sys.addShutdownHook(system.terminate())
  val bindingFuture = Http().bindAndHandle(routes, "localhost", 3000)
}
