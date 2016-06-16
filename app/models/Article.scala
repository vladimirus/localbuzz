package models

import java.time.LocalDateTime.now

import akka.actor.{Actor, ActorRef, Props}
import models.Chat.ClientSentMessage

object Article {
  case object Update
  def props(chat: ActorRef) = Props(new Article(chat))
}

class Article(chat: ActorRef) extends Actor {
  import Article._

  override def receive = {
    case Update =>
      chat ! latestArticles
  }

  def latestArticles = {
    new ClientSentMessage(now.toString)
  }
}
