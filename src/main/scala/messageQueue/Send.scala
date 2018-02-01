package messageQueue

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel


object Send extends App {

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

}
