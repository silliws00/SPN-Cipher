// Aira, Ria, Sara

package Proj2;

public class SPN {
	
	//xor function
	public String xor(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++){
        	if (a.charAt(i) == b.charAt(i)){
                result.append('0');
            } 
        	else{
                result.append('1');
            }
        }
        
        return result.toString();
    }

	//substitution
    public String substitute(String input, int[] sBox) {
        StringBuilder output = new StringBuilder();

        //goes through input in 4-bit blocks
        for (int i = 0; i < 16; i += 4) {
        	//creates substrings of the 4-bit blocks
            int index = Integer.parseInt(input.substring(i, i + 4), 2);
            int val = sBox[index];
            //convert from int to binary string
            String binary = Integer.toBinaryString(val);

         //ensure 4 bits, separate with leading 0
         while (binary.length() < 4){
             binary = "0" + binary;
         }

         //append to result
         output.append(binary);
        }

        return output.toString();
    }

    //permutation
    public String permute(String input, int[] perm){
        char[] output = new char[16];
        //reorder input according to perm table
        for (int i = 0; i < 16; i++) {
            output[i] = input.charAt(perm[i] - 1);
        }
        return new String(output);
    }

    public int[] invertSBox(int[] sBox){
        int[] inverse = new int[16];
        for (int i = 0; i < 16; i++) {
        	//invert sbox values
        	inverse[sBox[i]] = i;
        }
        return inverse;
    }

    public int[] invertPerm(int[] perm){
        int[] inverse = new int[16];
        for (int i = 0; i < 16; i++){
        	//invert perm values
        	inverse[perm[i] - 1] = i + 1;
        }
        return inverse;
    }

    public String[] generateRoundKeys(String key, int rounds){
        String[] keys = new String[rounds];
        for (int i = 0; i < rounds; i++) {
        	keys[i] = key.substring(0, 16);
        }
        return keys;
    }

    public String encrypt(String plaintext, String[] keys, int[] sBox, int[] perm, int rounds){
        String currentPlain = plaintext;

        //each round but last two
        for (int i = 0; i < rounds - 2; i++){
            String xorResult = xor(currentPlain, keys[i]);
            String sboxResult = substitute(xorResult, sBox);
            currentPlain = permute(sboxResult, perm);
        }
        
        //don't permute last two rounds
        //second-to-last round
        String xorResult = xor(currentPlain, keys[rounds - 2]);
        String sboxResult = substitute(xorResult, sBox);

        //final round
        return xor(sboxResult, keys[rounds - 1]);
    }

    public String decrypt(String ciphertext, String[] keys, int[] sBox, int[] perm, int rounds){
        int[] invSBox = invertSBox(sBox);
        int[] invPerm = invertPerm(perm);

        String undoXor = xor(ciphertext, keys[rounds - 1]);
        String undoSbox = substitute(undoXor, invSBox);
        String undoSecondLastXor = xor(undoSbox, keys[rounds - 2]);

        for (int i = rounds - 3; i >= 0; i--){
            String vPrev = permute(undoSecondLastXor, invPerm);
            String uPrev = substitute(vPrev, invSBox);
            undoSecondLastXor = xor(uPrev, keys[i]);
        }

        return undoSecondLastXor;    
     }

}
