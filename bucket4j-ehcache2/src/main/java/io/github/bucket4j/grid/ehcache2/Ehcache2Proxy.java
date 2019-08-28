/*
 *
 * Copyright 2015-2018 Vladimir Bukhtoyarov
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package io.github.bucket4j.grid.ehcache2;

import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Nothing;
import io.github.bucket4j.grid.CommandResult;
import io.github.bucket4j.grid.GridBucketState;
import io.github.bucket4j.grid.GridCommand;
import io.github.bucket4j.grid.GridProxy;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.concurrent.CacheLockProvider;
import net.sf.ehcache.concurrent.LockType;
import net.sf.ehcache.concurrent.Sync;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class Ehcache2Proxy<K extends Serializable> implements GridProxy<K> {

    private final Cache cache;
    private final CacheLockProvider cacheLockProvider;

    public Ehcache2Proxy(Cache cache) {
        this.cache = Objects.requireNonNull(cache);
        this.cacheLockProvider = (CacheLockProvider) cache.getInternalContext();
    }

    @Override
    public <T extends Serializable> CommandResult<T> execute(K key, GridCommand<T> command) {
        Ehcache2EntryProcessor<K, T> entryProcessor = Ehcache2EntryProcessor.executeProcessor(command);
        return process(key, entryProcessor);
    }

    @Override
    public void createInitialState(K key, BucketConfiguration configuration) {
        Ehcache2EntryProcessor<K, Nothing> entryProcessor = Ehcache2EntryProcessor.initStateProcessor(configuration);
        process(key, entryProcessor);
    }

    @Override
    public <T extends Serializable> T createInitialStateAndExecute(K key, BucketConfiguration configuration, GridCommand<T> command) {
        Ehcache2EntryProcessor<K, T> entryProcessor = Ehcache2EntryProcessor.initStateAndExecuteProcessor(command, configuration);
        return process(key, entryProcessor).getData();
    }

    @Override
    public <T extends Serializable> CompletableFuture<CommandResult<T>> executeAsync(K key, GridCommand<T> command) {
        // because JCache does not specify async API
        throw new UnsupportedOperationException();
    }

    @Override
    public <T extends Serializable> CompletableFuture<T> createInitialStateAndExecuteAsync(K key, BucketConfiguration configuration, GridCommand<T> command) {
        // because JCache does not specify async API
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<BucketConfiguration> getConfiguration(K key) {
        Element element = cache.get(key);
        GridBucketState state = element == null ? null : (GridBucketState) element.getObjectValue();
        if (state == null) {
            return Optional.empty();
        } else {
            return Optional.of(state.getConfiguration());
        }
    }

    @Override
    public boolean isAsyncModeSupported() {
        // because ehcache 2.x does not specify async API
        return false;
    }

    private <T extends Serializable> CommandResult<T> process(K key, Ehcache2EntryProcessor<K, T> entryProcessor) {
        Sync sync = cacheLockProvider.getSyncForKey(key);
        sync.lock(LockType.WRITE);
        try {
            // get
            Element element = cache.get(key);

            // process
            Ehcache2EntryProcessor.MutableEntry<K, GridBucketState> mutableEntry = new Ehcache2EntryProcessor.MutableEntry<>(cache, key, element);
            CommandResult<T> result = entryProcessor.process(mutableEntry);

            // put
            if (mutableEntry.exists()) {
                cache.put(new Element(key, result.getData()));
            }

            return result;
        } finally {
            sync.unlock(LockType.WRITE);
        }
    }

}
