#!/usr/bin/env groovy

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

def jsonDeserialize(String json) {

}

def jsonFormat(String json) {

}

def jsonValidate(String json) {

}

// Test code
def json = [
	"code": "0000",
	"message": "",
	"data": [
		"items": "adfa"
	]
]

println jsonSerialize(json)