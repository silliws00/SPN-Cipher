// Aira, Ria, Sara

package Proj2;

import java.util.Scanner;

public class InputValidation {

	static Scanner scnr = new Scanner(System.in);

	public static boolean isValidBinary(String s, int length){
		//check if string is empty
	    if (s == null){
	        return false;
	    }

	    //check for correct length
	    if (s.length() != length){
	        return false;
	    }

	    //check to make sure it is either 0 or 1 (binary)
	    for (int i = 0; i < s.length(); i++){
	        char c = s.charAt(i);

	        if (c != '0' && c != '1'){
	            return false;
	        }
	    }

	    return true;
	}

    public static String readBinary(String prompt, int length){
        while (true){
            System.out.print(prompt);
            String input = scnr.nextLine().replaceAll("\\s+", "");

            if (input.matches("[01]+") && input.length() == length) {
                return input;
            }

            System.out.println("Invalid binary string. Must be " + length + " bits.");
        }
    }

    public static String readKey() {
        while (true) {
            System.out.print("Enter 32-bit key: ");
            //remove whitespace in user input
            String key = scnr.nextLine().replaceAll("\\s+", "");

            //check length
            if (key.length() != 32) {
                System.out.println("Invalid key. Must be 32 bits.");
                continue;
            }

            //check each character
            boolean valid = true;
            for (int i = 0; i < key.length(); i++) {
                char c = key.charAt(i);
                if (c != '0' && c != '1') {
                    valid = false;
                }
            }

            if (valid) {
                return key; //valid key
            } else {
                System.out.println("Invalid key. Must be 32 bits of 0s and 1s.");
            }
        }
    }

    public static int[] readSBox(){
        int[] sBox = new int[16];
        while (true) {
            System.out.println("Enter 16 S-Box values (0-15):");

            //reads each value in the array and makes sure there are no duplicates
            boolean[] readValue = new boolean[16];
            boolean valid = true;

            int i = 0;

            while (i < 16){
            	//if not an integer
                if (!scnr.hasNextInt()){
                    valid = false;
                    scnr.next();
                } 
                else{
                    int val = scnr.nextInt();
                    //if the value is <0 or >15 or has been used already
                    if (val < 0 || val > 15 || readValue[val]) {
                        valid = false;
                    } 
                    else{
                        sBox[i] = val;
                        readValue[val] = true;
                    }
                }
                i++;
            }

            //clear if leftover newline in input
            if (scnr.hasNextLine()){
                scnr.nextLine();
            }

            //if the input is valid, return the s-box
            if (valid){
                return sBox;
            }

            System.out.println("Invalid S-Box. Must be 0–15 unique values.");
        }
    }

    public static int[] readPermutation(){
        int[] perm = new int[16];
        while (true){
            System.out.println("Enter 16 permutation values (1-16):");

            boolean[] readValue = new boolean[17];
            boolean valid = true;

            int i = 0;

            while (i < 16) {
            	//if not an integer
                if (!scnr.hasNextInt()){
                    valid = false;
                    scnr.next();
                } 
                else{
                    int val = scnr.nextInt();

                    //if val is <1 or >16 or has already been used
                    if (val < 1 || val > 16 || readValue[val]){
                        valid = false;
                    } 
                    else{
                        perm[i] = val;
                        readValue[val] = true;
                    }
                }
                i++;
            }

            //clear if leftover newline in input
            if (scnr.hasNextLine()){
                scnr.nextLine();
            }

            //if valid returns the permutation
            if (valid) {
                return perm;
            }

            System.out.println("Invalid permutation.");
        }
    }

}
