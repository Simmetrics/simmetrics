package org.simmetrics.builders;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;

final class ExecutionExceptionCache<K,V> implements Cache<K, V> {

	@Override
	public V getIfPresent(Object key) {
		return null;
	}

	@Override
	public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException{
		throw new ExecutionException(new Exception());
	}

	@Override
	public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
		return null;
	}

	@Override
	public void put(K key, V value) {
		// Does nothing
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// Does nothing
	}

	@Override
	public void invalidate(Object key) {
		// Does nothing
	}

	@Override
	public void invalidateAll(Iterable<?> keys) {
		// Does nothing
	}

	@Override
	public void invalidateAll() {
		// Does nothing
	}

	@Override
	public long size() {
		return 0;
	}

	@Override
	public CacheStats stats() {
		return null;
	}

	@Override
	public ConcurrentMap<K, V> asMap() {
		return null;
	}

	@Override
	public void cleanUp() {
		// Does nothing
	}

}
