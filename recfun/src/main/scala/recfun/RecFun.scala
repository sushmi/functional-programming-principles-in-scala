package recfun

object RecFun extends RecFunInterface {

  def main(args: Array[String]): Unit = {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(s"${pascal(col, row)} ")
      println()
    }

    val s1 = "(if (zero? x) max (/ 1 x))"
    println(s"Is $s1 balanced ? " + balance(s1.toList))

    val s2 = "I told him (that it’s not (yet) done). (But he wasn’t listening)"
    println(s"Is $s2 balanced ? " + balance(s2.toList))

    val s3 = ":-)"
    println(s"Is $s3 balanced ? " + balance(s3.toList))

    val s4 = "())("
    println(s"Is $s4 balanced ? " + balance(s4.toList))

    println(s"Is empty char list balanced ? " + balance(List.empty))
    println(s"Is ) char list balanced ? " + balance(List(')')))
    println(s"Is ( char list balanced ? " + balance(List('(')))

    println(s"possible change count: " + countChange(4, List(1,2)))

  }

  private def factorial(n: Int) = {
    def factIter(acc: Int, x: Int): Int = {
      if( x == 0) acc
      else factIter(acc * x, x -1)
    }

    factIter(1, n)
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = factorial(r) / (factorial(r - c) * factorial(c))

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    val openBrace: Char = '('
    val closeBrace: Char = ')'

    def checkBracketsOnly(charList: List[Char], stack: List[Char]): List[Char] = charList match {
      case Nil => stack
      case h :: t => {
        if (h == closeBrace && stack.headOption.getOrElse("") == openBrace) checkBracketsOnly(t, stack.tail)
        else if (h == openBrace || h == closeBrace) checkBracketsOnly(t, h +: stack)
        else checkBracketsOnly(t, stack)
      }
    }
    checkBracketsOnly(chars, List.empty).isEmpty
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = coins match {
      case _ if money == 0 => 1
      case h :: t if money > 0 => countChange(money - h, h :: t) + countChange(money, t)
      case _ => 0
    }
}
