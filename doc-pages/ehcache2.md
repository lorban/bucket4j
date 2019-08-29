# Ehcache 2.x integration

**Question:** Can't I use the Ehcache 2.x JSR 107 provider instead?
**Answer:** No, the Ehcache 2.x JSR 107 provider does not implement everything Bucket4j needs.

## Dependencies
To use ```bucket4j-ehcache2``` extension you need to add following dependency:
```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-ehcache2</artifactId>
    <version>${bucket4j.version}</version>
</dependency>
```

## Example of Bucket instantiation
```java
org.apache.ignite.IgniteCache<K, GridBucketState> cache = ...;
...

Bucket bucket = Bucket4j.extension(io.github.bucket4j.grid.ehcache2.Ehcache2.class).builder()
                   .addLimit(Bandwidth.simple(1_000, Duration.ofMinutes(1)))
                   .build(cache, key, RecoveryStrategy.RECONSTRUCT);
```

## Example of ProxyManager instantiation
```java
net.sf.ehcache.Ehcache cache = ...;
...

ProxyManager proxyManager = Bucket4j.extension(io.github.bucket4j.grid.ehcache2.Ehcache2.class).proxyManagerForCache(cache);
```
