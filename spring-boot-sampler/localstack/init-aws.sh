#!/bin/bash
awslocal sqs create-queue --queue-name sainsburys-parameters
awslocal sqs create-queue --queue-name sainsburys-parameters-sns
awslocal sqs create-queue --queue-name sainsburys-parameters-sns-raw
awslocal sns create-topic --name sainsburys-parameters-sns
awslocal sns subscribe --topic-arn arn:aws:sns:eu-west-1:000000000000:sainsburys-parameters-sns --protocol sqs --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:sainsburys-parameters-sns
awslocal sns subscribe --topic-arn arn:aws:sns:eu-west-1:000000000000:sainsburys-parameters-sns --protocol sqs --notification-endpoint arn:aws:sqs:eu-west-1:000000000000:sainsburys-parameters-sns-raw --attributes "{\"RawMessageDelivery\": \"true\"}"