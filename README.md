SimMetrics
==========

SimMetrics is a java library of Similarity or Distance Metrics, e.g. Levenshtein Distance, that provide float based similarity measures between String Data. All metrics return consistant measures rather than unbounded similarity scores. 

SimMetrics is based on the [SimMetrics Library](http://sourceforge.net/projects/simmetrics/) by Sam Chapman.

## Example ##

TOOD:

## Refactoring & Redesign ##

This fork is aimed at refactoring and cleaning up the SimMetrics project. This is done with the following aims: 

 * Support general workflow of simplication, tokenization and comparision.
 * Reduce clutter on the InterfaceSimilarityMetrics.
 * Allow caching of expensive tokenization and simplification operations.
 * Allow for easy anonymous-subclassing-style configuration.

## Workflow ##

The goal is to support a general workflow that consists of simplification, tokenization and comparision. Where applicable it should be possible to construct a similarity metric with any simplifier, tokenizer and submetric.

### Simplification ###

Simplification serves to map a a complex string `Chilpéric II son of Childeric II` to a simpler format `chilperic ii son of childeric ii`. This allows string from different sources to be compared in the same normal form.

In the current situation some algorithms apply their own simplification such as the removal of whitespace, non-diacritics, a-z characters, upper case, ect. Some apply none at all. This is done inconsistently between algorithms which is undesirable as between languages simplification is often non-trivial when dealing with languages that use characters outside of the a-z rnage. It also leaves the available character space in which the comparison is done implicit to the algorithm. 

To remedy these concerns it should be possible to set a simplifier that can take care of this. Language specific simplifiers may need to be developed by third-parties. 

On top the provided some algorithms may apply their own simplification again. This is okay for algorithms which rely on a specific language such as Soundex.

### Tokenization ###

Tokenization cuts up a string into tokens e.g. `[chilperic, ii, son, of, childeric, ii]`. Tokenization can also be done recursively `[ch,hi,il,il,lp,pe,er,ri,ic, ii, so,on, of, ch,hi,il,ld,de,er,ri,ic, ii]`.

Not all algorithms use tokenization but those that do such as SimonWhite, Dice and Jaccard are agnostic towards the exact method. While it eniterely feasible to let each algorithm implements its own features, there is a shared concern with regards to caching. Tokenization is expensive, being able to set a cache in a common manner allows programatic optimization when neccesary.

## Reduce Clutter ##

Many interfaces in the library contain methods that are not strictly related to the core functionality of comparing strings. This includes description strings, explanations, performance testing methods, ect. This should either be removed where redundant or refactored when usefull.

Ideally there is only a single method in the interface.

```
    public interface StringMetric {
    
        public float compare(String s1, String s2)
    
    } 
```

## Support Caching ##

Simplification and tokenization are complex and expensive operations. When comparing one string against a collection of strings these two operations are done repeatedly for a single string - a common use case when searching for a match. With a simple caching mechnism this overhead can be reduced. Being able to do this programmatically allows these to be inserted when needed.

## Easy Configuration ##

# Usage #

TODO: KISS Example.

## Tokenizers ##
TODO: Configure tokenizer

## Simplifiers ##
TODO: Configure tokenizer, simplifier.

## Caching ##
TODO: Configure caching. Show provided caching in `StringMetrics`.

## Performance Testing ##

TODO: Show performance test usage.
