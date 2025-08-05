
object BasketCalculator {
  case class BasketSummary(
                            subtotal: Double,
                            discountDescriptions: List[String],
                            total: Double
                          )
  val productPrices: Map[String, Double] = Map(
    "apple" -> 1.00,
    "bread" -> 0.80,
    "soup"  -> 0.65,
    "milk"  -> 1.30
  )
  def calculate(items: List[String]):BasketSummary  = {
    val validItems = items.map(_.toLowerCase).filter(productPrices.contains)
    val itemCounts = validItems
      .groupBy(identity)
      .mapValues(_.size)

    var subtotal = 0.0
    var discounts = List.empty[String]
    var discountAmount = 0.0
    subtotal = validItems.map(productPrices).sum


    itemCounts.get("apple").foreach { qty =>
      val appleDiscount = qty * productPrices("apple") * 0.10
      discountAmount += appleDiscount
      discounts ::= f"Apple 10%% off: -£${appleDiscount}%.2f"
    }


    // Bread free with 2 soups
    val soupQty = itemCounts.getOrElse("soup", 0)
    val breadQty = itemCounts.getOrElse("bread", 0)
    if (soupQty>=2 && breadQty > 0) {
      val freeBreadQty = math.min(soupQty / 2, breadQty)
      val breadDiscount = freeBreadQty * productPrices("bread") * 0.5
      discountAmount += breadDiscount
      discounts ::= f"Bread 50%% off (1 for 2 soups): -£${breadDiscount}%.2f"
    }
    BasketSummary(
      subtotal = subtotal,
      discountDescriptions = if (discounts.isEmpty) List("No offers available") else discounts.reverse,
      total = subtotal - discountAmount
    )
  }
}
