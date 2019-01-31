#!/bin/bash

IMAGE=`cat IMAGE_NAME`
DEPLOYMENT=$1
MODULE=$2
PATH=$PATH:/root/bin
export PATH
echo "update image to:${IMAGE}"
kubectl apply -f ${MODULE}.yaml
kubectl set image deployments/${MODULE} ${MODULE}=${IMAGE} --namespace=usm