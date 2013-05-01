INC=/usr/local/ssl/include/
LIB=/usr/local/ssl/lib

all : enc
enc : task4.c
	gcc -I$(INC) -L$(LIB) -o enc task4.c -lcrypto
.PHONY : clean
clean : 
	rm -rf enc

