SimMetrics
==========

SimMetrics is a java library of Similarity or Distance Metrics, e.g. Levenshtein distance, Cosine similarity, that provide float based similarity measures between String data. All metrics return consistent measures rather than unbounded similarity scores. 

SimMetrics is based on the [SimMetrics Library](http://sourceforge.net/projects/simmetrics/) by Sam Chapman.

## Version 2 ##

This version was aimed at refactoring and cleaning up the SimMetrics project. This has yielded the follow results

 * Support for general workflow of simplification, tokenization and comparision.
 * Reduced clutter on the StringMetric interface.
 * Allow caching of expensive tokenization and simplification operations.
 * Allow for easy anonymous-subclassing-style configuration.
 * Solved various small bugs

## Usage ##

For a quick and easy use [StringMetrics](./simmetrics-core/src/main/java/org/simmetrics/StringMetrics.java) contains a collection of well known string metrics.

```

	String str1 = "This is a sentence. It is made of words";
	String str2 = "This sentence is similair. It has almost the same words";
	
	StringMetric metric = StringMetrics.cosineSimilarity();
	
	float result = metric.compare(str1, str2); //0.4472

```

The [StringMetricBuilder](./simmetrics-core/src/main/java/org/simmetrics/StringMetricBuilder.java) is a convenience tool to build string metrics. Any class implementing StringMetric, ListMetric, SetMetric can be used to build a string metric.

A metric is used to measure the similarity between strings. Metrics can work on strings, lists or sets tokens. To compare strings with a metric that works on a collection of tokens a tokenizer is required.

By adding simplifiers, tokenizers and filters the effectiveness of a metric can be improved. The exact combination is generally domain specific. The builder supports these domain specific customization.

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

The full process consists a of a chain simplification steps, followed by a chain of tokenization steps. After each tokenization step tokens can be filtered. The final result is passed onto the metric.


### Simplification ###

Simplification increases the effectiveness of a metric by removing noise and dimensionality from the input. The process maps a a complex string such as `Chilpéric II son of Childeric II` to a simpler format `chilperic ii son of childeric ii`. This allows string from different sources to be compared in the same normal form.

Custom simplification can created by implementing the [Simplifier](./simmetrics-core/src/main/java/org/simmetrics/simplifiers/Simplifier.java) interface.

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

Tokenization cuts up a string into tokens e.g. `[chilperic, ii, son, of, childeric, ii]`. Tokenization can also be done repeatedly by tokenizing the individual tokens e.g. `[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]`.

````
	return new StringMetricBuilder()
			.with(new SimonWhite<String>())
			.tokenize(new WhitespaceTokenizer())
			.tokenize(new QGramTokenizer(2))
			.build();
````

The method of tokenization changes the effectiveness of a metric depending on the context. A whitespace tokenizer might be more useful to measure similarity between large bodies of texts whiles a q-gram tokenizer will work more effectively when matching words.

Tokenization can be done by any class implementing the [Tokenizer](./simmetrics-core/src/main/java/org/simmetrics/tokenizers/Tokenizer.java) interface and is required for all metrics that work on collections of tokens rather then whole strings; ListMetrics and SetMetrics.

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
### Filtering ###


Filtering removes tokens that should not be considered for comparison. For example removing all tokens with a size less then three from `[chilperic, ii, son, of, childeric, ii]` results in `[chilperic, son, childeric]`.

A filter can be implemented by implementing a the [Predicate](https://github.com/google/guava/blob/master/guava/src/com/google/common/base/Predicate.java) interface.


```
		StringMetric metric = new StringMetricBuilder()
				.with(new CosineSimilarity<String>())
				.simplify(new CaseSimplifier.Lower())
				.simplify(new NonWordCharacterSimplifier())
				.tokenize(new WhitespaceTokenizer())
				.filter(new Predicate<String>() {
					
					@Override
					public boolean apply(String input) {
						return input.length() > 3;
					}
				})
				.build();
```

By chaining predicates more complicated filters can be build.  

```
		Set<String> commonWords = ...;
		
		StringMetric metric = new StringMetricBuilder()
				.with(new CosineSimilarity<String>())
				.simplify(new CaseSimplifier.Lower())
				.simplify(new NonWordCharacterSimplifier())
				.tokenize(new WhitespaceTokenizer())
				.filter(Predicates.not(Predicates.in(commonWords)))
				.build();
```


### Caching ###

Simplification and tokenization can be complex and expensive operations. When comparing one string against a collection of strings these two operations are done repeatedly for a single string - a common use case when searching for a match. With a simple caching mechanism this overhead can be reduced. 


```
		StringMetric metric = new StringMetricBuilder()
				.with(new CosineSimilarity<String>())
				.simplify(new CaseSimplifier.Lower())
				.setSimplifierCache()
				.tokenize(new QGramTokenizer(2))
				.setTokenizerCache()
				.build();
```

When a cache is set it applies to the whole simplification or tokenization chain. The default cache has a size of two for use with `StringMetrics.compare(StringMetric, String, List<String>)` and friends.
