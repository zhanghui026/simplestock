package com.zzz.akka.avionics

import akka.actor.{Actor, ActorRef}

/**
 * @author zhangh
 * @version $Id: 2014/7/30 15:55 
 */
object ControlSurfaces {

  case class StickBack(amount:Float)
  case class StickForward(amount:Float)

}

class ControlSurfaces(altimeter:ActorRef) extends Actor {

  import ControlSurfaces._
  import Altimeter._
  def receive = {
    case StickBack(amount) => altimeter ! RateChange(amount)
    case StickForward(amount) => altimeter ! RateChange(-1*amount)

  }
}
