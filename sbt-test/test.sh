#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

find . -name 'target' -prune -exec rm '{}' -rf \;

sbt package

cd ./target/scala-2.12
unzip -qo test_2.12-0.1.0-SNAPSHOT.jar

diff -r com classes-weaved/com
