![](/doc-pages/white-logo.png)

# Java rate-limiting library based on token-bucket algorithm.
[![Join the chat at https://gitter.im/vladimir-bukhtoyarov/bucket4j](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/vladimir-bukhtoyarov/bucket4j?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Licence](https://img.shields.io/hexpm/l/plug.svg)](https://github.com/vladimir-bukhtoyarov/bucket4j/blob/master/LICENSE)

## Advantages of Bucket4j
* Implemented on top of ideas of well known algorithm, which are by de-facto standard for rate limiting in the IT industry.
* Effective lock-free implementation, Bucket4j is good scalable for multi-threading case.
* Absolutely non-compromise precision, Bucket4j does not operate with floats or doubles, all calculation are performed in the integer arithmetic,
this feature protects end users from calculation errors involved by rounding.
* Ability to switch from one JVM to cluster in two lines of code. Using Bucket4j you are able to limiting something in the cluster of JVMs.
Since [release 1.2](https://github.com/vladimir-bukhtoyarov/bucket4j/releases/tag/1.2.0) the ```Bucket4j``` supports any GRID solution which compatible with [JCache API (JSR 107)](https://www.jcp.org/en/jsr/detail?id=107) specification.
Just use your favorite grid including [Hazelcast](http://hazelcast.com/products/hazelcast/), [Ignite](https://ignite.apache.org/), [Coherence](http://www.oracle.com/technetwork/middleware/coherence/overview/index.html), [Infinispan](http://infinispan.org/) or any other.
* Ability to specify multiple bandwidths per bucket. For example you can limit 1000 events per hours but not often then 100 events per minute.
* Both synchronous and asynchronous API.
* Pluggable listener API that allows to implement monitoring and logging.
* Ability to use bucket as as scheduler, [see examples](https://github.com/vladimir-bukhtoyarov/bucket4j/blob/4.0/doc-pages/basic-usage.md#example-2---using-bucket-as-scheduler).

## Supported back-ends
As mentioned above in addition to local in-memory buckets, the Bucket4j supports clustered usage scenario on top of following back-ends:
 
| Back-end                   | Documentation page                                  | Async supported |
| -------------------------- | --------------------------------------------------- | :-------------: |
| ```JCache API (JSR 107)``` | [bucket4j-jcache](doc-pages/jcache-usage.md)        | No              |
| ```Hazelcast```            | [bucket4j-hazelcast](doc-pages/hazelcast.md)        | Yes             |
| ```Apache Ignite```        | [bucket4j-ignite](doc-pages/ignite.md)              | Yes             |
| ```Inifinispan```          | [bucket4j-infinspan](doc-pages/infinispan.md)       | Yes             |
| ```Oracle Coherence```     | [bucket4j-coherence](doc-pages/coherence.md)        | Yes             | 
| ```Ehcache 2.x```          | [bucket4j-ehcache2](doc-pages/ehcache2.md)          | No              | 

## General documentation
#### Basics:
* [Token bucket wikipedia](https://en.wikipedia.org/wiki/Token_bucket) - wikipedia page describes the token-bucket algorithm in classical form.
* [Non-formal overview of token-bucket algorithm](doc-pages/token-bucket-brief-overview.md) - the brief overview of token-bucket algorithm.

#### Examples:
* [Basic-usage](doc-pages/basic-usage.md) - examples of basic usage.
* [Advanced-usage](doc-pages/advanced-usage.md) - examples of advanced usage.
* [Asynchronous-usage](doc-pages/asynchronous.md) - examples of asynchronous usage.
* [Listening of bucket events](doc-pages/listener.md) - examples of monitoring.

#### Production checklist
* [Common production checklist](doc-pages/production-generic-checklist.md) - Mandatory points that need to be understood before using the Bucket4j in production, independently of local or clustered usage scenarios.
* [JCache production checklist](doc-pages/production-jcache-checklist.md) - Mandatory points that need to be understood before using the Bucket4j over JCache cluster.

#### Archive:
* [Documentation for legacy releases](doc-pages/archive-links.md).

## Third-party integrations:
* [marcosbarbero/spring-cloud-zuul-ratelimit](https://github.com/marcosbarbero/spring-cloud-zuul-ratelimit)
* [MarcGiffing/Spring Boot Starter for Bucket4j](https://github.com/MarcGiffing/bucket4j-spring-boot-starter) . Demos of usage (incluing Zuul and Hazelcast) can be found there [bucket4j-spring-boot-starter-examples](https://github.com/MarcGiffing/bucket4j-spring-boot-starter-examples).
* [JHipster/JHipster API Gateway](https://jhipster.github.io/api-gateway/#rate_limiting)
* [Zivver/Dropwizard Ratelimit](https://github.com/zivver/dropwizard-ratelimit)

## Third-party demos and articles:
* [Rate limiting Spring MVC endpoints with bucket4j](https://golb.hplar.ch/2019/08/rate-limit-bucket4j.html)
* [Abdennebi/spring-boot-bucket4j-hazelcast-demo](https://github.com/Abdennebi/spring-boot-bucket4j-hazelcast-demo)
* [ProgrammerSought Bucket4j - preliminary understanding](http://www.programmersought.com/article/2524209291/)

## Get Bucket4j library
#### You can add Bucket4j to your project as maven dependency
The Bucket4j is distributed through both [JCenter](https://bintray.com/bintray/jcenter) and [Maven Central](http://search.maven.org/),
use any of them:
```xml
<dependency>
    <groupId>com.github.vladimir-bukhtoyarov</groupId>
    <artifactId>bucket4j-core</artifactId>
    <version>4.5.0</version>
</dependency>
``` 
#### You can build Bucket4j from sources
```bash
git clone https://github.com/vladimir-bukhtoyarov/bucket4j.git
cd bucket4j
mvn clean install
```

## License
Copyright 2015-2019 Vladimir Bukhtoyarov
Licensed under the Apache Software License, Version 2.0: <http://www.apache.org/licenses/LICENSE-2.0>.

## Have a question?
Feel free to ask in:
* Gitter chat [https://gitter.im/vladimir-bukhtoyarov/bucket4j](https://gitter.im/vladimir-bukhtoyarov/bucket4j) 
* Github issue tracker [https://github.com/vladimir-bukhtoyarov/bucket4j/issues/new](https://github.com/vladimir-bukhtoyarov/bucket4j/issues/new)

## Have a sensitive question?
If any information in the question should not be made publicly available, then try to contact with author via:
* Twitter private message [https://twitter.com/monitoring_king](https://twitter.com/monitoring_king)
* Linkedin private message [https://www.linkedin.com/in/vladimir-bukhtoyarov-a2385224](https://www.linkedin.com/in/vladimir-bukhtoyarov-a2385224)

## Who use Bucket4j?
![Ringcentral](/doc-pages/images/ringcentral.png "Ringcentral")
![JHipster](/doc-pages/images/jhipster.png "JHipster")
![Opentable](/doc-pages/images/opentable.png "Opentable")
![Apereo CAS](/doc-pages/images/apereo_cas.jpg "Apereo CAS")

