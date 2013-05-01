 #include <stdio.h>
 #include <openssl/evp.h>

 main(int argc, char *argv[])
 {
 EVP_MD_CTX *mdctx;
 const EVP_MD *md;
 char mess1[] = "Test Message 0\n";
 unsigned char md_value[EVP_MAX_MD_SIZE];
 int md_len, i;

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

 mdctx = EVP_MD_CTX_create();
 EVP_DigestInit_ex(mdctx, md, NULL);
 EVP_DigestUpdate(mdctx, mess1, strlen(mess1));
 EVP_DigestFinal_ex(mdctx, md_value, &md_len);
 EVP_MD_CTX_destroy(mdctx);

 printf("Digest is: ");
 for(i = 0; i < 3; i++) printf("%2x\n", md_value[i]);
 printf("\n");
 }
