// Aira, Ria, Sara

package Proj2;

import java.util.Random;

public class LinearApproximation {
	
	public static int getBit(String s, int index) {
		//convert from char digit to actual integer
        return s.charAt(index) - '0';
    }

	//get the specific bits from linear approximation formula
	public static int computeSigmaK(String[] keys) {
	    int bit1 = getBit(keys[0], 4);  // K1,5
	    int bit2 = getBit(keys[0], 6);  // K1,7
	    int bit3 = getBit(keys[0], 7);  // K1,8
	    int bit4 = getBit(keys[1], 5);  // K2,6
	    int bit5 = getBit(keys[2], 5);  // K3,6
	    int bit6 = getBit(keys[2], 13); // K3,14

	    int sum = bit1 + bit2 + bit3 + bit4 + bit5 + bit6;

	    //XOR equation
	    if (sum % 2 == 0) {
	        return 0;
	    } 
	    else {
	        return 1;
	    }
	}

	//computes the linear approximation expression
	public static int computeLinearVal(String plaintext, String q) {
	    //get the specific bits
	    int p5  = getBit(plaintext, 4);   // P5
	    int p7  = getBit(plaintext, 6);   // P7
	    int p8  = getBit(plaintext, 7);   // P8
	    int q6  = getBit(q, 5);           // Q6
	    int q8  = getBit(q, 7);           // Q8
	    int q14 = getBit(q, 13);          // Q14
	    int q16 = getBit(q, 15);          // Q16

	    int sum = p5 + p7 + p8 + q6 + q8 + q14 + q16;

	    //XOR equation
	    if (sum % 2 == 0) {
	        return 0;
	    } else {
	        return 1;
	    }
	}

	
	//generates random binary for plaintext and keys
	public static String generateRandomBinary(int length, Random rand) {
	    StringBuilder randomBits = new StringBuilder();
	    for (int i = 0; i < length; i++) {
	        if (rand.nextBoolean()) {
	            randomBits.append('1');
	        } else {
	            randomBits.append('0');
	        }
	    }
	    return randomBits.toString();
	}
	
}
