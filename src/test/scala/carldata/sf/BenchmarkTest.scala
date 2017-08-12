package carldata.sf

import carldata.sf.Runtime.NumberValue
import org.scalameter._
import org.scalatest._


/**
  * Measure library performance
  */
class BenchmarkTest extends FlatSpec with Matchers {

  "Interpreter" should "run simple script in less then 1 ms" in {
    val code =
      """
        |module Test1
        |
        |def main(a: Number, b: Number): Number = sin(a) + cos(b)
      """.stripMargin
    val exec = Compiler.compile(code, Seq(Core.header))
      .map { ast => new Interpreter(ast, new Core()) }
      .right.get

    val time: Quantity[Double] = withWarmer(new Warmer.Default).measure {
      exec.run("main", Seq(NumberValue(1), NumberValue(3)))
    }

    math.max(1.0, time.value) shouldBe 1.0
  }
}