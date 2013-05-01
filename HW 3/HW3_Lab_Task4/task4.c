#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/evp.h>
	
//unsigned char *IV = (unsigned char *)malloc(sizeof(unsigned char)*16);
//memset( IV, 0x00, sizeof(IV) );	
//unsigned char IV[] = { 0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
//unsigned char *key = (unsigned char *)malloc(sizeof(unsigned char)*16);
//memset( key, 0x20, sizeof( key ) );
//unsigned char key[] = {0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20,0x20};
//char ciphertext[] = {0x8d,0x20,0xe5,0x05,0x6a,0x8d,0x24,0xd0,0x46,0x2c,0xe7,0x4e,0x49,0x04,0xc1,0xb5,0x13,0xe1,0x0d,0x1d,0xf4,0xa2,0xef,0x2a,0xd4,0x54,0x0f,0xae,0x1c,0xa0,0xaa,0xf9};

int main( void )
{
	unsigned char outbuff[ 1024 ];
	int outlen;
	int tmplen;
	unsigned char IV[] = "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00";
	unsigned char key[] = "\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20\x20";
	char plaintext[] = "This is a top secret.";
	char ciphertext[] = "\x8d\x20\xe5\x05\x6a\x8d\x24\xd0\x46\x2c\xe7\x4e\x49\x04\xc1\xb5\x13\xe1\x0d\x1d\xf4\xa2\xef\x2a\xd4\x54\x0f\xae\x1c\xa0\xaa\xf9";	

	int i = 0;	

	FILE *fp;
	fp = fopen( "words.txt", "rb" );
	if( !fp )
	{
		printf( "cannot open file 'words.txt'\n" );
		return 1;
	}
	
	EVP_CIPHER_CTX ctx;
	EVP_CIPHER_CTX_init( &ctx );

	
	while( fscanf( fp, "%s", key ) != EOF )
	{
	
		for( i = strlen(key); i < 16; i++)
			key[ i ] = 0x20;

		//printf( "%d\n", sizeof(key) );	
		EVP_EncryptInit_ex( &ctx, EVP_aes_128_cbc(), NULL, key, IV );
		if( !EVP_EncryptUpdate( &ctx, outbuff, &outlen, plaintext, strlen( plaintext ) ) )
			return 2;
		if( !EVP_EncryptFinal_ex( &ctx, outbuff + outlen, &tmplen ) )
			return 3;
		outlen += tmplen;

		/*
		if( strcmp( key, "median" ) == 0 )
		{
			//printf( "%s\n", key );
			//printf( "%d\n", strlen(outbuff) );
			//printf( "0x%x\n", *((long *)outbuff) );
			for( i = 0; i < sizeof( outbuff ); i++ )
			{
				printf( "0x%2x ",outbuff[i]&0xff );
			}
			//printf( "0x%x\n", *((long *)ciphertext) );
		}
		*/

		if( strcmp( outbuff, ciphertext ) == 0 )
		{
			printf( "Key is %s\n", key );
			printf( "Display of bytes in hex after encryption:\n" );
			for( i = 0; i < sizeof( outbuff ); i++ )
				printf( "0x%2x ",outbuff[i]&0xff );
			break;
		}

		memset( outbuff, 0x00, sizeof( outbuff ) );
		memset( key, 0x20, sizeof( key ) );
		memset( IV, 0x00, sizeof(IV) );
	}
	
	EVP_CIPHER_CTX_cleanup( &ctx );
	fclose( fp );
	return 0;
}
