import java.io.IOException;

public class DES_Encryption {
	final static int BLOCK_SIZE = 8;
	
	public static void main(String[] args) throws IOException{
		
		StringBuilder originalBL = new StringBuilder();
		StringBuilder plaintextL = new StringBuilder();
		StringBuilder plaintextR = new StringBuilder();
		
		char[] originalBlock = new char[ BLOCK_SIZE ];
		char[] keys = { 0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc };
		
		
		int[][][] sBOX = 
		{ 		{ {14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
				  {0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8 },
				  {4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0 },
				  {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13} },
				{ {15,  1,  8, 14,  6, 11,  3,  4,  9,  7,  2, 13, 12,  0,  5, 10},
				  {3, 13,  4,  7, 15,  2,  8, 14, 12,  0,  1, 10,  6,  9, 11,  5 },
				  {0, 14,  7, 11, 10,  4, 13,  1,  5,  8, 12,  6,  9,  3,  2, 15 },
				  {13,  8, 10,  1,  3, 15,  4,  2, 11,  6,  7, 12,  0,  5, 14,  9} },
				{{10,  0,  9, 14,  6,  3, 15,  5,  1, 13, 12,  7, 11,  4,  2,  8},
  			     {13,  7,  0,  9,  3,  4,  6, 10,  2,  8,  5, 14, 12, 11, 15,  1 },
				 {13,  6,  4,  9,  8, 15,  3,  0, 11,  1,  2, 12,  5, 10, 14,  7 },
				 { 1, 10, 13,  0,  6,  9,  8,  7,  4, 15, 14,  3, 11,  5,  2, 12 } },
				{{ 7, 13, 14,  3,  0,  6,  9, 10,  1,  2,  8,  5, 11, 12,  4, 15},
	  			 {13,  8, 11,  5,  6, 15,  0,  3,  4,  7,  2, 12,  1, 10, 14,  9 },
				 {10,  6,  9,  0, 12, 11,  7, 13, 15,  1,  3, 14,  5,  2,  8,  4 },
				 { 3, 15,  0,  6, 10,  1, 13,  8,  9,  4,  5, 11, 12,  7,  2, 14 } },
				{{ 2, 12,  4,  1,  7, 10, 11,  6,  8,  5,  3, 15, 13,  0, 14,  9},
		  		 {14, 11,  2, 12,  4,  7, 13,  1,  5,  0, 15, 10,  3,  9,  8,  6 },
				 { 4,  2,  1, 11, 10, 13,  7,  8, 15,  9, 12,  5,  6,  3,  0, 14 },
				 { 11,  8, 12,  7,  1, 14,  2, 13,  6, 15,  0,  9, 10,  4,  5,  3 } },
				{{ 12,  1, 10, 15,  9,  2,  6,  8,  0, 13,  3,  4, 14,  7,  5, 11},
			  	 {10, 15,  4,  2,  7, 12,  9,  5,  6,  1, 13, 14,  0, 11,  3,  8 },
				 { 9, 14, 15,  5,  2,  8, 12,  3,  7,  0,  4, 10,  1, 13, 11,  6 },
				 { 4,  3,  2, 12,  9,  5, 15, 10, 11, 14,  1,  7,  6,  0,  8, 13 } },
				{{ 4, 11,  2, 14, 15,  0,  8, 13,  3, 12,  9,  7,  5, 10,  6,  1},
				 {13,  0, 11,  7,  4,  9,  1, 10, 14,  3,  5, 12,  2, 15,  8,  6 },
				 { 1,  4, 11, 13, 12,  3,  7, 14, 10, 15,  6,  8,  0,  5,  9,  2 },
				 {  6, 11, 13,  8,  1,  4, 10,  7,  9,  5,  0, 15, 14,  2,  3, 12 } },
				{{13,  2,  8,  4,  6, 15, 11,  1, 10,  9,  3, 14,  5,  0, 12,  7},
				 { 1, 15, 13,  8, 10,  3,  7,  4, 12,  5,  6, 11,  0, 14,  9,  2 },
				 { 7, 11,  4,  1,  9, 12, 14,  2,  0,  6, 10, 13, 15,  3,  5,  8 },
				 { 2,  1, 14,  7,  4, 10,  8, 13, 15, 12,  9,  0,  3,  5,  6, 11 } }
		};

		originalBL.append( "abcdefgh" );
		
		long start = System.currentTimeMillis();
		
		if( originalBL.length() != 8 )
		{
			System.out.println( "Invalid plaintext size(8-byte)" );
			return;
		}

		originalBlock = originalBL.toString().toCharArray();
		
		int i = 0;
		while( i < originalBlock.length )
		{
			if( i < 4 )
				plaintextL.append( originalBlock[ i ] );
			else
				plaintextR.append( originalBlock[ i ] );
			i++;
		}
		System.out.println( "Li is " + plaintextL );
		System.out.println( "Ri is " + plaintextR );
		StringBuilder Li = toBinaryString( plaintextL.toString() );
		StringBuilder Ri = toBinaryString( plaintextR.toString() );
		System.out.println( "The binary format of Li is " + display( Li.toString() ) );
		System.out.println( "The binary format of Ri is " + display( Ri.toString() ) );

		StringBuilder newPlaintextR = new StringBuilder();
		
		//convert 'keys' into binary String
		String key = keysToString( keys );
		System.out.println( "key is " + display( key ) );
		
		newPlaintextR = Mangler( plaintextR, key, sBOX );
		
		String ciphertext = "";
		//System.out.println( toBinaryString( plaintextR.toString() ) );
		ciphertext = Ri + XOR( Li, newPlaintextR, 32 );
		
		System.out.println( "After one DES round, the result is " + display( ciphertext ) );
		System.out.println( "The ciphertext is hex format is " + toHex( ciphertext ) );
		
		long finish = System.currentTimeMillis();
		System.out.println( "Program's running time is " + (finish-start) + " ms." );

	}
	
	public static String toHex( String ciphertext )
	{
		StringBuilder temp;
		String subtemp;
		StringBuilder Hex_Ciphertext = new StringBuilder();
		
		for( int i = 0; i < ciphertext.length(); i += 8 )
		{
			temp = new StringBuilder( ciphertext.substring( i, i+8 ) ).reverse();
			
			for( int j = 0; j < 8; j += 4 )
			{
				subtemp = temp.substring( j , j+4 );
				Hex_Ciphertext.append( Integer.toHexString( Integer.parseInt( subtemp, 2 ) ) );
			}
			
		}
		return Hex_Ciphertext.toString();
	}
		
	public static String keysToString( char[] keys )
	{
		StringBuilder key = new StringBuilder();
		for( int i = 0; i < 6; i++ )
		{
			for (int j = 0; j < 8; j++)
			{
				key.append( (keys[ i ] & 1) == 0 ? 0 : 1 );
				keys[ i ] >>= 1;
			}
		}
		
		return key.toString();
	}
	
	public static StringBuilder Mangler( StringBuilder plaintextR, String key, int[][][] sBOX )
	{
		StringBuilder newPlaintextR = Expansion( plaintextR );
		String afterXOR = XOR( newPlaintextR, new StringBuilder( key ), 48 );
		System.out.println( "After XOR between expanded Ri and key, the result is " + afterXOR );
		String sBoxResult = S_BOX( afterXOR, sBOX );
		System.out.println( "After Substitution box, result is " + sBoxResult );
		String permuResult = Permutation( sBoxResult );
		System.out.println( "After permutation, the result is " + permuResult );
		
		return new StringBuilder( permuResult );
	}
	
	public static String Permutation( String sBoxResult )
	{
		StringBuilder permuResult = new StringBuilder();
		permuResult.append( sBoxResult.charAt(15) );
		permuResult.append( sBoxResult.charAt(6) );
		permuResult.append( sBoxResult.charAt(19) );
		permuResult.append( sBoxResult.charAt(20) );
		permuResult.append( sBoxResult.charAt(28) );
		permuResult.append( sBoxResult.charAt(11) );
		permuResult.append( sBoxResult.charAt(27) );
		permuResult.append( sBoxResult.charAt(16) );
		
		permuResult.append( sBoxResult.charAt(0) );
		permuResult.append( sBoxResult.charAt(14) );
		permuResult.append( sBoxResult.charAt(22) );
		permuResult.append( sBoxResult.charAt(25) );
		permuResult.append( sBoxResult.charAt(4) );
		permuResult.append( sBoxResult.charAt(17) );
		permuResult.append( sBoxResult.charAt(30) );
		permuResult.append( sBoxResult.charAt(9) );
		
		permuResult.append( sBoxResult.charAt(1) );
		permuResult.append( sBoxResult.charAt(7) );
		permuResult.append( sBoxResult.charAt(23) );
		permuResult.append( sBoxResult.charAt(13) );
		permuResult.append( sBoxResult.charAt(31) );
		permuResult.append( sBoxResult.charAt(26) );
		permuResult.append( sBoxResult.charAt(2) );
		permuResult.append( sBoxResult.charAt(8) );
		
		permuResult.append( sBoxResult.charAt(18) );
		permuResult.append( sBoxResult.charAt(12) );
		permuResult.append( sBoxResult.charAt(29) );
		permuResult.append( sBoxResult.charAt(5) );
		permuResult.append( sBoxResult.charAt(21) );
		permuResult.append( sBoxResult.charAt(10) );
		permuResult.append( sBoxResult.charAt(3) );
		permuResult.append( sBoxResult.charAt(24) );
		
		return permuResult.toString();
	}
	
	public static String S_BOX( String afterXOR, int[][][] sBOX )
	{
		String temp;
		StringBuilder interm;
		StringBuilder sBoxRet = new StringBuilder();

		for( int i = 0; i < 8; i++ )
		{
			temp = afterXOR.substring( i * 6, i*6 + 6 );
			int row = (temp.charAt( 0 ) - '0') + 2 * (temp.charAt( 5 ) - '0');
			int column = (temp.charAt( 1 ) - '0') + 2 * (temp.charAt( 2 ) - '0') + 4 * (temp.charAt( 3 ) - '0') + 8 * (temp.charAt( 4 ) - '0');
			int result = sBOX[ i ][ row ][ column ];
			interm = new StringBuilder( String.format( "%4s", Integer.toBinaryString( result ) ) ).reverse();
			sBoxRet.append( interm.toString().replace( ' ', '0' ) );
		}
		
		return sBoxRet.toString();
	}
	
	public static String XOR( StringBuilder op1, StringBuilder op2, int bitNum )
	{
		StringBuilder result = new StringBuilder();
		for( int i = 0; i < bitNum; i++ )
		{
			if( op1.charAt( i ) == op2.charAt( i ) )
				result.append( '0' );
			else
				result.append( '1' );
		}
		
		return result.toString();
	}
	
	
	public static StringBuilder Expansion( StringBuilder plaintextR )
	{
		StringBuilder beforeExpan = new StringBuilder();
		StringBuilder afterExpan = new StringBuilder();
		int i = 0;
		int j = 0;
		
		beforeExpan = toBinaryString( plaintextR.toString() );
		System.out.println( "Before Expansion, the result is " + beforeExpan.toString() );
		
		for( i = 0; i < 8; i++ )
		{
			if( i == 0 )
				afterExpan.append( beforeExpan.charAt( 31 ) );
			else
				afterExpan.append( beforeExpan.charAt( j-1 ) );
			
			for( j = i*4; j < 4 + i*4; j++ )
				afterExpan.append( beforeExpan.charAt( j ) );
			
			if( i == 7 )
				afterExpan.append( beforeExpan.charAt( 0 ) );
			else
				afterExpan.append( beforeExpan.charAt( j ) );
		}
		
		System.out.println( "After Expansion, the result is " + display( afterExpan.toString() ) );
		
		return afterExpan;
	}
	
	public static StringBuilder toBinaryString( String plaintextR )
	{
		StringBuilder binary = new StringBuilder();
		byte[] bytes = plaintextR.getBytes();
		
		for (byte b : bytes)
		{
		     int val = b;
		     for (int i = 0; i < 8; i++)
		     {
		        binary.append((val & 1) == 0 ? 0 : 1);
		        val >>= 1;
		     }
		}
		
		return binary;
	}
	
	public static String display( String bitStream )
	{
		StringBuilder res = new StringBuilder();
		
		for( int i =0; i < bitStream.length(); i ++ )
		{
			res.append( bitStream.charAt( i ) );
			
			if( (i+1)%8 == 0 )
			{
				res.append( " " );
			}
		}
		
		return res.toString();
	}	
}