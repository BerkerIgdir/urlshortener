#!/bin/bash

docker-compose -f replica_set.yaml build

docker-compose -f replica_set.yaml up -d

sleep 5

docker exec mongodb1 /scripts/rs-init.sh