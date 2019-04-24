package clicker2.networking

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString


case object UpdateGames

case object AutoSave

case class GameState(gameState: String)

class ClickerServer extends Actor {
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var clients: Set[ActorRef] = Set()

  override def receive: Receive = {


    //    Example of adding an actor with this actor as its supervisor
    //    Note that we use the context of this actor and do not create a new actor system
    //    val childActor = context.actorOf(Props(classOf[GameActor], username))
    case b: Bound => println("Listening on port: " + b.localAddress.getPort)
    case c: Connected =>
      println("Client Connected: " + c.remoteAddress)
      this.clients = this.clients + sender()
      sender() ! Register(self)
    case PeerClosed =>
      println("Client Disconnected: " + sender())
      this.clients = this.clients - sender()
    case r: Received =>
      println("Received: " + r.data.utf8String)
    //    case send: SendToClients =>
    //      println("Sending: " + send.message)
    //      this.clients.foreach((client: ActorRef) => client ! Write(ByteString(send.message)))

    case UpdateGames =>
    case AutoSave =>
    case gs: GameState =>
      val delimiter = "~"
      var mess = gs.gameState.toString
      mess = mess + delimiter
      this.clients.foreach((client: ActorRef) => client ! Write(ByteString(mess)))
  }

}


object ClickerServer {


  def main(args: Array[String]): Unit = {

    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[ClickerServer]))

    actorSystem.scheduler.schedule(0 milliseconds, 100 milliseconds, server, UpdateGames)
    actorSystem.scheduler.schedule(0 milliseconds, 5000 milliseconds, server, AutoSave)
  }
}

