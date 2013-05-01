
public class CountSameBit {

	
	public static int Count( StringBuffer before, StringBuffer after )
	{		
		int count = 0;
		
		if( (before.length() % 2 != 0) || (after.length() % 2 != 0) )
		{
			System.out.println( "Parameter length fault!" );
			System.exit(1);
		}
		
		int i = 0;
		while( i < before.length() )
		{
			StringBuffer a = new StringBuffer();
			StringBuffer b = new StringBuffer();
			
			a.append( before.charAt( i ) );
			a.append( before.charAt( i + 1 ) );
			b.append( after.charAt( i ) );
			b.append( after.charAt( i + 1 ) );
			
			int m = Integer.parseInt( a.toString(),  16 );
			int n = Integer.parseInt( b.toString(),  16 );
			
			for( int mark = 128; mark > 0; mark >>= 1 )
			{
				if( (m & mark ) == (n & mark) )
					count += 1;
			}
			i+=2;
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		StringBuffer before = new StringBuffer("ff7a9c0009aa90b78d016f01073fb5bcf8c1e55f");
		StringBuffer after = new StringBuffer("8c6693deea4cc211f0af0ec69166af30fb261381");
		System.out.println( "The number of same bits is " + Count( before, after ) );
	}
}
