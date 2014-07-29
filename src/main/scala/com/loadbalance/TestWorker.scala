package com.loadbalance

import akka.actor.{ActorRef, ActorPath}

import scala.concurrent.Future
import akka.pattern.pipe
/**
 * @author zhangh
 * @version $Id: 2014/7/28 16:09 
 */
class TestWorker(masterLocation: ActorPath) extends Worker(masterLocation) {
  // We'll use the current dispatcher for the execution context.
  // You can use whatever you want.
  implicit val ec = context.dispatcher

  def doWork(workSender: ActorRef, msg: Any): Unit = {
    Future {
      println(workSender.path)
      workSender ! msg
//      println("EEEEEEEEEEEEEEEEEEEEEEE")
      WorkComplete("done")
    } pipeTo self
  }
}
