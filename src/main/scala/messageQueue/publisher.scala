package messageQueue

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
//    path(""){
//      get{
//      }
//  }

  print(s"The sever is running at http://localhost:3000")
  sys.addShutdownHook(system.teminate())
  val bindingFuture = Http().bindAndHandle(routes, "localhost", 3000)
}
