#!/bin/bash
set -e # Stops the script if any command fails
echo "1st"
aws --endpoint-url=http://localhost:4566 cloudformation delete-stack \
    --stack-name cura
echo "2nd"
aws --endpoint-url=http://localhost:4566 cloudformation deploy \
    --stack-name cura \
    --template-file "./cdk.out/localstack.template.json"
echo "3rd"
aws --endpoint-url=http://localhost:4566 elbv2 describe-load-balancers \
    --query "LoadBalancers[0].DNSName" --output text