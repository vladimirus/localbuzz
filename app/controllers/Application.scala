package controllers

import java.time.LocalDateTime
import java.util.Calendar
import javax.inject.{Inject, Singleton}

import models.Chat.UpdateTime

import scala.concurrent.duration.DurationInt
import akka.actor.{ActorSystem, Props, ActorRef}
import models.{Chat, ClientActor}
import play.api._
import play.api.libs.concurrent.Akka
import play.api.mvc._
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class Application @Inject()(actorSystem: ActorSystem) extends Controller {

  val chat = actorSystem.actorOf(Props[Chat], "chat")

  Logger info  "Scheduling the reminder daemon"
  actorSystem.scheduler.schedule(0 seconds, 1 seconds, chat, UpdateTime)

  def socket = WebSocket.acceptWithActor[String, String] {
    (request: RequestHeader) =>
      (out: ActorRef) =>
        Props(new ClientActor(out, chat))
  }

  def index = Action {
    Ok(views.html.index("Hello."))
  }
}
