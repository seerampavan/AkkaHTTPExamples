package com.akka.utils

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

object ServerUtil extends App {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val nonsecuredRoute = com.akka.unsecured.UnSecuredRest.route
  val securedRoute = com.akka.unsecured.UnSecuredRest.route

  val address = "localhost"
  val httpPort = 9000

  val httpBinding: Future[ServerBinding] = Http().bindAndHandle(nonsecuredRoute, address, httpPort)

  httpBinding.onComplete {
    case Failure(t) => throw t
    case Success(b) => println(s"Running REST services at http://{$b.localAddress}")
  }

}
