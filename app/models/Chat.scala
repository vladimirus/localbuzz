package models

import java.time.LocalDateTime

import akka.actor.{Actor, ActorRef}
import play.api.Logger

// our domain message protocol
case object Join
case object Leave
case object UpdateTime

final case class ClientSentMessage(text: String)

// Chat actor
class Chat extends Actor {
  // initial message-handling behavior
  def receive = process(Set.empty)

  def process(subscribers: Set[ActorRef]): Receive = {
    case Join =>
      // replaces message-handling behavior by the new one
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
    new ClientSentMessage(LocalDateTime.now().toString)
  }
}
