#include <stdio.h>
#include <openssl/evp.h>
#include <string.h>
#include <stdlib.h>
#define MESS_SIZE 30
#define HASH_SIZE 10000


int main(int argc, char *argv[])
 {
	EVP_MD_CTX *mdctx;
	const EVP_MD *md;

	unsigned char md_value[EVP_MAX_MD_SIZE];
	int md_len, i;
	int count = 0;
	char var[10];

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

	unsigned char p[HASH_SIZE][5];
	memset( &p,0,sizeof(p) );
	unsigned char *q = (unsigned char *)malloc( 5*sizeof(unsigned char) );	
	memset( q, 0, sizeof(q) );

	while(1){
		sprintf( var, "%d", count );
 		mdctx = EVP_MD_CTX_create();
		EVP_DigestInit_ex(mdctx, md, NULL);
		
		char mess1[MESS_SIZE] = "Test Message ";
		strcat( mess1, var );
		strcat( mess1, "\n" );
		EVP_DigestUpdate(mdctx, mess1, strlen(mess1));
		EVP_DigestFinal_ex(mdctx, md_value, &md_len);

		for(i = 0; i < 3; i++)
		{	q[i] = md_value[i]; printf("%02x\n", q[i]); }

		EVP_MD_CTX_destroy(mdctx);
		
		for( i = 0; i < count; i ++ )
		{
			if( (p[i][0] == q[0]) && (p[i][1] == q[1]) && (p[i][2] == q[2]) )
			{
				printf( "~~~~~~~~~~~~~~~~~\n" );
				printf( "i is %d\n", i );
				printf( "count is %d\n", count );
				printf( "Found!\n" );
				printf( "~~~~~~~~~~~~~~~~~\n" );
				return 1;
			}
		}
				
				printf( "The hash of q is %s\n", q );
		strcpy( p[count],q );
		count ++;
		
 	}

	return 0;
}
