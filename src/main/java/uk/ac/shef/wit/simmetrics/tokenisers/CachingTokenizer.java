package uk.ac.shef.wit.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class CachingTokenizer implements Tokenizer {

	private static final int CACHE_SIZE = 2;

	private final Tokenizer tokenizer;

	public CachingTokenizer(Tokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}

	public Tokenizer getTokenizer() {
		return tokenizer;
	}

	private LoadingCache<String, ArrayList<String>> arrayCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, ArrayList<String>>() {

				@Override
				public ArrayList<String> load(String key) throws Exception {
					return tokenizer.tokenizeToList(key);
				}

			});

	private LoadingCache<String, Set<String>> setCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, Set<String>>() {

				@Override
				public Set<String> load(String key) throws Exception {
					return tokenizer.tokenizeToSet(key);
				}

			});

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + tokenizer + "]";
	}

	public InterfaceTermHandler getStopWordHandler() {
		return tokenizer.getStopWordHandler();
	}

	public void setStopWordHandler(InterfaceTermHandler stopWordHandler) {
		tokenizer.setStopWordHandler(stopWordHandler);
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

}
