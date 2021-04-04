#!/bin/sh
JLINK_VM_OPTIONS="--add-opens java.base/java.lang.reflect=com.jfoenix"
DIR=`dirname $0`
$DIR/java $JLINK_VM_OPTIONS -m org.demka/org.demka.App "$@"
