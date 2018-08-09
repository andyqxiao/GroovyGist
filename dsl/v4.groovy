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

	def call(Closure closure) {
		closure.delegate = this
		closure.call()
	}
}

class Android {
	DefaultConfig defaultConfig = new DefaultConfig()
	Integer compileSdkVersion = 0

	def compileSdkVersion(Integer compileSdkVersion) {
		this.compileSdkVersion = compileSdkVersion
	}

	def defaultConfig(Closure closure) {
		closure.delegate = defaultConfig
		closure.call()
	}

	def call(Closure closure) {
		closure.delegate = this
		closure.call()
	}
}

android = new Android()

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