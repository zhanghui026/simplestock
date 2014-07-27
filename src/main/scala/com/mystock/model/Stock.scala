package com.mystock.model

/**
 * Created by zhangh on 2014/7/26.
 */
trait Price {
  def price:Double
  def num:Int
}
case class Stock(sid:String,
                 name:String,shortDesc:String)

/**
 * var hq_str_sh601006="大秦铁路, 27.55, 27.25, 26.91, 27.55, 26.20, 26.91, 26.92,
22114263, 589824680, 4695, 26.91, 57590, 26.90, 14700, 26.89, 14300,
26.88, 15100, 26.87, 3100, 26.92, 8900, 26.93, 14230, 26.94, 25150, 26.95, 15220, 26.96, 2008-01-11, 15:05:32";
这个字符串由许多数据拼接在一起，不同含义的数据用逗号隔开了，按照程序员的思路，顺序号从0开始。
0：”大秦铁路”，股票名字；
1：”27.55″，今日开盘价；
2：”27.25″，昨日收盘价；
3：”26.91″，当前价格；
4：”27.55″，今日最高价；
5：”26.20″，今日最低价；
6：”26.91″，竞买价，即“买一”报价；
7：”26.92″，竞卖价，即“卖一”报价；
8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
10：”4695″，“买一”申请4695股，即47手；
11：”26.91″，“买一”报价；
12：”57590″，“买二”
13：”26.90″，“买二”
14：”14700″，“买三”
15：”26.89″，“买三”
16：”14300″，“买四”
17：”26.88″，“买四”
18：”15100″，“买五”
19：”26.87″，“买五”
20：”3100″，“卖一”申报3100股，即31手；
21：”26.92″，“卖一”报价
(22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
30：”2008-01-11″，日期；
31：”15:05:32″，时间；
 * @param stock
 * @param nowValue
 */
case class NowStock(stock:Stock,nowValue:String){
  lazy val values = nowValue.split(',')

  lazy val todayStartPrice = nowValue(1).toDouble

  lazy val yestodayEndPrice = nowValue(2).toDouble

  lazy val nowPrice = nowValue(3).toDouble

  lazy val todayHighPrice = nowValue(4).toDouble

  lazy val todayLowPrice = nowValue(5).toDouble


  lazy val todayDate = nowValue(30)

  lazy val todayTime = nowValue(29)
}

case class BuyStock(stock:Stock,buyPrice:Double,buyNumber:Int) extends Price
{
  override def price: Double = buyPrice

  override def num: Int = buyNumber

  def positive(percent:Int = 15) =  buyPrice * (1+percent/100d)

  def negative(percent:Int = 15) = buyPrice * (1 - percent/100d)


  def positive15 = positive(15)

  def negative15 = negative(15)
}
case class SoldStock(stock:Stock,soldPrice:Double,soldNumber:Int) extends Price {

  override def price: Double = soldPrice

  override def num: Int = soldNumber
}

case class InMoney(money:Double)
case class OutMoney(money:Double)
case class MyAccount(money:Double,remainMoney:Double,withDrawMoney:Double,buystocks:Seq[BuyStock],soldstocks:Seq[SoldStock]){

  def sumPrices[T<:Price](stocks:Seq[T]) = {
    stocks.foldLeft[Double](0.0){
      (b,a) => b + a.price*a.num
    }
  }

  /**
   * 所有买入股票的价钱
   * @return
   */
  def allBuyStocksPrice=sumPrices(buystocks)

  /**
   * 所有买进股票的价格
   * @return
   */
  def allSoldStocksPrice = sumPrices(soldstocks)

  /**
   * 当前利润
   * @return
   */
  def profit = withDrawMoney+remainMoney + allBuyStocksPrice - money

  /**
   *
   * 不算股票的利润
   */
  def withoutStocksProfit = withDrawMoney+remainMoney - money

}
object MyAccount {
  def apply(inMoney:Seq[InMoney],_remainMoney:Double,outMoney:Seq[OutMoney],_buystocks:Seq[BuyStock],_soldstocks:Seq[SoldStock]):MyAccount={
    MyAccount(
      inMoney.foldLeft(0d)(_+_.money),
      _remainMoney,outMoney.foldLeft(0d)(_+_.money),_buystocks,_soldstocks)
  }
}
