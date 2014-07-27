package com.mystock.sina

/**
 * Created by zhangh on 2014/7/26.
 */
object SinaHelper {
  final val SEP = '='

  def parse(nowStock: String) = {
    //=分隔符
    val stockId = nowStock.takeWhile(_ != SEP).split('_').last
    val stockValue = nowStock.dropWhile(_!=SEP).stripPrefix("=\"").stripLineEnd.stripSuffix(";").stripSuffix("\"")
    (stockId,stockValue)
  }

  def parseComplete(stockValue:String) = {
    stockValue.split(',')
  }
}
