#!/usr/bin/env groovy

import groovy.json.JsonSlurper

def isMap(Object obj) {
	return obj instanceof java.util.LinkedHashMap
}

def isList(Object obj) {
	return obj instanceof java.util.ArrayList
}

def jsonSerialize(Object obj) {
	def json = ""
	if (isList(obj)) {
		json += '['
		obj.each {
			json += jsonSerialize(it)
			json += ','
		}
		json = json.substring(0, json.length() - 1)
		json += ']'
	} else if (isMap(obj)) {
		json += '{'
		obj.each { key, value ->
			json += '"' + key + '"'
			json += ':'
			json += jsonSerialize(value)
			json += ','
		}
		json = json.substring(0, json.length() - 1)
		json += '}'
	} else if (obj instanceof String) {
		json += '"'
		json += obj
		json += '"'
	} else {
		json += obj
	}
	return json
}

def branches = []

def jsonText = new File("data/map_local_data.json").text
def map = new JsonSlurper().parseText(jsonText)

map.branchs.each { bches ->
	bches.city_branch.each {
		def branch = [:]
		branch.city = bches.city
		branch.name = it.name
		branch.address = it.address
		branch.latitude = it.lat.toFloat()
		branch.longitude = it.lag.toFloat()
		branch.services = "外币兑换|提供42小时自动柜员机服务"
		branch.workingHours = "星期一至星期五"
		branch.CNAPS = "12345678"
		branch.phone = "12345678"
		branch.fax = "12345678"
		branches << branch
	}
}

def json = [
	"code": "0000",
	"message": "",
	"data": [
		"items": branches
	]
]

println jsonSerialize(json)