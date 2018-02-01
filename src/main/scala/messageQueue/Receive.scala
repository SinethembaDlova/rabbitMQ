package messageQueue

import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.AMQP
import java.io.IOException

object Receive extends App {

  //namming the queeu
  private val QUEUE_NAME = "hello"

  val factory = new ConnectionFactory
  factory.setHost("localhost")
  val connection = factory.newConnection
  val channel = connection.createChannel

  channel.queueDeclare(QUEUE_NAME, false, false, false, null)
  println(" [*] Waiting for messages. To exit press CTRL+C")

  val consumer = new DefaultConsumer(channel) {
    @throws[IOException]
    def handleDelivery(consumerTag: String, envelope: Nothing, properties: AMQP.BasicProperties, body: Array[Byte]): Unit = {
      val message = new String(body, "UTF-8")
    println(" [x] Received '" + message + "'")
    }
  }
  channel.basicConsume(QUEUE_NAME, true, consumer)

}
