#!/bin/bash

gfsh start server --name="${HOSTNAME}" --locators=${GEODE_LOCATOR_SERVICE_HOST}[${GEODE_LOCATOR_SERVICE_PORT}]

while true; do
  sleep 20
done