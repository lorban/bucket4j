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
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.Element;

import java.io.Serializable;


public interface Ehcache2EntryProcessor<K extends Serializable, T extends Serializable> extends Serializable {

    static <K extends Serializable> Ehcache2EntryProcessor<K, Nothing> initStateProcessor(BucketConfiguration configuration) {
        return new InitStateProcessor<>(configuration);
    }

    static <K extends Serializable, T extends Serializable> Ehcache2EntryProcessor<K, T> executeProcessor(GridCommand<T> targetCommand) {
        return new ExecuteProcessor<>(targetCommand);
    }

    static <K extends Serializable, T extends Serializable> Ehcache2EntryProcessor<K, T> initStateAndExecuteProcessor(GridCommand<T> targetCommand, BucketConfiguration configuration) {
        return new InitStateAndExecuteProcessor<>(targetCommand, configuration);
    }

    default long currentTimeNanos() {
        return System.currentTimeMillis() * 1_000_000;
    }

    CommandResult<T> process(MutableEntry<K, GridBucketState> mutableEntry, Object... arguments) throws CacheException;


    class MutableEntry<K, V> {
        private final Cache cache;
        private final K key;
        private Element element;

        public MutableEntry(Cache cache, K key, Element element) {
            this.cache = cache;
            this.key = key;
            this.element = element;
        }

        K getKey() {
            return key;
        }

        V getValue() {
            return element == null ? null : (V) element.getObjectValue();
        }

        boolean exists() {
            return element != null;
        }

        void remove() {
            if (element != null) {
                cache.remove(key);
                element = null;
            }
        }

        void setValue(V value) {
            element = new Element(key, value);
        }
    }


}
