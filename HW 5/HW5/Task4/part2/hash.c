#include <stdio.h>
#include <openssl/evp.h>
#include <string.h>
#include <stdlib.h>
#define MESS_SIZE 30
#define HASH_SIZE 10000


unsigned char * getHash( unsigned char *mess, char *argv1 )
{
	unsigned char *hash = (char *)malloc( 4*sizeof(unsigned char) );

	EVP_MD_CTX *mdctx;
	int md_len;
	unsigned char md_value[EVP_MAX_MD_SIZE];	
	const EVP_MD *md = EVP_get_digestbyname(argv1);

	
	mdctx = EVP_MD_CTX_create();
	EVP_DigestInit_ex(mdctx, md, NULL);
	EVP_DigestUpdate(mdctx, mess, strlen(mess));
	EVP_DigestFinal_ex(mdctx, md_value, &md_len);

	int i = 0;
	for( ; i < 3; i ++ )
	{
		hash[i] = md_value[i];
	}
	hash[i] = '\0';

	EVP_MD_CTX_destroy(mdctx);

	return hash;
}


int main(int argc, char *argv[])
 {
	EVP_MD_CTX *mdctx;
	const EVP_MD *md;

	unsigned char md_value[EVP_MAX_MD_SIZE];
	int md_len, i;
	int count = 1;
	char var[10];
	unsigned char* original_mess = "Test Message 0\n";
	unsigned char* original_hash;

	OpenSSL_add_all_digests();

	if(!argv[1]) {
	      printf("Usage: mdtest digestname\n");
	      exit(1);
	}

	md = EVP_get_digestbyname(argv[1]);
	
	if(!md) {
		printf("Unknown message digest %s\n", argv[1]);
		exit(1);
	}

	original_hash = getHash( original_mess, argv[1] );

	while(1){
		sprintf( var, "%d", count );
 		mdctx = EVP_MD_CTX_create();
		EVP_DigestInit_ex(mdctx, md, NULL);
		
		char mess1[MESS_SIZE] = "Test Message ";
		strcat( mess1, var );
		strcat( mess1, "\n" );
		EVP_DigestUpdate(mdctx, mess1, strlen(mess1));
		EVP_DigestFinal_ex(mdctx, md_value, &md_len);

		EVP_MD_CTX_destroy(mdctx);
		
		if( ( original_hash[0] == md_value[0]) && (original_hash[1] == md_value[1]) && (original_hash[2] == md_value[2]) )
		{
			printf( "~~~~~~~~~~~~~~~~~\n" );
			printf( "Trial: %d\n", count );
			printf( "Found!\n" );
			printf( "~~~~~~~~~~~~~~~~~\n" );
			return 1;
		}
		count ++;
 	}

	return 0;
}
