SimMetrics
==========

SimMetrics is a java library of Similarity or Distance Metrics, e.g. Levenshtein Distance, that provide float based similarity measures between String Data. All metrics return consistant measures rather than unbounded similarity scores. 

SimMetrics is based on the [SimMetrics Library](http://sourceforge.net/projects/simmetrics/) by Sam Chapman.

## Usage ##

For a quick and easy use [StringMetrics](/simmetrics-core/src/main/java/org/simmetrics/StringMetrics.java) contains a collection of well known metrics.

```
	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";

	StringMetric metric = StringMetrics.cosineSimilarity();

	float result = metric.compare(str1, str2); //0.4472
```

The [StringMetricBuilder](/simmetrics-core/src/main/java/org/simmetrics/StringMetricBuilder.java) can be used to improve the effectiveness of a metric by adding simplifier steps that remove upper case, non-word characters, ect or using different tokenizers. These steps are generally domain dependent.

```
	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";

	StringMetric metric = new StringMetricBuilder()
			.with(new CosineSimilarity<String>())
			.simplify(new CaseSimplifier.Lower())
			.simplify(new NonWordCharacterSimplifier())
			.tokenize(new WhitespaceTokenizer())
			.build();

	float result = metric.compare(str1, str2); //0.5590
```

## String Metric Builder ##

To improve the effectiveness of a comparison different combinations of simplification, tokenization, filtering and comparison schemes can be tried. The String Metric Builder supports the creation of a work flow that applies simplification, tokenization and filtering before comparison.

### Simplification ###

Simplfication increases the effectiveness of a metric by removing noise and dimensionality of the strings. The process maps a a complex string such as `Chilpéric II son of Childeric II` to a simpler format `chilperic ii son of childeric ii`. This allows string from different sources to be compared in the same normal form.

Custom simplification can added by implementing the Simplifier interface.

```
	public class NonWordCharacterSimplifier implements Simplifier {

		@Override
		public String simplify(String input) {
		    return input.replaceAll("\\W", " ");
		}
	}
```

A custom simplifier can be added onto any metric by using the StringMetricBuilder. 

```
	final String str1 = "This is a sentence. It is made of words";
	final String str2 = "This sentence is similair. It has almost the same words";

	StringMetric metric = new StringMetricBuilder()
			.with(new CosineSimilarity<String>())
			.simplify(new CaseSimplifier.Lower())
			.simplify(new NonWordCharacterSimplifier())
			.tokenize(new WhitespaceTokenizer())
			.build();

	final float result = metric.compare(str1, str2); //0.5590
```

### Tokenization ###

Tokenization cuts up a string into tokens e.g. `[chilperic, ii, son, of, childeric, ii]`.Tokenization can also be done repeatedly by tokenizing the individual tokens e.g. `[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]`.

````
	return new StringMetricBuilder()
			.with(new SimonWhite<String>())
			.tokenize(new WhitespaceTokenizer())
			.tokenize(QGramTokenizer.Q2)
			.build();
````

The method of tokenization changes the effectiveness of a metric depending on the context. A whitespace tokenizer might be more useful to measure similarity between large bodies of texts whiles a q-gram tokenizer will work more effectively for matching words.

Tokenization can be done by any class implementing the Tokenizer interface and is required for all metrics that work on collections of tokens rather then whole strings; ListMetrics and SetMetrics.

```
	public final class WhitespaceTokenizer implements Tokenizer {

		@Override
		public final List<String> tokenizeToList(final String input) {
			return Arrays.asList(pattern.split(input));
		}
		
		@Override
		public Set<String> tokenizeToSet(final String input) {
			return new HashSet<>(tokenizeToList(input));
		}
	
	}
```


## Caching ##

Simplification and tokenization are complex and expensive operations. When comparing one string against a collection of strings these two operations are done repeatedly for a single string - a common use case when searching for a match. With a simple caching mechnism this overhead can be reduced. Being able to do this programmatically allows these to be inserted when needed.

## Refactoring & Redesign ##

This fork is aimed at refactoring and cleaning up the SimMetrics project. This is done with the following aims: 

 * Support general workflow of simplication, tokenization and comparision.
 * Reduce clutter on the InterfaceSimilarityMetrics.
 * Allow caching of expensive tokenization and simplification operations.
 * Allow for easy anonymous-subclassing-style configuration.

## Caching ##
TODO: Configure caching. Show provided caching in `StringMetrics`.

## Performance Testing ##

TODO: Show performance test usage.
