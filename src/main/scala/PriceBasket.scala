object PriceBasket {
  def main(args: Array[String]): Unit = {
    if (args.isEmpty) {
      println("Usage: PriceBasket item1 item2 item3 ...")
      return
    }

    val summary = BasketCalculator.calculate(args.toList)

    println(f"Subtotal:£${summary.subtotal}%.2f")
    summary.discountDescriptions.foreach(println)
    println(f"Total:£${summary.total}%.2f")
  }
}
