package com.example

import com.mystock.http.{SinaFininceHttp, SinaFinace}
import com.mystock.model.NowStock
import com.mystock.sina.StockUtil

/**
 * Created by zhangh on 2014/7/30.
 */
object AllStocksLow extends App{
  //找到所有上证和深证当前价格以及前一天价格小于3元的股票
  //sh 600和sh 601
  def doStock(stockPrefix:String,lowPrice:Double=3)= {
    val ss =( (1 to 999) map {
      d => {
        val stock = if(d < 10) stockPrefix+"00"+d
        else if (d>=10 & d<100) stockPrefix+"0"+d
        else stockPrefix+d
        SinaFininceHttp.doStock(stock)
      }
    }).filter(!_._2.isEmpty).map {
      p => NowStock(p._1,p._2)
    }.filter(p=> p.nowPrice < lowPrice && p.nowPrice > 1)
    //  println(ss)
    ss.foreach {
      s => println(s.stock +" "+s.nowPrice+" "+s.todayStartPrice+" "+s.todayLowPrice)
    }
  }

  doStock("sh600")
  doStock("sh601")
  doStock("sz000")
}
