#! /usr/bin/awk -f
$0 ~ /@author/ { 
	gsub(/\</, "[")
	gsub(/\>/, "]")
	print
}
$0 !~ /@author/ {
	print
}