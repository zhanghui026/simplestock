package com.mystock.sina

/**
 * Created by zhangh on 2014/7/26.
 */
object StockUtil {
  /**
   * 看股票代码
沪市A股票买卖的代码是以600或601打头
B股买卖的代码是以900打头

深市A股票买卖的代码是以000打头
B股买卖的代码是以200打头
   * @param id
   * @return
   */
  def formatStockId(id:String) = {
    val r = id.toLowerCase
    if(r.startsWith("sz")||r.startsWith("sh")){
      r
    }else{
      if(r.startsWith("600") || r.startsWith("601"))
        "sh" + r
      else if (r.startsWith("000") || r.startsWith("200"))
        "sz" + r
      else
        r
    }
  }

}
