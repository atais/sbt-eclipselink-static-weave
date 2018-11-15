#!/bin/bash
parent_path=$( cd "$(dirname "${BASH_SOURCE[0]}")" ; pwd -P )
cd "$parent_path"

find . -name 'target' -prune -exec rm '{}' -rf \;

sbt package

cd ./target/scala-2.12
unzip -qo test_2.12-0.1.0-SNAPSHOT.jar

SRC="com/github/atais/entity"

if [ ! -f "$SRC/EntityA.class" ]; then
  echo "EntityA.class not found"
  exit 1
fi

if [ ! -f "$SRC/EntityB.class" ]; then
  echo "EntityB.class not found"
  exit 1
fi