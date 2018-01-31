package carldata.sf.core

import java.time.temporal.ChronoUnit
import java.time.{Instant, LocalDateTime, ZoneOffset}

import carldata.series.TimeSeries
import carldata.sf.{Compiler, Interpreter}
import org.scalatest._


/**
  * Tests for Time Series core
  */
class TimeSeriesTest extends FlatSpec with Matchers {

  "TimeSeriesCore" should "run test function" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = xs
      """.stripMargin
    val tsv: TimeSeries[Float] = TimeSeries.empty
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(tsv))
    }
    val ts = result.right.get.asInstanceOf[TimeSeries[Float]]
    ts.length shouldBe 0
  }

  it should "map over values" in {
    val code =
      """
        |def f(a: Number): Number = a+2
        |
        |def main(xs: TimeSeries): TimeSeries = map(xs, f)
      """.stripMargin
    val tsv = TimeSeries.fromTimestamps(Seq((1L, 1f), (2L, 1f), (3L, 1f)))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(tsv))
    }
    val ts = result.right.get.asInstanceOf[TimeSeries[Float]]
    ts.values.sum shouldBe 9
  }

  it should "differentiate values" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = differentiate(xs)
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(60),
      now.plusSeconds(75))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(idx.tail, Vector(1f, 1f, 0f, -1f, 4f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts))
    }
    result shouldBe Right(expected)
  }

  it should "differentiate time" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = delta_time(xs)
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(34), now.plusSeconds(56), now.plusSeconds(57))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(idx.tail, Vector(15f, 15f, 4f, 22f, 1f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts))
    }
    result shouldBe Right(expected)
  }

  it should "find average in fixed-interval series" in {
    val code =
      """
        |def main(xs: TimeSeries, d: String): TimeSeries = groupby_avg(xs, dt_convert(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:01:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(180))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(Vector(now, now.plusSeconds(60), now.plusSeconds(3 * 60)), Vector(2.25f, 2f, 6f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, "*/1 * * * *"))
    }
    result shouldBe Right(expected)
  }

  it should "find maximum in fixed-interval series" in {
    val code =
      """
        |def main(xs: TimeSeries, d: String): TimeSeries = groupby_max(xs, dt_convert(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:01:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(180))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(Vector(now, now.plusSeconds(60), now.plusSeconds(3 * 60)), Vector(3f, 2f, 6f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, "*/1 * * * *"))
    }
    result shouldBe Right(expected)
  }

  it should "find minimum in fixed-interval series" in {
    val code =
      """
        |def main(xs: TimeSeries, d: String): TimeSeries = groupby_min(xs, dt_convert(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:01:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(80))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(Vector(now, now.plusSeconds(60)), Vector(1f, 2f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, "*/1 * * * *"))
    }
    result shouldBe Right(expected)
  }

  it should "find median in fixed-interval series" in {
    val code =
      """
        |def main(xs: TimeSeries, d: String): TimeSeries = groupby_median(xs, dt_convert(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:01:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(80), now.plusSeconds(85))
    val ts = TimeSeries(idx, Vector(3f, 2f, 1f, 3f, 2f, 6f, 7f))
    val expected = TimeSeries(Vector(now, now.plusSeconds(60)), Vector(2.5f, 6f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, "*/1 * * * *"))
    }
    result shouldBe Right(expected)
  }

  it should "find sum in fixed-interval series" in {
    val code =
      """
        |def main(xs: TimeSeries, d: String): TimeSeries = groupby_sum(xs, dt_convert(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:01:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(80))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(Vector(now, now.plusSeconds(60)), Vector(9f, 8f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, "*/1 * * * *"))
    }
    result shouldBe Right(expected)
  }

  it should "shift time forward" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number): TimeSeries = shift(xs, hours(d))
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60),
      now.plusSeconds(60 * 60), now.plusSeconds(70 * 60))
    val idx2 = Vector(now.plusSeconds(60 * 60), now.plusSeconds(75 * 60), now.plusSeconds(90 * 60), now.plusSeconds(105 * 60),
      now.plusSeconds(120 * 60), now.plusSeconds(130 * 60))
    val vs = Vector(1f, 2f, 3f, 4f, 5f, 6f)
    val ts = TimeSeries(idx, vs)
    val expected = TimeSeries(idx2, vs)
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f))
    }
    result shouldBe Right(expected)
  }

  it should "slice timeseries" in {
    val code =
      """
        |def main(xs: TimeSeries, sd: DateTime, ed: DateTime): TimeSeries = slice(xs, sd, ed)
      """.stripMargin
    val now = Instant.EPOCH
    val startDate = now.plusSeconds(15 * 60)
    val endDate = now.plusSeconds(60 * 60)
    val idx = Vector(now, now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60),
      now.plusSeconds(59 * 60), now.plusSeconds(60 * 60))
    val idx2 = Vector(now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60), now.plusSeconds(59 * 60))
    val vs = Vector(1f, 2f, 3f, 4f, 5f, 6f)
    val vs2 = Vector(2f, 3f, 4f, 5f)
    val ts = TimeSeries(idx, vs)
    val expected = TimeSeries(idx2, vs2)
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, startDate, endDate))
    }
    result shouldBe Right(expected)
  }

  it should "find cumulative" in {
    val code =
      """
        |def main(xs: TimeSeries, f: DateTime => DateTime): TimeSeries = running_total(xs, f)
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60),
      now.plusSeconds(60 * 60), now.plusSeconds(70 * 60))

    def f(d: Instant): Instant = d.truncatedTo(ChronoUnit.HOURS)

    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 4f, 5f, 6f))
    val expected = TimeSeries(idx, Vector(1f, 3f, 6f, 10f, 5f, 11f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, f(_)))
    }
    result shouldBe Right(expected)
  }

  it should "calculate rolling avg" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number): TimeSeries = rolling_avg(xs, minutes(d))
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(80))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(idx, Vector(1f, 1.5f, 2.0f, 2.25f, 2.5f, 3.5f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f))
    }
    result shouldBe Right(expected)
  }

  it should "calculate rolling sum" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number): TimeSeries = rolling_sum(xs, minutes(d))
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15), now.plusSeconds(30), now.plusSeconds(45), now.plusSeconds(65), now.plusSeconds(80))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 3f, 2f, 6f))
    val expected = TimeSeries(idx, Vector(1f, 3f, 6f, 9f, 10f, 14f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f))
    }
    result shouldBe Right(expected)
  }

  it should "step index" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number): TimeSeries = step(xs, minutes(d))
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(60 * 60), now.plusSeconds(2 * 60 * 60))
    val idx2 = Vector(now, now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60), now.plusSeconds(60 * 60),
      now.plusSeconds(75 * 60), now.plusSeconds(90 * 60), now.plusSeconds(105 * 60))
    val ts = TimeSeries(idx, Vector(10f, 8f, 12f))
    val expected = TimeSeries(idx2, Vector(2, 2, 2, 2, 3, 3, 3, 3))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 15f))
    }
    result shouldBe Right(expected)
  }

  it should "find previous value" in {
    val code =
      """
        |def main(xs: TimeSeries):TimeSeries = prev(xs)
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:00:02").toInstant(ZoneOffset.UTC)
    val idx = Vector(now.plusSeconds(1 * 60), now.plusSeconds(2 * 60), now.plusSeconds(3 * 60))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f))
    val expected = TimeSeries(Vector(now.plusSeconds(2 * 60), now.plusSeconds(3 * 60)), Vector(1f, 2f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts))
    }
    result shouldBe Right(expected)
  }

  it should "interpolate missing values" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number): TimeSeries = interpolate(xs, minutes(d))
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:00:02").toInstant(ZoneOffset.UTC)
    val idx = Vector(now.plusSeconds(1 * 60), now.plusSeconds(4 * 60), now.plusSeconds(6 * 60))
    val ts = TimeSeries(idx, Vector(1f, 4f, 6f))
    val expected = TimeSeries(Vector(now.plusSeconds(1 * 60), now.plusSeconds(3 * 60), now.plusSeconds(5 * 60)),
      Vector(1f, 3f, 5f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 2f))
    }
    result shouldBe Right(expected)
  }

  it should "fill missing values with default value" in {
    val code =
      """
        |def main(xs: TimeSeries, d: Number, v: Number): TimeSeries = fill_missing(xs, minutes(d), v)
      """.stripMargin
    val now = LocalDateTime.parse("2015-01-01T00:00:02").toInstant(ZoneOffset.UTC)
    val idx = Vector(now.plusSeconds(1 * 60), now.plusSeconds(3 * 60), now.plusSeconds(4 * 60))
    val ts = TimeSeries(idx, Vector(1f, 4f, 6f))
    val expected = TimeSeries(Vector(now.plusSeconds(1 * 60), now.plusSeconds(2 * 60), now.plusSeconds(3 * 60), now.plusSeconds(4 * 60)),
      Vector(1f, 0f, 4f, 6f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f, 0f))
    }
    result shouldBe Right(expected)
  }

  it should "repeat values" in {
    val code =
      """
        |def main(xs: TimeSeries, sd: DateTime, ed: DateTime, d: Number): TimeSeries = repeat(xs, sd, ed, hours(d))
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(now, now.plusSeconds(15 * 60), now.plusSeconds(30 * 60), now.plusSeconds(45 * 60))
    val ts = TimeSeries(idx, Vector(1, 4, 6, 8))
    val idx2 = Vector(now.plusSeconds(60 * 60), now.plusSeconds(75 * 60), now.plusSeconds(90 * 60), now.plusSeconds(105 * 60))
    val expected = TimeSeries(idx ++ idx2, Vector(1, 4, 6, 8, 1, 4, 6, 8))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, now, now.plusSeconds(2 * 60 * 60), 1f))
    }
    result shouldBe Right(expected)
  }

  it should "find time weight average #1" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = time_weight_average(xs, minutes(5))
      """.stripMargin
    val now = LocalDateTime.parse("2014-05-01T08:00:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(now.plusSeconds(1 * 60), now.plusSeconds(6 * 60), now.plusSeconds(11 * 60), now.plusSeconds(16 * 60),
      now.plusSeconds(21 * 60))
    val idx2 = Vector(now.plusSeconds(5 * 60), now.plusSeconds(10 * 60), now.plusSeconds(15 * 60), now.plusSeconds(20 * 60),
      now.plusSeconds(25 * 60))
    val ts = TimeSeries(idx, Vector(0.123912f, 0.123748004f, 0.12717001f, 0.13364601f, 0.136148f))
    val expected = TimeSeries(idx2, Vector(0.099061996f, 0.123824f, 0.12599f, 0.13250801f, 0.13593f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts))
    }
    TimeSeries.almostEqual(expected, result.right.get.asInstanceOf[TimeSeries[Float]], 0.001f) shouldBe true
  }

  it should "find time weight average #2" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = time_weight_average(xs, minutes(5))
      """.stripMargin
    val now = LocalDateTime.parse("2014-05-01T08:00:00").toInstant(ZoneOffset.UTC)
    val idx = Vector(1, 2, 3, 4, 5, 6, 7, 8, 9).map(i => now.plusSeconds(i * 60))
    val idx2 = Vector(5, 10).map(i => now.plusSeconds(i * 60))
    val ts = TimeSeries(idx, Vector(0.12359f, 0.12408999f, 0.12387f, 0.12376f, 0.12425f, 0.12348001f, 0.12327f, 0.12387f, 0.12425f))
    val expected = TimeSeries(idx2, Vector(0.099061996f, 0.123824f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts))
    }
    TimeSeries.almostEqual(expected, result.right.get.asInstanceOf[TimeSeries[Float]], 0.001f) shouldBe true

  }

  it should "find discrete values" in {
    val code =
      """
        |def main(xs: TimeSeries, v: Number): TimeSeries = discrete(xs, v)
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(0, 5, 10, 15, 20, 25, 30).map(i => now.plusSeconds(i * 60))
    val ts = TimeSeries(idx, Vector(1f, 2f, 3f, 5f, 8f, 3f, 2f))
    val expected = TimeSeries(idx.tail, Vector(1f, 1f, 2f, 3f, 5f, 9f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 10f))
    }
    result shouldBe Right(expected)
  }

  it should "interpolate outliers" in {
    val code =
      """
        |def main(xs: TimeSeries, b: Number, t: Number): TimeSeries = interpolate_outliers(xs, b ,t)
      """.stripMargin
    val now = Instant.EPOCH
    val idx = Vector(1, 2, 3, 4, 5, 6).map(i => now.plusSeconds(i))
    val ts = TimeSeries(idx, Vector(3f, 20f, 5f, 6f, 0f, 8f))
    val expected = TimeSeries(idx, Vector(3f, 4f, 5f, 6f, 7f, 8f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f, 10f))
    }
    result shouldBe Right(expected)
  }

  it should "const" in {
    val code =
      """
        |def main(xs: TimeSeries): TimeSeries = const(xs, 5)
      """.stripMargin
    val now = Instant.now()
    val idx = Vector(now.plusSeconds(1), now.plusSeconds(2), now.plusSeconds(4), now.plusSeconds(5))
    val xs = TimeSeries(idx, Vector(3f, 20f, 5f, 6f))
    val expected = TimeSeries(idx, Vector(5f, 5f, 5f, 5f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(xs))
    }
    result shouldBe Right(expected)
  }

  it should "remove outliers" in {
    val code =
      """
        |def main(xs: TimeSeries, b: Number, t: Number): TimeSeries = remove_outliers(xs, b ,t)
      """.stripMargin
    val now = Instant.now
    val idx = Vector(1, 2, 3, 4, 5, 6).map(i => now.plusSeconds(i))
    val idx2 = Vector(1, 3, 4, 6).map(i => now.plusSeconds(i))
    val ts = TimeSeries(idx, Vector(3f, 20f, 5f, 6f, 0f, 8f))
    val expected = TimeSeries(idx2, Vector(3f, 5f, 6f, 8f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, 1f, 10f))
    }
    result shouldBe Right(expected)
  }

  it should "solve if statement: compare time series with const" in {
    val code =
      """
        |def main(a: TimeSeries, b: TimeSeries): TimeSeries = if a > 1 then a else b
      """.stripMargin
    val now = Instant.now
    val idx = Vector(1, 2, 3, 4).map(i => now.plusSeconds(i))
    val ts = TimeSeries(idx, Vector(3f, 20f, 3f, 0f))
    val ts2 = TimeSeries(idx, Vector(3f, 18f, 5f, 1f))
    val expected = TimeSeries(idx, Vector(3f, 20f, 3f, 1f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, ts2))
    }
    result shouldBe Right(expected)
  }

  it should "solve if statement: compare two time series (different size)" in {
    val code =
      """
        |def main(a: TimeSeries, b: TimeSeries): TimeSeries = if a == b then a else b
      """.stripMargin
    val now = Instant.now
    val idx = Vector(1, 2, 3, 4).map(i => now.plusSeconds(i))
    val ts = TimeSeries(idx, Vector(3f, 20f, 3f, 0f))
    val ts2 = TimeSeries(idx.tail, Vector(20f, 3f, 1f))
    val expected = TimeSeries(idx.tail, Vector(20f, 3f, 1f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, ts2))
    }
    result shouldBe Right(expected)
  }

  it should "solve if statement: compare two time series (same size)" in {
    val code =
      """
        |def main(a: TimeSeries, b: TimeSeries): TimeSeries = if a > b then a else b
      """.stripMargin
    val now = Instant.now
    val idx = Vector(1, 2, 3, 4).map(i => now.plusSeconds(i))
    val ts = TimeSeries(idx, Vector(3f, 20f, 3f, 0f))
    val ts2 = TimeSeries(idx, Vector(3f, 18f, 5f, 1f))
    val expected = TimeSeries(idx, Vector(3f, 20f, 5f, 1f))
    val result = Compiler.make(code).flatMap { exec =>
      Interpreter(exec).run("main", Seq(ts, ts2))
    }
    result shouldBe Right(expected)
  }

}