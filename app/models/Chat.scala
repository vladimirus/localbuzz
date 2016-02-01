package models

import java.time.LocalDateTime

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

  def receive = process(Set.empty)

  def process(subscribers: Set[ActorRef]): Receive = {
    case Join =>
      context become process(subscribers + sender)

    case Leave =>
      context become process(subscribers - sender)

    case msg: ClientSentMessage =>
      // send messages to all subscribers except sender
      (subscribers - sender).foreach { _ ! msg }

    case UpdateTime =>
      subscribers.foreach { _ !  currentTimedMessage}
  }

  def currentTimedMessage = {
    Logger info  "yo!"
    new ClientSentMessage(LocalDateTime.now.toString)
  }
}
