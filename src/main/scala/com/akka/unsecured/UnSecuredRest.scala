package com.akka.unsecured

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.akka.utils.JsonProtocol._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.akka.utils.{Item, ItemList}
import spray.json._

import scala.collection.mutable.ArrayBuffer

object UnSecuredRest {

  val itemArray = ArrayBuffer.empty[Item]

  val route =
    path("item") {
      post {
        entity(as[Item]) { item =>
          complete {
            require(!itemArray.exists(_.id == item.id),
                    s"${item.id} exists already")
            itemArray += item
            item
          }
        }
      } ~
        put {
          entity(as[Item]) { item =>
            complete {
              val elemIndex = for {
                itemVal <- itemArray
                if itemVal.id == item.id
              } yield itemArray.indexOf(itemVal)
              itemArray.remove(elemIndex.head)
              itemArray += item
              item
            }
          }
        } ~
        delete {
          entity(as[Item]) { item =>
            complete {
              val elemIndex = for {
                itemVal <- itemArray
                if itemVal.id == item.id
              } yield itemArray.indexOf(itemVal)
              itemArray.remove(elemIndex.head)
              ItemList(itemArray.toArray)
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
