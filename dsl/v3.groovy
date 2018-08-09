#!/usr/bin/env groovy

class DefaultConfig {
	Integer versionCode = 0
	String versionName = ""

	def versionCode(Integer versionCode) {
		this.versionCode = versionCode
	}

	def versionName(String versionName) {
		this.versionName = versionName
	}
}

class Android {
	Integer compileSdkVersion = 0
	def compileSdkVersion(Integer compileSdkVersion) {
		this.compileSdkVersion = compileSdkVersion
	}

	DefaultConfig defaultConfig = new DefaultConfig()
}

android = new Android()

def android(Closure c) {
	c.delegate = android
	c.call()
}

def defaultConfig(Closure c) {
	c.delegate = android.defaultConfig
	c.call()
}

println "Configurations before DSL:"
println "android.compileSdkVersion=${android.compileSdkVersion}"
println "android.defaultConfig.versionCode=${android.defaultConfig.versionCode}"
println "android.defaultConfig.versionName=\"${android.defaultConfig.versionName}\""

android {
    compileSdkVersion 27
    defaultConfig {
        versionCode 1
        versionName "1.0"
    }
}

println "Configurations after DSL:"
println "android.compileSdkVersion=${android.compileSdkVersion}"
println "android.defaultConfig.versionCode=${android.defaultConfig.versionCode}"
println "android.defaultConfig.versionName=\"${android.defaultConfig.versionName}\""