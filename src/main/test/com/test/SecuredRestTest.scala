package com.test

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MalformedHeaderRejection, MissingHeaderRejection}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import org.scalatest.{Matchers, WordSpec}
import spray.json._

import scala.collection.immutable

class SecuredRestTest extends WordSpec with Matchers with ScalatestRouteTest {

  import com.akka.secured.SecuredRest._

  HttpRequest(
    HttpMethods.POST,
    "/item",
    immutable.Seq(RawHeader("dbValueAndRole", "itemdb,admin")),
    HttpEntity(MediaTypes.`application/json`, ByteString("""{"id":"id_1", "name":"name_1", "value":1, "orderId":"orderId_1"}"""))) ~> route ~> check {
    println("Successfully posted the record to db")
    status shouldEqual StatusCodes.OK
  }

  HttpRequest(
    HttpMethods.PUT,
    "/item",
    immutable.Seq(RawHeader("dbValueAndRole", "itemdb,normal_user")),
    HttpEntity(MediaTypes.`application/json`, ByteString("""{"id":"id_1", "name":"name_1", "value":1, "orderId":"orderId_1"}"""))) ~> route ~> check {
    println("not authorize to post the record to db")
    rejection shouldEqual AuthorizationFailedRejection
  }

  HttpRequest(
    HttpMethods.GET,
    "/item",
    immutable.Seq(RawHeader("dbValueAndRole", "itemdb,admin"))) ~> route ~> check {
    status shouldEqual StatusCodes.OK
    println(s"list of records in db are ${entityAs[String].parseJson.compactPrint}")
    entityAs[String].parseJson.compactPrint shouldEqual """{"items":[{"id":"id_1", "name":"name_1", "value":1, "orderId":"orderId_1"}]}""".parseJson.compactPrint

    system.terminate()
  }
}
