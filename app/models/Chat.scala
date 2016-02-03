package models

import akka.actor.{Actor, ActorRef}

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
      context become process(subscribers + sender)

    case Leave =>
      context become process(subscribers - sender)

    case msg: ClientSentMessage =>
      (subscribers - sender).foreach { _ ! msg }
  }
}
