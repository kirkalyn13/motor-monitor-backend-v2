#!/bin/bash
set -e

yum update -y
amazon-linux-extras enable corretto17
yum install -y java-17-amazon-corretto awscli

API_KEY=$(aws secretsmanager get-secret-value \
  --secret-id "${api_secret_arn}" \
  --query SecretString \
  --output text \
  --region ${aws_region} \
  | python3 -c "import sys,json; print(json.load(sys.stdin)['api_key'])")

nohup java -jar "${app_jar_path}" \
  --server.port=${app_port} \
  --app.api-key="$API_KEY" \
  > /var/log/app.log 2>&1 &