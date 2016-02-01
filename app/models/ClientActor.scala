package models

import akka.actor.{Actor, ActorRef}
import models.Chat.{ClientSentMessage, Leave, Join}

class ClientActor(client: ActorRef, chat: ActorRef) extends Actor {

  chat ! Join

  override def postStop() = chat ! Leave

  def receive = {
    // this handles messages from the websocket
    case text: String =>
      chat ! ClientSentMessage(text)

    case ClientSentMessage(text) =>
      client ! text
  }
}
