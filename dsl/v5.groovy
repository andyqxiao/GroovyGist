#!/usr/bin/env groovy

class Configuration {
	def call(Closure closure) {
		closure.delegate = this
		closure.call()
	}

	def methodMissing(String name, args) {
		if (args.size() == 1) {
			def arg = args[0]
			if (arg instanceof Closure) {
				arg.delegate = this."${name}"
				arg.call()
			}
		}
	}
}

class DefaultConfig extends Configuration {
	Integer versionCode = 0
	String versionName = ""

	def versionCode(Integer versionCode) {
		this.versionCode = versionCode
	}

	def versionName(String versionName) {
		this.versionName = versionName
	}
}

class Android extends Configuration {
	DefaultConfig defaultConfig = new DefaultConfig()
	Integer compileSdkVersion = 0

	def compileSdkVersion(Integer compileSdkVersion) {
		this.compileSdkVersion = compileSdkVersion
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