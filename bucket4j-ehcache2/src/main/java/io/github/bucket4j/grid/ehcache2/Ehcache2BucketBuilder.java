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

import io.github.bucket4j.AbstractBucketBuilder;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.grid.GridBucket;
import io.github.bucket4j.grid.RecoveryStrategy;
import io.github.bucket4j.Bucket;

import net.sf.ehcache.Cache;
import java.io.Serializable;

/**
 * {@inheritDoc}
 *
 * This builder creates the buckets backed by any <a href="https://www.jcp.org/en/jsr/detail?id=107">JCache API (JSR 107)</a> implementation.
 *
 */
public class Ehcache2BucketBuilder extends AbstractBucketBuilder<Ehcache2BucketBuilder> {

    /**
     * Creates the new instance of {@link Ehcache2BucketBuilder}
     */
    public Ehcache2BucketBuilder() {
        super();
    }

    /**
     * Constructs an instance of {@link GridBucket} which state actually stored inside in-memory data-grid,
     * the bucket stored in the grid immediately, so one network request will be issued to grid.
     * Due to this method performs network IO, returned result must not be treated as light-weight entity,
     * it will be a performance anti-pattern to use this method multiple times for same key,
     * you need to cache result somewhere and reuse between invocations,
     * else performance of all operation with bucket will be 2-x times slower.
     *
     * <p>
     * Use this method if and only if you need to full control over bucket lifecycle(especially specify {@link RecoveryStrategy}),
     * and you have clean caching strategy which suitable for storing buckets,
     * else it would be better to work through {@link Ehcache2#proxyManagerForCache(Cache) ProxyManager},
     * which does not require any caching, because ProxyManager operates with light-weight versions of buckets.
     *
     * @param cache distributed cache which will hold bucket inside cluster.
     *             Feel free to store inside single {@code cache} as mush buckets as you need.
     * @param key  for storing bucket inside {@code cache}.
     *             If you plan to store multiple buckets inside single {@code cache}, then each bucket should has own unique {@code key}.
     * @param recoveryStrategy specifies the reaction which should be applied in case of previously saved state of bucket has been lost.
     *
     * @return new distributed bucket
     */
    public <K extends Serializable> Bucket build(Cache cache, K key, RecoveryStrategy recoveryStrategy) {
        BucketConfiguration configuration = buildConfiguration();
        Ehcache2Proxy<K> gridProxy = new Ehcache2Proxy<>(cache);
        return GridBucket.createInitializedBucket(key, configuration, gridProxy, recoveryStrategy);
    }

}
