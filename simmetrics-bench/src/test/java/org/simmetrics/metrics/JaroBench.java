/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 * 
 * Copyright (C) 2015 SimMetrics authors
 * 
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */
package org.simmetrics.metrics;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * On "twillouer" computer :
 * <p/>
 * <pre>
 *  JaroBench.compare  Web Database Applications  WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection  thrpt  200  814285,208 ± 10236,226  ops/s
 *  JaroBench.compare  Web Database Applications  WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection   avgt   15    1207,234 ±    65,726  ns/op
 *
 *
 * JaroBench.compare  Web Database Applications  WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection  thrpt  200  1303910,930 ± 8807,522  ops/s
 *  JaroBench.compare  Web Database Applications  WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection   avgt   15      763,480 ±   29,435  ns/op
 * </pre>
 */
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
public class JaroBench {

  private Jaro jaro;

  @Param("Web Database Applications")
  String one;

  @Param("WebRAD: Building Database Applications on the Web with Visual FoxPro and Web Connection")
  String two;

  @Setup
  public void setup() throws Throwable {
    jaro = new Jaro();
  }

  @Benchmark
  public void compare() {
    jaro.compare(one, two);
  }

}
