package com.mystock.http

import akka.actor.Status.Success
import akka.actor.{ActorSystem, Props}
import com.mystock.model.Stock
import org.apache.http.HttpResponse
import org.apache.http.client.{ClientProtocolException, ResponseHandler, HttpClient}
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

import scala.concurrent.Future


/**
 * Created by zhangh on 2014/7/26.
 */
object SinaFinace extends App{

  StockMaster.gainStockSnap(Seq("600050","000996","600163","600726"))

  println("----------------------------------------");

}
