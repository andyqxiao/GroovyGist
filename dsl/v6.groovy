#!/usr/bin/env groovy

class Configuration {
	def call(Closure closure) {
		closure.delegate = this
		closure()
	}

	def methodMissing(String name, args) {
		if (args.size() == 1) {
			def arg = args[0]
			if (arg instanceof Closure) {
				arg.resolveStrategy = Closure.DELEGATE_FIRST
				arg.delegate = this."${name}"
				arg()
			} else {
				if (this.hasProperty(name)) {
					this."$name" = arg
				}
			}
		}
	}
}

class DefaultConfig extends Configuration {
	Integer versionCode = 0
	String versionName = ""
}

class Android extends Configuration {
	DefaultConfig defaultConfig = new DefaultConfig()
	Integer compileSdkVersion = 0
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