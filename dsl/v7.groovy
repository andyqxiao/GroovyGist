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

class BuildType extends Configuration {
	static all = []
	String name
	String applicationIdSuffix
	Boolean minifyEnabled
	Boolean debuggable
}

def methodMissing(String name, args) {
	if (args.size() == 1) {
		def arg = args[0]
		if (arg instanceof Closure) {
			def buildType = new BuildType()
			buildType.name name
			arg.resolveStrategy = Closure.DELEGATE_FIRST
			arg.delegate = buildType
			arg()
			BuildType.all.add(buildType)
		}
	}
}

def buildTypes(Closure c) {
	c.delegate = this
	c()
}

buildTypes {
    debug {
        minifyEnabled false
        debuggable true
        applicationIdSuffix "*.debug"
    }
	release {
        minifyEnabled true
        debuggable false
        applicationIdSuffix "*.release"
    }
}

BuildType.all.eachWithIndex { it, index ->
	println "${index}:${it.name}"
	println it.minifyEnabled
	println it.debuggable
	println it.applicationIdSuffix
}