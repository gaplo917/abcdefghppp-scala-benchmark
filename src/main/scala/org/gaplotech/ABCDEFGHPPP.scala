package org.gaplotech

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

/**
  * Created by Gary Lo on 26/3/2016.
  */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Array(Mode.AverageTime))
@Warmup(iterations = 50, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 50, time = 100, timeUnit = TimeUnit.MILLISECONDS)
class ABCDEFGHPPP {

  @Benchmark
  def optimizedAndParallel = {
    val possibleToTry = (0 to 9)
      .toList
      .filter(x => x != 1)

    // first solve ef + gh = 111
    possibleToTry
      .combinations(4)
      .toStream
      .par
      .flatMap(_.permutations)
      .filter {
        case List(e, f, g, h) =>  e > 0 && g > 0
      }
      .filter {
        case List(e, f, g, h) => 111 - (g * 10 + h) ==  e * 10 + f
      }
      .toList
      .flatMap(efgh => possibleToTry.diff(efgh).permutations.map(_.take(4)).map(_ ++ efgh))
      .filter {
        case List(a,b,c,d,e,f,g,h) => a > 0 && c > 0 && a > c
      }
      .filter {
        case List(a,b,c,d,e,f,g,h) => a * 10 + b - (c * 10 + d) == e * 10 + f
      }
      .foreach{
        case List(a,b,c,d,e,f,g,h) =>
        // Hide printing log in benchmark
        // println(s"a=$a,b=$b,c=$c,d=$d,e=$e,f=$f,g=$g,h=$h,p=1")
      }
  }

  @Benchmark
  def optimized = {
    val possibleToTry = (0 to 9)
      .toList
      .filter(x => x != 1)

    // first solve ef + gh = 111
    possibleToTry
      .combinations(4)
      .toStream
      .flatMap(_.permutations)
      .filter {
        case List(e, f, g, h) =>  e > 0 && g > 0
      }
      .filter {
        case List(e, f, g, h) => 111 - (g * 10 + h) ==  e * 10 + f
      }
      .toList
      .flatMap(efgh => possibleToTry.diff(efgh).permutations.map(_.take(4)).map(_ ++ efgh))
      .filter {
        case List(a,b,c,d,e,f,g,h) => a > 0 && c > 0 && a > c
      }
      .filter {
        case List(a,b,c,d,e,f,g,h) => a * 10 + b - (c * 10 + d) == e * 10 + f
      }
      .foreach{
        case List(a,b,c,d,e,f,g,h) =>
        // Hide printing log in benchmark
        // println(s"a=$a,b=$b,c=$c,d=$d,e=$e,f=$f,g=$g,h=$h,p=1")
      }
  }
}
