package controllers

import javax.inject.{Inject, Singleton}

import akka.actor.{ActorRef, ActorSystem, Props}
import models.Article.Update
import models.{Article, Chat, ClientActor}
import play.api.Play.current
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

@Singleton
class Application @Inject()(actorSystem: ActorSystem) extends Controller {

  val chat = actorSystem.actorOf(Props[Chat], "chat")
  val article = actorSystem.actorOf(Article.props(chat), "article")
  actorSystem.scheduler.schedule(0 seconds, 1 seconds, article, Update)

  def socket = WebSocket.acceptWithActor[String, String] {
    (request: RequestHeader) =>
      (out: ActorRef) =>
        Props(new ClientActor(out, chat))
  }

  def index = Action {
    Ok(views.html.index("Hello."))
  }
}
