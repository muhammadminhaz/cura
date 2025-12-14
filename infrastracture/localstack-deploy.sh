#!/bin/bash
#!/bin/bash
set -e

# ---------------- CONFIG ----------------
STACK_NAME="cura"
BUCKET_NAME="cf-templates"
TEMPLATE_FILE="./cdk.out/localstack.template.json"
TEMPLATE_KEY="patient-stack.template.json"
REGION="us-west-1"
LOCALSTACK_URL="http://localhost:4566"

export AWS_DEFAULT_REGION="$REGION"
export AWS_ACCESS_KEY_ID="test"
export AWS_SECRET_ACCESS_KEY="test"

# ---------------- ENSURE S3 BUCKET ----------------
echo "Ensuring S3 bucket exists: $BUCKET_NAME"

if ! aws --endpoint-url="$LOCALSTACK_URL" s3api head-bucket --bucket "$BUCKET_NAME" 2>/dev/null; then
  echo "Bucket not found. Creating s3://$BUCKET_NAME ..."
  aws --endpoint-url="$LOCALSTACK_URL" s3 mb "s3://$BUCKET_NAME"
else
  echo "Bucket already exists."
fi

# ---------------- UPLOAD TEMPLATE ----------------
echo "Uploading CloudFormation template to S3..."

aws --endpoint-url="$LOCALSTACK_URL" s3 cp \
  "$TEMPLATE_FILE" \
  "s3://$BUCKET_NAME/$TEMPLATE_KEY"

TEMPLATE_URL="http://$BUCKET_NAME.s3.localhost.localstack.cloud:4566/$TEMPLATE_KEY"
echo "Template URL: $TEMPLATE_URL"

# ---------------- DELETE STACK IF EXISTS ----------------
echo "Checking if CloudFormation stack exists..."

if aws --endpoint-url="$LOCALSTACK_URL" cloudformation describe-stacks \
   --stack-name "$STACK_NAME" >/dev/null 2>&1; then

  echo "Deleting existing CloudFormation stack: $STACK_NAME"
  aws --endpoint-url="$LOCALSTACK_URL" cloudformation delete-stack \
    --stack-name "$STACK_NAME"

  echo "Waiting for stack deletion..."
  aws --endpoint-url="$LOCALSTACK_URL" cloudformation wait stack-delete-complete \
    --stack-name "$STACK_NAME"

else
  echo "No existing stack found."
fi

# ---------------- CREATE STACK ----------------
echo "Creating new CloudFormation stack..."

aws --endpoint-url="$LOCALSTACK_URL" cloudformation create-stack \
  --stack-name "$STACK_NAME" \
  --template-url "$TEMPLATE_URL" \
  --capabilities CAPABILITY_NAMED_IAM

# ---------------- WAIT FOR STACK ----------------
echo "Waiting for stack creation..."

aws --endpoint-url="$LOCALSTACK_URL" cloudformation wait stack-create-complete \
  --stack-name "$STACK_NAME"

echo "Stack creation completed."

# ---------------- FETCH ALB ----------------
echo "Looking for the last ALB (expected API Gateway)..."

last_alb_dns=$(aws --endpoint-url="$LOCALSTACK_URL" elbv2 describe-load-balancers \
  --query "LoadBalancers[-1].DNSName" \
  --output text)

if [[ "$last_alb_dns" != "None" && -n "$last_alb_dns" ]]; then
  echo "API Gateway ALB: http://$last_alb_dns"
else
  echo "No ALBs found!"
fi
