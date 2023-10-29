## A replacement for deprecated Gradle 5 osgi plugin
[![Download](https://img.shields.io/gradle-plugin-portal/v/io.github.goooler.osgi)](https://plugins.gradle.org/plugin/io.github.goooler.osgi)
[![Main](https://github.com/Goooler/gradle-legacy-osgi-plugin/actions/workflows/main.yml/badge.svg?branch=trunk&event=push)](https://github.com/Goooler/gradle-legacy-osgi-plugin/actions/workflows/main.yml)
[![License](https://img.shields.io/github/license/Goooler/gradle-legacy-osgi-plugin.svg)](LICENSE)

`osgi` plugin has been removed from Gradle core plugins since Gradle 6, yet some builds still depend on it.

This repo extracts the legacy `osgi` plugin (and unit test) from Gradle 5.6.4 source code.

If you're still using the deprecated `osgi` Gradle plugin in Gradle 6+, you can use this plugin (`com.github.blindpirate.osgi`) as an option.
If you're still using the deprecated `osgi` Gradle plugin in Gradle 6, you can use this plugin (`io.github.goooler.osgi`) as an option,
this is a fork of [blindpirate/gradle-legacy-osgi-plugin](https://github.com/blindpirate/gradle-legacy-osgi-plugin), thanks for contributions of original authors.

## How to use

Just replace the original `apply plugin: 'osgi'` with `apply plugin: 'io.github.goooler.osgi'`, or
```kotlin
plugins {
    id("io.github.goooler.osgi") version "0.8.0"
}
```
