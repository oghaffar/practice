#!/bin/bash

while true
do
	curl -s -4 http://192.168.206.128:4567/hello
	sleep .1
done
