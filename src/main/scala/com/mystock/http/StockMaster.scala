package com.mystock.http

import akka.actor.{Props, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import com.mystock.sina.StockUtil

import scala.util.{Success, Failure}

/**
 * Created by zhangh on 2014/7/26.
 */
object StockMaster {
  implicit val system = ActorSystem()
  val sinaHttpGet = system.actorOf(Props[SinaFininceHttp],"sina")

  def gainStockSnap(stockIds:Seq[String]) = {
    implicit val timeout = Timeout(5 seconds)
    import scala.concurrent.ExecutionContext.Implicits.global

   val res = stockIds.map {
      code =>
        val f = (sinaHttpGet ? StockUtil.formatStockId(code)).mapTo[(String,String)]
//        f.onSuccess {
//          case (s,k) => println((s,k))
//        }
       f
   }
   res foreach {
     f =>
       f onSuccess {
         case (s,v) => println(s+"->"+v)
       }
   }


//    val future = (system.actorSelection("/user/sina") ? StockUtil.formatStockId(stockIds(0)) ).mapTo[(String,String)]
//    future onSuccess {
//      case (k:String,s:String) => println("i known"+s)
//    }
//    import system.dispatcher
    //获得一串股票的
//    val res = stockIds.map {
//      sid => {
//        val future = ( ).mapTo[String]
//    val result = Await.result(future, 5 seconds).asInstanceOf[String]
//    println("result:"+result)
//        println("scc"+f)
//      f onSuccess {
//      case s:String => println("ddd"+s)
//    }
//       f onFailure {
//         case e:Exception => e.printStackTrace()
//       }
//        f onComplete {
//          case Success(x) => print("succ"+x)
//          case Failure(x) => println("fail"+x)
//        }

//      }

//    }
//    res foreach {
//      p =>
//        println(p)
//      }

  }
  

}
