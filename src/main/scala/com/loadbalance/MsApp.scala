package com.loadbalance

import akka.actor.{ActorPath, Props, ActorSystem}
import akka.pattern._
import akka.util.Timeout
import scala.concurrent.duration._


/**
 * @author zhangh
 * @version $Id: 2014/7/28 16:21 
 */
object MsApp extends App{
  val system = ActorSystem("mster")
  def worker(name: String) = system.actorOf(Props(
    new TestWorker(ActorPath.fromString(
      "akka://%s/user/%s".format(system.name, name)))))
  implicit val timeout = Timeout(5 seconds)
  val m = system.actorOf(Props[Master], "master")
  // Create three workers
  val w1 = worker("master")
  val w2 = worker("master")
  val w3 = worker("master")
  // Send some work to the master
  m ? "Hithere"
  m ? "Guys"
  m ? "So"
  m ? "What's"
  m ? "Up?"

}
