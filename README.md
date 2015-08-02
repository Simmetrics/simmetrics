[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.mpkorstanje/simmetrics/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.mpkorstanje/simmetrics-core)
[![Build Status](https://travis-ci.org/Simmetrics/simmetrics.svg)](https://travis-ci.org/Simmetrics/simmetrics)
[![Coverage Status](https://coveralls.io/repos/Simmetrics/simmetrics/badge.svg?branch=develop&service=github)](https://coveralls.io/github/Simmetrics/simmetrics?branch=develop)

SimMetrics 
==========
SimMetrics is a java library of Similarity or Distance Metrics, e.g. Levenshtein distance, Cosine similarity, that provide float based similarity measures between String data. All metrics return consistent measures rather than unbounded similarity scores. 

## Usage ##

For a quick and easy use [StringMetrics](./simmetrics-core/src/main/java/org/simmetrics/StringMetrics.java) contains a collection of well known string metrics.

```java
	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";
	
	StringMetric metric = StringMetrics.cosineSimilarity();
	
	float result = metric.compare(str1, str2); //0.4472
```

The [StringMetricBuilder](./simmetrics-core/src/main/java/org/simmetrics/StringMetricBuilder.java) is a convenience tool to build string metrics. Any class implementing StringMetric, ListMetric, SetMetric can be used to build a string metric. The builder supports simplification, tokenization, token-filtering, and caching.

For a terse syntax use `import static org.simmetrics.StringMetricBuilder.with;`

```java
	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";

	StringMetric metric =
			with(new CosineSimilarity<String>())
			.simplify(new Case.Lower(Locale.ENGLISH))
			.simplify(new NonWordCharacter())
			.tokenize(new Whitespace())
			.build();

	float result = metric.compare(str1, str2); //0.5590
```
