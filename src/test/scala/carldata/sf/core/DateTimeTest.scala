package carldata.sf.core

import java.time.format.DateTimeFormatter

import carldata.sf.Runtime.{NumberValue, StringValue, Value}
import carldata.sf.core.DateTimeModule.DateTimeValue
import carldata.sf.{Compiler, Interpreter}
import org.scalatest._


class DateTimeTest extends FlatSpec with Matchers {
  val libs = Seq(DateTimeModule.header)
  val dt = new DateTimeModule()

  "Datetime" should "convert date from string" in {
    val code =
      """
        |def main(s: String): DateTime = date(s)
      """.stripMargin

    val params = Seq(StringValue("2017-08-21T10:30:30"))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    toUTC(result.right.get) shouldBe "2017-08-21T10:30:30"
  }

  it should "adjust date" in {
    val code =
      """
        |def main(dt: DateTime,y: Number, m: Number, d:Number): DateTime = adjust_date(dt,y,m,d)
      """.stripMargin

    val params = Seq(dt.$from_date(NumberValue(2017), NumberValue(8), NumberValue(21)), NumberValue(2016), NumberValue(1), NumberValue(1))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    result.right.get.toString shouldBe dt.$from_date(NumberValue(2016), NumberValue(1), NumberValue(1)).toString
  }

  it should "adjust time" in {
    val code =
      """
        |def main(dt: DateTime,y: Number, m: Number, d:Number): DateTime = adjust_time(dt,y,m,d)
      """.stripMargin

    val params = Seq(dt.$from_date(NumberValue(2017), NumberValue(8), NumberValue(21)), NumberValue(10), NumberValue(30), NumberValue(30))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    toUTC(result.right.get) shouldBe "2017-08-21T10:30:30"
  }

  it should "get day of week" in {
    val code =
      """
        |def main(dt: DateTime): Number = day_of_week(dt)
      """.stripMargin

    val result1 = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", Seq(dt.$from_date(NumberValue(2017), NumberValue(8), NumberValue(21))))
    }
    result1.right.get shouldBe NumberValue(1)

    val result2 = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", Seq(dt.$from_date(NumberValue(2017), NumberValue(8), NumberValue(23))))
    }
    result2.right.get shouldBe NumberValue(3)

    val result3 = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", Seq(dt.$from_date(NumberValue(2017), NumberValue(8), NumberValue(26))))
    }
    result3.right.get shouldBe NumberValue(6)

  }

  it should "floor hours" in {
    val code =
      """
        |def main(dt: DateTime): DateTime = floor_hours(dt)
      """.stripMargin

    val params = Seq(dt.$from_datetime(NumberValue(2017), NumberValue(8), NumberValue(21), NumberValue(11), NumberValue(31), NumberValue(36), NumberValue(0)))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    toUTC(result.right.get) shouldBe "2017-08-21T00:31:36"
  }

  it should "floor minutes" in {
    val code =
      """
        |def main(dt: DateTime): DateTime = floor_minutes(dt)
      """.stripMargin

    val params = Seq(dt.$from_datetime(NumberValue(2017), NumberValue(8), NumberValue(21), NumberValue(11), NumberValue(31), NumberValue(36), NumberValue(0)))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    toUTC(result.right.get) shouldBe "2017-08-21T11:00:36"
  }

  it should "floor seconds" in {
    val code =
      """
        |def main(dt: DateTime): DateTime = floor_seconds(dt)
      """.stripMargin

    val params = Seq(dt.$from_datetime(NumberValue(2017), NumberValue(8), NumberValue(21), NumberValue(11), NumberValue(31), NumberValue(36), NumberValue(0)))
    val result = Compiler.compile(code, libs).flatMap { exec =>
      Interpreter(exec).run("main", params)
    }
    toUTC(result.right.get) shouldBe "2017-08-21T11:31:00"
  }

  def toUTC(v: Value): String = {
    v.asInstanceOf[DateTimeValue].dt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
  }

}