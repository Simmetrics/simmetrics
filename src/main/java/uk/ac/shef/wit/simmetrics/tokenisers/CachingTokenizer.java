package uk.ac.shef.wit.simmetrics.tokenisers;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import uk.ac.shef.wit.simmetrics.wordhandlers.InterfaceTermHandler;

public class CachingTokenizer implements InterfaceTokeniser {

	private static final int CACHE_SIZE = 2;

	private final InterfaceTokeniser tokenizer;

	public CachingTokenizer(InterfaceTokeniser tokenizer) {
		this.tokenizer = tokenizer;
	}

	private Cache<String, ArrayList<String>> arrayCache = CacheBuilder
			.newBuilder().initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build();

	private Cache<String, Set<String>> setCache = CacheBuilder.newBuilder()
			.initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE).build();

	@Deprecated
	public String getShortDescriptionString() {
		return getClass().getSimpleName();
	}

	@Override
	public String toString() {
		return "CachingTokenizer [" + tokenizer + "]";
	}

	public InterfaceTermHandler getStopWordHandler() {
		return tokenizer.getStopWordHandler();
	}

	public void setStopWordHandler(InterfaceTermHandler stopWordHandler) {
		tokenizer.setStopWordHandler(stopWordHandler);
	}

	public ArrayList<String> tokenizeToArrayList(final String input) {
		try {
			return arrayCache.get(input, new Callable<ArrayList<String>>() {

				public ArrayList<String> call() throws Exception {
					return tokenizer.tokenizeToArrayList(input);
				}
			});
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	public Set<String> tokenizeToSet(final String input) {
		try {
			return setCache.get(input, new Callable<Set<String>>() {

				public Set<String> call() throws Exception {
					return tokenizer.tokenizeToSet(input);
				}
			});
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

}
