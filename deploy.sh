#!/bin/bash -l

IMAGE=`cat IMAGE_NAME`
MODULE=$1
PATH=$PATH:/root/bin

export PATH
echo "apply deployment"
kubectl apply -f ${MODULE}.yaml
echo "update image version"
kubectl set image deployments/${MODULE} ${MODULE}=${IMAGE} --namespace=usm