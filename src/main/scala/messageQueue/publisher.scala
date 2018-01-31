package messageQueue

import java.util.UUID

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer

import scala.io.StdIn

import scala.collection.mutable.ArrayBuffer

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel


object Send extends App {

  implicit  val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  //namming the queeu
  private val QUEUE_NAME = "hello"

  //creating a connection to the server
  val factory = new ConnectionFactory
  factory.setHost("localhost")
  val connection = factory.newConnection()
  val channel = connection.createChannel

  //creating a channel
  channel.queueDeclare(QUEUE_NAME, false, false, false, null)
  val message = "Hello World!"
  channel.basicPublish("", QUEUE_NAME, null, message.getBytes)
  System.out.println(" [x] Sent '" + message + "'")

  channel.close()
  connection.close()
  val routes =
    path("") {
      get {
        val numbers = 2
        complete(HttpEntity(ContentTypes.`application/json`,
          s"""
             |{
             |  "data": $message
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