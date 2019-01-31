#!/bin/bash -l

IMAGE=`cat IMAGE_NAME`
MODULE=$1
PATH=$PATH:/root/bin

export PATH
echo "update image to:${IMAGE}"
kubectl apply -f ${MODULE}.yaml
kubectl set image deployments/${MODULE} ${MODULE}=${IMAGE} --namespace=usm