package com.akka.utils

import spray.json.DefaultJsonProtocol

case class Item(id: String, name: String, value: Int, orderId: Option[String])

case class ItemList(items: Array[Item])

object JsonProtocol extends DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat4(Item)
  implicit val itemListFormat = jsonFormat1(ItemList)
}