#! /usr/bin/awk -f
$0 ~ /[Ii][Ss][Oo]-8859-1/ { 
	gsub(/[Ii][Ss][Oo]-8859-1/, "utf-8")
#	print
}
$0 !~ /[Ii][Ss][Oo]-8859-1/ {
	print
}
