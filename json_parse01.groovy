	#!/usr/bin/env groovy

	/**
	 * Get the nearest object or array end
	 */
	def getNearestEnd(String json, int start, String head, String tail) {
		def end = start
		def count = 1
		while (count > 0) {
			end++
			def c = json.charAt(end)
			if (c == head) {
				count++
			} else if (c == tail) {
				count--
			}
		}
		return end;
	}

	/**
	 * Parse the object
	 */
	def parseObject(String json) {
		def map = [:]
		def length = json.length()
		def index = 1
		def state = 'none' // none, string-value, other-value
		def key = ''
		while (index < length -1) {
			def c = json.charAt(index)
			switch(c) {
				case '"':
					if (state == 'none') {
						def keyStart = index + 1;
						def keyEnd = keyStart;
						while (json.charAt(keyEnd) != '"') {
							keyEnd++
						}
						index = keyEnd
						def keyValue = json[keyStart .. keyEnd - 1]
						key = keyValue
					} else if (state == 'value') {
						def stringStart = index + 1;
						def stringEnd = stringStart;
						while (json.charAt(stringEnd) != '"') {
							stringEnd++
						}
						index = stringEnd
						def stringValue = json[stringStart .. stringEnd - 1]
						map.put(key, stringValue)
					}
					break

				case '{':
					def objectStart = index
					def objectEnd = getNearestEnd json, index, '{', '}'
					def objectValue = json[objectStart .. objectEnd]
					map.put(key, parseObject(objectValue))
					index = objectEnd
					break

				case '[':
					def arrayStart = index
					def arrayEnd = getNearestEnd(json, index, '[', ']')
					def arrayValue = json[arrayStart .. arrayEnd]
					map.put(key, parseArray(arrayValue))
					index = arrayEnd
					break

				case ':':
					state = 'value'
					break

				case ',':
					state = 'none'
					key = ''
					break;

				case ["\n", "\t", "\r", " "]:
					break

				default:
					break
			}
			index++
		}

		return map
	}

	/**
	 * Parse the array
	 */
	def parseArray(String json) {
		def list = []
		def length = json.length()
		def index = 1
		def state = 'none' // none, string-value, other-value
		while (index < length -1) {
			def c = json.charAt(index)
			switch(c) {
				case '"':
					def stringStart = index + 1;
					def stringEnd = stringStart;
					while (json.charAt(stringEnd) != '"') {
						stringEnd++
					}
					def stringValue = json[stringStart .. stringEnd - 1]
					list.add(stringValue)
					index = stringEnd
					break

				case '{':
					def objectStart = index
					def objectEnd = getNearestEnd(json, index, '{', '}')
					def objectValue = json[objectStart .. objectEnd]
					list.add(parseObject(objectValue))
					index = objectEnd
					break

				case '[':
					def arrayStart = index
					def arrayEnd = getNearestEnd(json, index, '[', ']')
					def arrayValue = json[arrayStart .. arrayEnd]
					list.add(parseArray(arrayValue))
					index = arrayEnd
					break

				case ["\n", "\t", "\r", " "]:
					break

				case ',':
					state = 'none'
					key = ''
					break;
					
				default:
					break
			}
			index++
		}

		return list
	}

	/**
	 * Parse the JSON, object or array
	 */
	def parseJson(String json) {
		def start = json[0]
		if (start == '[') {
			return parseArray(json)
		} else if (start == '{') {
			return parseObject(json)
		} else {
			return null
		}
	}

	// Test code
	println parseJson('{"abdef":["Jim","Tom","Sam",["XYZ","ABC"]],{"namek":["adbc","cdef"]}}')
