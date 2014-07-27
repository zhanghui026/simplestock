package com.mystock.http

import akka.actor.Actor
import akka.actor.Actor.Receive
import akka.actor.Status.Success
import com.mystock.model.Stock
import akka.io.IO
import akka.pattern.ask
import com.mystock.sina.SinaHelper
import org.apache.http.HttpResponse
import org.apache.http.client.{ClientProtocolException, ResponseHandler}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


import scala.concurrent.Future
//import DefaultActorSystem._
/**
 * Created by zhangh on 2014/7/26.
 */
class SinaFininceHttp extends Actor{
  def doStock(sid:String) {
    val httpclient = HttpClients.createDefault()
    val httpget = new HttpGet("http://hq.sinajs.cn/list=" + sid)

    // Create a custom response handler
    val responseHandler = new ResponseHandler[String]() {
      override def handleResponse(response: HttpResponse): String = {
        val status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
          val entity = response.getEntity();
          if (entity != null) EntityUtils.toString(entity) else null
        } else {
          throw new ClientProtocolException("Unexpected response status: " + status);
        }
      }
    }
    val responseBody = httpclient.execute(httpget, responseHandler)
    val p=SinaHelper.parse(responseBody)
    sender ! p
  }
  override def receive: Receive = {
    case sid:String => doStock(sid)
    case Stock(sid,_,_) => doStock(sid)
    case x => println(x)
  }
}
