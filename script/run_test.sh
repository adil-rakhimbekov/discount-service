#!/usr/bin/env bash

echo -e "\nStarting Docker Compose...\n"
docker-compose -f docker/docker-compose.yml up --detach

echo -e "\nRunning test...\n"
mvn clean install jacoco:report

echo -e "\nShutting down Docker Compose...\n"
docker-compose -f docker/docker-compose.yml down