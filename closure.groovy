#!/usr/bin/env groovy

class Cat {
	def bark() {
		println "Miao"
	}
}

class Dog {
	def bark() {
		println "Wang"
	}
}

def hello = {
	bark()
}

def cat = new Cat()
def dog = new Dog()

hello.delegate = dog

hello()
