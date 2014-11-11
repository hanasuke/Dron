#!/bin/sh

ls *.java > list.tmp

while read line
do
    javac -encoding UTF-8 ${line}
done < list.tmp
rm list.tmp
