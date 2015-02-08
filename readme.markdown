[![Build Status](https://travis-ci.org/akilman/twitter-processor.svg?branch=master)](https://travis-ci.org/akilman/twitter-processor)

[![Dependency Status](https://www.versioneye.com/user/projects/54d6b7de3ca0849531000765/badge.svg?style=flat)](https://www.versioneye.com/user/projects/54d6b7de3ca0849531000765)

Overview
========
This is a sample application illustrating the usage of Java 8 lambdas to compare against traditional java.

In a nutshell, this application leverages [Twitter4J](http://twitter4j.org/en/) for pulling twitter data and executing
a series of contrived operations on that data.

Disclaimer
==========
This project is meant for illustrative purposes only. Code included is not intended to be useful, performant, or
clever in any form

Building
========
This project requires you have [Gradle](http://www.gradle.org/) installed

To build: `gradle build`

To import into [IntelliJ](http://www.jetbrains.com/idea/), open the associated `build.gradle` file

Running
=======
You'll need to authenticate via OAuth to run this application

First, [register your application](http://twitter.com/oauth_clients/new) to acquire a consumer key.

Next, run `blog.bootstrap.Bootstrap` with the following set as system properties:
```
-Dtwitter4j.oauth.consumerKey=[consumer key]
-Dtwitter4j.oauth.consumerSecret=[consumer secret]
```

This will load an access token which can later be used to run samples.

Samples are included in the `blog.Driver`, and primarily intended to be executed from the IDE



