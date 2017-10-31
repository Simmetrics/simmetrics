[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mpkorstanje/simmetrics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mpkorstanje/simmetrics-core)
[![Build Status](https://travis-ci.org/mpkorstanje/simmetrics.svg)](https://travis-ci.org/mpkorstanje/simmetrics)
[![Coverage Status](https://coveralls.io/repos/mpkorstanje/simmetrics/badge.svg?branch=develop&service=github)](https://coveralls.io/github/mpkorstanje/simmetrics?branch=develop)

SimMetrics 
==========
A Java library of similarity and distance metrics e.g. Levenshtein distance and Cosine similarity. All similarity metrics return normalized values rather than unbounded similarity scores. Distance metrics return non-negative unbounded scores.

## Usage ##

For a quick and easy use [StringMetrics](./simmetrics-core/src/main/java/org/simmetrics/metrics/StringMetrics.java) and [StringDistances](./simmetrics-core/src/main/java/org/simmetrics/metrics/StringDistances.java) contain a collection of well known similarity and distance metrics.

```java
String str1 = "This is a sentence. It is made of words";
String str2 = "This sentence is similar. It has almost the same words";

StringMetric metric = StringMetrics.cosineSimilarity();

float result = metric.compare(str1, str2); //0.4767
```

The [StringMetricBuilder](./simmetrics-core/src/main/java/org/simmetrics/builders/StringMetricBuilder.java) and [StringDistanceBuilder](./simmetrics-core/src/main/java/org/simmetrics/builders/StringDistanceBuilder.java) are convenience tools to build string similarity and distance metrics. Any class implementing Metric or Distance respectively can be used to build a metric. The builders support simplification, tokenization, token-filtering, token-transformation, and caching.
For usage see the [examples section](./simmetrics-example/src/main/java/org/simmetrics/example/).

For a terse syntax use `import static org.simmetrics.builders.StringMetricBuilder.with;`

```java
String str1 = "This is a sentence. It is made of words";
String str2 = "This sentence is similar. It has almost the same words";

StringMetric metric =
		with(new CosineSimilarity<String>())
		.simplify(Simplifiers.toLowerCase(Locale.ENGLISH))
		.simplify(Simplifiers.replaceNonWord())
		.tokenize(Tokenizers.whitespace())
		.build();

float result = metric.compare(str1, str2); //0.5720
```

Metrics that operate on lists, sets, or multisets are generic can be used to compare collections of arbitrary elements. The elements in the collection must implement equals and hashcode.

```java
Set<Integer> scores1 = new HashSet<>(asList(1, 1, 2, 3, 5, 8, 11, 19));
Set<Integer> scores2 = new HashSet<>(asList(1, 2, 4, 8, 16, 32, 64));

SetMetric<Integer> metric = new OverlapCoefficient<>();

float result = metric.compare(scores1, scores2); // 0.4285
```

## Unicode ##

Due to Java's [Unicode Character Representations](http://docs.oracle.com/javase/7/docs/api/java/lang/Character.html#unicode) some care must be taken when using string metrics that compare strings using char values. Using for example Smith-Waterman on a texts written in [Linear-A](https://en.wikipedia.org/wiki/Linear_A) will result in an unexpectedly high similarity as every other char is the same high surrogate. Metrics that operate on lists, sets, or multisets such as Cosine Similarity are not affected.

