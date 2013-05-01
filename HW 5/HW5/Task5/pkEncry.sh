#! /bin/sh

for i in $(seq 1000);
do openssl rsautl -encrypt -inkey public.pem -pubin -in message -out Enmessage.ssl; 
done
