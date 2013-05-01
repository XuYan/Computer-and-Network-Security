#! /bin/sh

for i in $(seq 1000);
do openssl rsautl -decrypt -inkey privatekey.pem -in Enmessage.ssl -out Demessage.txt; 
done
