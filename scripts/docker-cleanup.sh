#!/bin/bash
docker volume ls -qf dangling=true
docker volume rm $(docker volume ls -qf dangling=true)
docker system prune
