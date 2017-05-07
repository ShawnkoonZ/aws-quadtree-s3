#!/bin/sh

wget https://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip

unzip -q aws-java-sdk.zip

rm aws-java-sdk.zip

mv $(ls|grep aws-java-sdk-*) vendor

rm vendor/*.txt && rm vendor/*.html && rm -rf vendor/samples && rm -rf vendor/documentation

mkdir vendor/amazon && mkdir vendor/amazon/aws-java-sdk

mv vendor/lib vendor/amazon/aws-java-sdk && mv vendor/third-party vendor/amazon/aws-java-sdk
