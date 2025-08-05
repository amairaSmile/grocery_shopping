import org.scalatest.funsuite.AnyFunSuite

class BasketCalculatorTest extends AnyFunSuite {

  test("Apple discount applied correctly") {
    val summary = BasketCalculator.calculate(List("apple", "apple"))
    assert(summary.subtotal === 2.00)
    assert(summary.total === 1.80)
    assert(summary.discountDescriptions.exists(_.contains("Apple 10%")))
  }

  test("Bread discount with 2 soups") {
    val summary = BasketCalculator.calculate(List("soup", "soup", "bread"))
    assert(summary.subtotal === 2.10)
    assert(summary.total === 1.30)
    assert(summary.discountDescriptions.exists(_.contains("Bread free")))
  }

  test("No discounts") {
    val summary = BasketCalculator.calculate(List("milk"))
    assert(summary.subtotal === 1.30)
    assert(summary.total === 1.30)
    assert(summary.discountDescriptions.contains("No offers available"))
  }

  test("Unknown items are ignored") {
    val summary = BasketCalculator.calculate(List("apple", "xyz", "milk"))
    assert(summary.subtotal === 2.30)
    assert(summary.total < 2.30)
  }
}
