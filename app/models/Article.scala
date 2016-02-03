package models

import java.time.LocalDateTime

import akka.actor.{Actor, ActorRef, Props}
import models.Chat.ClientSentMessage
import play.api.Logger

object Article {
  case object Update
  def props(chat: ActorRef): Props = Props(new Article(chat))
}

class Article(chat: ActorRef) extends Actor {
  import Article._

  override def receive: Receive = {
    case Update =>
      chat ! latestArticles
  }

  def latestArticles = {
    Logger info  "yo!"
    new ClientSentMessage(LocalDateTime.now.toString)
  }
}
