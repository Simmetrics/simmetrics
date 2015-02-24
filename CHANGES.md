Change Log
==========

## Since 3.0.0-SNAPSHOT ##

 - Removed Chapman Algorithms
 - Removed Taglink and Taglink Token
 - Removed CSV tokenizer for being nonsensical
 - Refactored soundex
  - Removed dash from soundex string
  - Reduced minimum possible length to 1
  - Corrected implementation w/r repeated characters
  - Improved performance
 - Improved Jaro
  - Fixed bug that incorrectly calculated the half length
  - Refactored code
  - Added tests based on Wikipedia references
 - Changed MongeElkan implementation
  - Added normalization to remove asymetry
  - Similarity is now calculated as sqrt(monge-elkan(a,b) * monge-elkan(b,a))
 - Changed Matching Coeficient implementation
  - Original implementation should have been identical to Jaccard but wasn't. Implementation now is Jaccard but for lists rather then sets.
 - Reduced verbosity of StringBuilder syntax
 - Refactored Levenstein and NeedlemanWunch
  - Memory reduced from O(n^2) to O(n)
 - Reimplemented SmithWatermanGotoh and SmithWaterman
  - SW implementation was Gotoh's O(n^2) implementation. SWG was regular SW. Implementations have been swapped.
 - Refactored cost functions 
  - Replaced SubstitutionCostFunction and AffineGapPenalty interfaces with Substitution and Gap interface respectively.
   - Gap penalties and mismatched character both apply a negative value.
      
## Since 2.0.0 ##

 - General refactoring

