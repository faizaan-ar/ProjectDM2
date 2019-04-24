package clicker2.networking

import akka.actor.Actor

case object Update

case object ClickGold

case object Save

case object Setup

case class BuyEquipment(equipmentID: String)

class GameActor(username: String) extends Actor {

  override def receive: Receive = {
    case Setup =>
    case Update =>
    case Save =>
    case ClickGold =>
    case buy: BuyEquipment =>
  }
}
