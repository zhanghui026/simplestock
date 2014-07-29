package com.loadbalance

import akka.actor.ActorRef

/**
 * @author zhangh
 * @version $Id: 2014/7/28 15:45 
 */
object MasterWorkerProtocal {
  //Messages from worker
  case class WorkerCreated(workder:ActorRef)
  case class WorkerRequestsWork(worker:ActorRef)
  case class WorkIsDone(worker:ActorRef)

  case class WorkToBeDone(work:Any)
  case object WorkIsReady
  case object NoWorkToBeDone

}
