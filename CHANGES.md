Change Log
==========

## Since 3.0.3 ##
  - Clarified java doc w/ regards to reflexive reflexive properties
  - Clarified java doc w/ regards to NullPointerExceptions
  - Optimization of Jaro
  - Optimization of SimonWhite
  - Various minor optimizations

## Since 3.0.2 ##
  - Optimization of Jaro

## Since 3.0.1 ##
  - Fixed dependency on snapshot version of core in example moodule.

## Since 3.0.0 ##

 - Further crud removal
  - Removed Chapman Algorithms
  - Removed Taglink and Taglink Token
  - Removed CSV tokenizer
 - Improved algorithms
  - Improved Jaro
    - Fixed bug that incorrectly calculated the half length
    - Refactored code
    - Added tests based on Wikipedia references
  - Changed MongeElkan implementation
    - Added normalization to remove asymetry
    - Similarity is now calculated as sqrt(monge-elkan(a,b) * monge-elkan(b,a))
  - Changed Matching Coeficient implementation
    - Original implementation should have been identical to Jaccard but wasn't. Implementation now is Jaccard but for lists rather then sets.
  - Refactored Levenstein and NeedlemanWunch
    - Memory reduced from O(n^2) to O(n)
  - Reimplemented SmithWatermanGotoh and SmithWaterman
    - SW implementation was Gotoh's O(n^2) implementation. SWG was regular SW. Implementations have been swapped.
  - Refactored cost functions 
    - Replaced SubstitutionCostFunction and AffineGapPenalty interfaces with Substitution and Gap interface respectively.
    - Gap penalties and mismatched character both apply a negative value.
 - Reduced verbosity of StringBuilder syntax
 - Added simplifiers from Apache Encoders project	
   - Caverphone1
   - Caverphone2
   - ColognePhonetic
   - DaitchMokotoffSoundex
   - DoubleMetaphone
   - MatchRatingApproach
   - Metaphone
   - Nysiis
   - RefinedSoundex
   - Soundex

## Since 2.0.0 ##

 - General refactoring
 - Added StringMetricBuilder
