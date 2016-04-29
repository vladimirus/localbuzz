package models

import akka.actor.{Actor, ActorRef}
import play.api.Logger

object Chat {
  case class ClientSentMessage(text: String)
  case object Join
  case object Leave
  case object UpdateTime
}

class Chat extends Actor {
  import Chat._

  override def receive = process(Set.empty)

  def process(subscribers: Set[ActorRef]): Receive = {
    case Join =>
      Logger info  "User joined"
      context become process(subscribers + sender)

    case Leave =>
      Logger info  "User left"
      context become process(subscribers - sender)

    case msg: ClientSentMessage =>
      (subscribers - sender).foreach { _ ! msg }
  }
}
