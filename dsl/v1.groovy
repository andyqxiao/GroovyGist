#!/usr/bin/env groovy

def android(Closure c) {
	c.call()
}

def defaultConfig(Closure c) {
	c.call()
}

def compileSdkVersion(Integer version) {
	println "android.compileSdkVersion=${version}"
}

def versionCode(Integer version) {
	println "android.defaultConfig.versionCode=${version}"
}

def versionName(String version) {
	println "android.defaultConfig.versionName=\"${version}\""
}

android {
    compileSdkVersion 27
    defaultConfig {
        versionCode 1
        versionName "1.0"
    }
}