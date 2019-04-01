package com.akka.secured

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server._
import com.akka.utils.{Item, ItemList}
import spray.json._
import scala.collection.mutable.ArrayBuffer
import com.akka.utils.JsonProtocol._

object SecuredRest {

  val itemArray = ArrayBuffer.empty[Item]

  val parseHeader: Directive1[String] =
    optionalHeaderValueByName("dbValueAndRole").map[String]({
      case Some(dbValueAndRole) => dbValueAndRole
      case None => throw new RuntimeException("no header exist to access to db")
    })

  val authorize: Directive0 = {
    parseHeader.flatMap { dbValueAndRole =>
      val headerArray = dbValueAndRole.split(",")
      val (db, role) = (headerArray.head, headerArray.last)
      if (db.equalsIgnoreCase("itemDB") && role.equals("admin")) pass
      else extractMethod.flatMap({
        case HttpMethods.POST | HttpMethods.PUT => reject(AuthorizationFailedRejection)
        case _ => pass
      })
    }
  }


  val route =
    path("item") {
      (put | post) {
        authorize {
          entity(as[Item]) { item =>
            complete {
              require(!itemArray.exists(_.id == item.id), s"${item.id} exists already")
              itemArray += item
              item
            }
          }
        }
      } ~
        get {
          complete {
            ItemList(itemArray.toArray)
          }
        }
    }
}