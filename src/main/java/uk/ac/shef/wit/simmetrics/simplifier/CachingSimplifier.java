package uk.ac.shef.wit.simmetrics.simplifier;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachingSimplifier implements Simplifier {

	private static final int CACHE_SIZE = 2;

	private final Simplifier simplifier;

	public CachingSimplifier(Simplifier simplifier) {
		super();
		this.simplifier = simplifier;
	}
	
	public Simplifier getSimplifier() {
		return simplifier;
	}

	private LoadingCache<String, String> arrayCache = CacheBuilder.newBuilder()
			.initialCapacity(CACHE_SIZE).maximumSize(CACHE_SIZE)
			.build(new CacheLoader<String, String>() {

				@Override
				public String load(String key) throws Exception {
					return getSimplifier().simplify(key);
				}

			});

	public String simplify(String input) {
		try {
			return arrayCache.get(input);
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

}
