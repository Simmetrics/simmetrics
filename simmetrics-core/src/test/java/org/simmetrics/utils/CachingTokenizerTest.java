
package org.simmetrics.utils;

import org.junit.Test;
import org.simmetrics.tokenizers.Tokenizer;
import org.simmetrics.tokenizers.TokenizerTest;
import org.simmetrics.utils.CachingTokenizer;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.mockito.Mockito.*;

@SuppressWarnings("javadoc")
public class CachingTokenizerTest extends TokenizerTest {

	private Tokenizer innerTokenizer;

	@Override
	protected final Tokenizer getTokenizer() {
		
		innerTokenizer = mock(Tokenizer.class);
		
		when(innerTokenizer.tokenizeToList("ABC")).thenReturn(newArrayList("ABC"));
		when(innerTokenizer.tokenizeToList("CCC")).thenReturn(newArrayList("CCC"));
		when(innerTokenizer.tokenizeToList("EEE")).thenReturn(newArrayList("EEE"));
		when(innerTokenizer.tokenizeToList("")).thenReturn(newArrayList(""));

		when(innerTokenizer.tokenizeToSet("ABC")).thenReturn(newHashSet("ABC"));
		when(innerTokenizer.tokenizeToSet("CCC")).thenReturn(newHashSet("CCC"));
		when(innerTokenizer.tokenizeToSet("EEE")).thenReturn(newHashSet("EEE"));
		when(innerTokenizer.tokenizeToSet("")).thenReturn(newHashSet(""));

		
		return new CachingTokenizer(2,2,innerTokenizer);
	}

	@Override
	public final T[] getTests() {

		return new T[] { new T("ABC", "ABC")
				, new T("CCC", "CCC"),
				new T("ABC", "ABC"), new T("EEE", "EEE"), new T("ABC", "ABC"),
				new T("CCC", "CCC"),
				new T("","") 

		};
	}
	@Test
	public final void tokenizeToListUsesCache() {
		for (T t : tests) {
			tokenizer.tokenizeToList(t.string());
		}
				
		 verify(innerTokenizer, times(1)).tokenizeToList("ABC");
		 verify(innerTokenizer, times(2)).tokenizeToList("CCC");
	}


	@Test
	public final void tokenizeToSetUsesCache() {
		for (T t : tests) {
			tokenizer.tokenizeToSet(t.string());
		}

		 verify(innerTokenizer, times(1)).tokenizeToSet("ABC");
		 verify(innerTokenizer, times(2)).tokenizeToSet("CCC");
	}
}
