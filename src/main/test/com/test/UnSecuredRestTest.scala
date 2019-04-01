package com.test

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{HttpEntity, MediaTypes, StatusCodes}
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import spray.json._

class UnSecuredRestTest extends WordSpec with Matchers with ScalatestRouteTest {

  import com.akka.unsecured.UnSecuredRest._

  (1 to 5).map(index => s"""{"id":"id_$index", "name":"name_$index", "value":$index, "orderId":"orderId_$index"}""").foreach { itemStr =>

    Post("/item", HttpEntity(MediaTypes.`application/json`, ByteString(itemStr))) ~> route ~> check {
      println(s"Successfully posted the record - ${entityAs[String].parseJson.compactPrint} to DB ")
      status shouldEqual StatusCodes.OK
    }
  }

  Put("/item", HttpEntity(MediaTypes.`application/json`, ByteString(s"""{"id":"id_2", "name":"name_2_updated", "value":200, "orderId":"orderId_2_updated"}"""))) ~> route ~> check {
    println(s"Successfully updated the record ${entityAs[String].parseJson.compactPrint} in DB ")
    status shouldEqual StatusCodes.OK
    entityAs[String].parseJson.compactPrint shouldEqual """{"id":"id_2", "name":"name_2_updated", "value":200, "orderId":"orderId_2_updated"}""".parseJson.compactPrint
  }

  Delete("/item", HttpEntity(MediaTypes.`application/json`, ByteString(s"""{"id":"id_4", "name":"name_4", "value":4, "orderId":"orderId_4"}"""))) ~> route ~> check {
    println(s"Successfully deleted the record ${entityAs[String].parseJson.compactPrint} in DB ")
    status shouldEqual StatusCodes.OK
  }

  Get("/item") ~> route ~> check {
    println(s"Fetching all the records from DB- \n${entityAs[String].parseJson.compactPrint} ")
    status shouldEqual StatusCodes.OK
  }
  system.terminate()
}
