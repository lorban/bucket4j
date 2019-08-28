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

import io.github.bucket4j.Extension;
import io.github.bucket4j.grid.ProxyManager;
import net.sf.ehcache.Cache;

import java.io.Serializable;

/**
 * The extension of Bucket4j library addressed to support Ehcache 2.x.
 */
public class Ehcache2 implements Extension<Ehcache2BucketBuilder> {

    /**
     * {@inheritDoc}
     *
     * @return new instance of {@link Ehcache2BucketBuilder}
     */
    @Override
    public Ehcache2BucketBuilder builder() {
        return new Ehcache2BucketBuilder();
    }

    /**
     * Creates {@link Ehcache2ProxyManager} for specified cache.
     *
     * @param cache cache for storing state of buckets
     * @param <T> type of keys in the cache
     * @return {@link ProxyManager} for specified cache.
     */
    public <T extends Serializable> ProxyManager<T> proxyManagerForCache(Cache cache) {
        return new Ehcache2ProxyManager<>(cache);
    }

}
