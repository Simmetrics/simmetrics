package org.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.simmetrics.metrics.Tokenizing;
import org.simmetrics.wordhandlers.TermHandler;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachingTokenizer implements TokenizingTokenizer {

	private static final int CACHE_SIZE = 2;

	private Tokenizer tokenizer;

	private final LoadingCache<String, ArrayList<String>> arrayCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, ArrayList<String>>() {

				@Override
				public ArrayList<String> load(String key) throws Exception {
					return getTokenizer().tokenizeToList(key);
				}

			});

	private final LoadingCache<String, Set<String>> setCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, Set<String>>() {

				@Override
				public Set<String> load(String key) throws Exception {
					return getTokenizer().tokenizeToSet(key);
				}

			});
	
	public CachingTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}


	public LoadingCache<String, ArrayList<String>> getArrayCache() {
		return arrayCache;
	}
	
	public LoadingCache<String, Set<String>> getSetCache() {
		return setCache;
	}
	
	
	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	public void setTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public ArrayList<String> tokenizeToList(final String input) {

		try {
			// Return copy of list to preserve state of cached version. Callers
			// may modify the list.
			return new ArrayList<String>(arrayCache.get(input));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	public Set<String> tokenizeToSet(final String input) {
		try {
			// Return copy of set to preserve state of cached set. Callers
			// may modify the set.
			return new HashSet<String>(setCache.get(input));
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + tokenizer + "]";
	}

	
}
