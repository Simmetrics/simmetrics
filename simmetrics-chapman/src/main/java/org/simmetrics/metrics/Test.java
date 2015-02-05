package org.simmetrics.metrics;

public class Test {

	public static void main(String[] args) {
		int line = 0;
		line |= (1 << 0);
		line |= (1 << 1);
		line |= 0000000000;

		System.out.println(toBinaryString(line));
		
		System.out.println(32-Integer.highestOneBit(line));

		

	}

	public static String toBinaryString(int i) {
		return toUnsignedString(i, 1);
	}

	/**
	 * Convert the integer to an unsigned number.
	 */
	private static String toUnsignedString(int i, int shift) {
		char[] buf = new char[32];
		int charPos = 32;
		int radix = 1 << shift;
		int mask = radix - 1;
		
		
		for (int n = 0; n < buf.length; n++){
			buf[--charPos] = digits[i & mask];
			i >>>= shift;
		} 

		return new String(buf, charPos, (32 - charPos));
	}
	
    final static char[] digits = {
        '0' , '1' , '2' , '3' , '4' , '5' ,
        '6' , '7' , '8' , '9' , 'a' , 'b' ,
        'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
        'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
        'o' , 'p' , 'q' , 'r' , 's' , 't' ,
        'u' , 'v' , 'w' , 'x' , 'y' , 'z'
    };
}
