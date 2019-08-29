package io.github.bucket4j.grid.ehcache2;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

class MutableEntry<K, V> {
    private final Ehcache cache;
    private final K key;
    private Element element;

    MutableEntry(Ehcache cache, K key, Element element) {
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

    Element getElement() {
        return element;
    }

    void setValue(V value) {
        element = new Element(key, value);
    }
}
