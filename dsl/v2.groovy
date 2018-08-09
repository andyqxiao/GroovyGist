#!/usr/bin/env groovy

prefix = []

def invokeMethod(String name, args) {
	args.each { arg ->
		if (arg instanceof Closure) {
			arg.delegate = this
			arg.prefix 
			prefix.add(name)
			arg.call()
			prefix.dropRight(1)
		} else if (arg instanceof String) {
			def str = ''
			prefix.each {
				str += it + '.'
			}
			str = str.substring(0, str.length() - 1)
			println "${str}.${name}=\"${arg}\""
		} else {
			def str = ''
			prefix.each {
				str += it + '.'
			}
			str = str.substring(0, str.length() - 1)
			println "${str}.${name}=${arg}"
		}
	}
}

android {
    compileSdkVersion 27
    defaultConfig {
        versionCode 1
        versionName "1.0"
    }
}