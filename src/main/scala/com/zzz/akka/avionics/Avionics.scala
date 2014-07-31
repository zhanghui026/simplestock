package com.zzz.akka.avionics

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern._
import scala.concurrent.ExecutionContext.Implicits.global
/**
 * @author zhangh
 * @version $Id: 2014/7/30 16:07 
 */
object Avionics {

  implicit  val timeout = Timeout(5 seconds)

  val system = ActorSystem("PlaneSimulation")

  val plane = system.actorOf(Props[Plane],"Plane")
  def main(args:Array[String]) {
    val control = Await.result((plane ? Plane.GiveMeControl).mapTo[ActorRef],5 seconds)
    system.scheduler.scheduleOnce(200.milli){
      control ! ControlSurfaces.StickBack(1f)
    }

    system.scheduler.scheduleOnce(1 seconds){
      control ! ControlSurfaces.StickBack(0f)
    }

    system.scheduler.scheduleOnce(3 seconds){
      control ! ControlSurfaces.StickBack(0.5f)
    }

    system.scheduler.scheduleOnce(4.seconds){
      control ! ControlSurfaces.StickBack(0f)
    }

    system.scheduler.scheduleOnce(5.seconds) {
      system.shutdown
    }
  }

}
