#! /bin/sh

for i in $(seq 1000);
do openssl enc -aes-128-cbc -e -in message -out aesEncryp.bin -k NiceDay; 
done
