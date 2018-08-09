#!/usr/bin/env groovy

def parseVersionName(String versionName) {
	def BUILD_TYPES = ['', 'alpha', 'beta', 'rc', 'ga', 'rtm', 'oem']
	def numbers = [0, 0, 0]
	def type = 0

	def parts = versionName.replaceAll("-", ' ').replaceAll("\\s+", '-').split('-')
	if (parts.length > 0) {
		parts[0].split('\\.').eachWithIndex { it, index ->
			numbers[index] = it
		}
	}

	if (parts.length > 1) {
		type = BUILD_TYPES.findIndexOf {
			parts[1].equalsIgnoreCase(it)
		}
	}

	(numbers[0] as int) * 100000 + (numbers[1] as int)* 1000 + (numbers[2] as int) * 10 + type
}

println parseVersionName("1.2.9  beta")