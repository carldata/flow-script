package carldata.sf.core

import java.time._
import java.time.temporal.ChronoUnit

import carldata.series.{Gen, Outliers, TimeSeries}
import carldata.sf.Runtime

/**
  * Extend FlowScript with operations on the Time Series
  */
object TimeSeriesModule {

  // Header which will be provided to the compiler
  val header: String =
    """
      |external def map(xs: TimeSeries, f: Number => Number): TimeSeries
      |external def differentiate(xs: TimeSeries): TimeSeries
      |external def discrete(xs: TimeSeries, d: Duration, v: Number): TimeSeries
      |external def delta_time(xs: TimeSeries): TimeSeries
      |external def fill_missing(xs: TimeSeries, d: Duration, v: Number): TimeSeries
      |external def groupby_avg(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def groupby_max(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def groupby_median(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def groupby_min(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def groupby_sum(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def interpolate(xs: TimeSeries, d: Duration): TimeSeries
      |external def interpolate_outliers(xs: TimeSeries, bottom: Number, top: Number): TimeSeries
      |external def prev(xs: TimeSeries): TimeSeries
      |external def remove_outliers(xs: TimeSeries, bottom: Number, top: Number): TimeSeries
      |external def repeat(xs: TimeSeries, osd: DateTime, oed: DateTime, rsd: DateTime, red: DateTime): TimeSeries
      |external def rolling_avg(xs: TimeSeries, d: Duration): TimeSeries
      |external def rolling_sum(xs: TimeSeries, d: Duration): TimeSeries
      |external def running_total(xs: TimeSeries, f: DateTime => DateTime): TimeSeries
      |external def shift(xs: TimeSeries, d: Duration): TimeSeries
      |external def slice(xs: TimeSeries, sd: DateTime, ed: DateTime): TimeSeries
      |external def step(xs: TimeSeries, d: Duration): TimeSeries
      |external def time_weight_average(xs: TimeSeries, d: Duration): TimeSeries
      |external def const(xs: TimeSeries, v: Number): TimeSeries
    """.stripMargin

  def apply(): TimeSeriesModule = new TimeSeriesModule()
}

class TimeSeriesModule extends Runtime {
  // Function definition
  def $map(xs: TimeSeries[Double], f: Double => Double): TimeSeries[Double] = xs.mapValues(f)

  def $differentiate(xs: TimeSeries[Double]): TimeSeries[Double] = TimeSeries.differentiate(xs)

  def $delta_time(xs: TimeSeries[Double]): TimeSeries[Double] = {
    if (xs.isEmpty) xs
    else {
      val idx = xs.index.tail
      val vs = xs.index.tail.zip(xs.index)
        .map(x => x._1.getEpochSecond - x._2.getEpochSecond)
        .map(_.toDouble)
      TimeSeries(idx, vs)
    }
  }

  def $fill_missing(xs: TimeSeries[Double], d: Duration, v: Double): TimeSeries[Double] = xs.resampleWithDefault(d, v)


  /**
    * This function calculate diffOverflow for each period for input TimeSeries,
    * then resample by sum values in every period and put 0 if there is no data in this period
    */
  def $discrete(xs: TimeSeries[Double], d: Duration, v: Double): TimeSeries[Double] = {
    val start: Instant = floor_time(xs.index.head.truncatedTo(ChronoUnit.HOURS), xs.index.head, d)

    def ceiling(current: Instant): Instant = {
      val diff = ChronoUnit.SECONDS.between(start, current)

      def floor = start.plusSeconds((diff / d.getSeconds) * d.getSeconds + d.getSeconds)

      val minutes = (LocalDateTime.ofInstant(floor, ZoneOffset.UTC).getMinute / (d.getSeconds / 60)) * (d.getSeconds / 60)
      LocalDateTime.ofInstant(floor, ZoneOffset.UTC).withMinute(minutes.toInt).withSecond(0).toInstant(ZoneOffset.UTC)
    }

    def sum(ys: Seq[(Instant, Double)]): Double = ys.map(_._2).sum

    val ys = if (v == 0) TimeSeries.diffOverflow(xs).groupByTime(ceiling, sum)
    else {
      TimeSeries.diffOverflow(xs, v).groupByTime(ceiling, sum)
    }

    val ps = if (ys.index.last != xs.index.last.plusSeconds(d.getSeconds)) {
      val idx = start.plusSeconds(d.getSeconds) +: ys.index :+ xs.index.last.plusSeconds(d.getSeconds)
      val vs = 0d +: ys.values :+ 0d
      (idx, vs)
    }
    else if (ys.index.head != xs.index.head.plusSeconds(d.getSeconds)) {
      val idx = start.plusSeconds(d.getSeconds) +: ys.index
      val vs = 0d +: ys.values
      (idx, vs)
    }
    else (ys.index, ys.values)
    TimeSeries(ps._1, ps._2).addMissing(d, (_, _, _) => 0.0)
  }

  def $interpolate(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = TimeSeries.interpolate(xs, d)

  def $interpolate_outliers(xs: TimeSeries[Double], bottom: Double, top: Double): TimeSeries[Double] = {
    def f(x: Double, y: Double): Double = {
      (x + y) / 2
    }

    Outliers.interpolate(xs, bottom, top, f)
  }

  def $remove_outliers(xs: TimeSeries[Double], bottom: Double, top: Double): TimeSeries[Double] = Outliers.remove(xs, bottom, top)

  def $groupby_avg(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = {
    def g(seq: Seq[Double]): Double = seq.sum / seq.length

    if (xs.isEmpty) xs
    else {
      xs.groupByTime(f, x => g(x.unzip._2))
    }
  }

  def $groupby_max(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = xs.groupByTime(f, _.unzip._2.max)

  def $groupby_median(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = {
    def g(seq: Seq[Double]): Double = {
      val sorted = seq.sorted
      val center = Math.abs(sorted.length / 2)
      if (seq.length % 2 == 0) {
        (sorted(center) + sorted(center - 1)) / 2
      }
      else {
        sorted(center)
      }
    }

    if (xs.isEmpty) xs
    else {
      xs.groupByTime(f, x => g(x.unzip._2))
    }


  }

  def $groupby_min(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = xs.groupByTime(f, _.unzip._2.min)


  def $groupby_sum(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = xs.groupByTime(f, _.unzip._2.sum)

  def $prev(xs: TimeSeries[Double]): TimeSeries[Double] = {
    if (xs.isEmpty) xs
    else {
      val idx = xs.index.tail
      val vs = xs.values.take(idx.size)
      TimeSeries(idx, vs)
    }
  }

  /**
    * @param xs  Source series
    * @param osd Start date of output series
    * @param oed End date of output series
    * @param rsd Start date of pattern to repeat from source data
    * @param red End date of pattern to repeat from source data
    */
  def $repeat(xs: TimeSeries[Double], osd: Instant, oed: Instant, rsd: Instant, red: Instant): TimeSeries[Double] = {
    Gen.repeat(xs.slice(rsd, red), osd, oed)
  }


  def $rolling_avg(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = {
    def f(v: Seq[Double]): Double = v.sum / v.length

    xs.rollingWindow(d, f)
  }

  def $rolling_sum(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = xs.rollingWindow(d, _.sum)

  def $running_total(xs: TimeSeries[Double], f: Instant => Instant): TimeSeries[Double] = TimeSeries.integrateByTime(xs, f)

  def $shift(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = xs.shiftTime(d)

  def $slice(xs: TimeSeries[Double], sd: Instant, ed: Instant): TimeSeries[Double] = xs.slice(sd, ed)

  def $step(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = TimeSeries.step(xs, d)

  def $time_weight_average(xs: TimeSeries[Double], d: Duration): TimeSeries[Double] = {
    def f[V](x1: (Instant, V), x2: (Instant, V), tsh: Instant) = x1._2

    val xs2 = TimeSeries[Double](xs.index.head.truncatedTo(ChronoUnit.HOURS) +: xs.index, 0d +: xs.values)
      .addMissing(d, f)

    def g[V](ys0: Seq[(Instant, V)]): V = {
      val ys = if (ys0.head._1 != xs.index.head && ys0.head._1 == xs2.index.head) ys0.tail.sortBy(_._1) else ys0.sortBy(_._1)
      val unzipped = ys.unzip
      val lastIndex = floor_time(xs2.index.head, unzipped._1.head, d).plus(d)
      val deltas = (unzipped._1.tail :+ lastIndex).zip(unzipped._1)
        .map(x => x._1.getEpochSecond - x._2.getEpochSecond)
        .map(_.toDouble)

      unzipped._2
        .zip(deltas)
        .map(x => x._2 * (x._1.asInstanceOf[Double] / d.getSeconds))
        .sum
        .asInstanceOf[V]
    }

    val ys = xs2.groupByTime(floor_time(xs2.index.head, _, d), g)
    TimeSeries(ys.index.tail :+ ys.index.last.plusSeconds(d.getSeconds), ys.values)
  }

  def $const(xs: TimeSeries[Double], v: Double): TimeSeries[Double] = {
    xs.mapValues(_ => v)
  }

  private def floor_time(st: Instant, ct: Instant, d: Duration): Instant = {
    val diff = ChronoUnit.SECONDS.between(st, ct)
    st.plusSeconds((diff / d.getSeconds) * d.getSeconds)
  }

}

