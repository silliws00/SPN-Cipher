// Aira, Ria, Sara

package Proj2;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        SPN spn = new SPN();

        int[] sBox = InputValidation.readSBox(); //S-box values
        int[] perm = InputValidation.readPermutation(); //permutation values

        //number of generations for simulation
        int numPlaintexts = 200;
        int keysPerPlaintext = 500;

        //initialize counters
        int countSigma0LA0 = 0; //counts when the linear approximation equals 0 when sigmaK = 0
        int countSigma0Total = 0; //counts total times when sigmaK = 0
        int countSigma1LA0 = 0; //counts when the linear approximation equals 0 when sigmaK = 1
        int countSigma1Total = 0; //counts total times when sigmaK = 1

        //simulation loop
        for (int pIndex = 0; pIndex < numPlaintexts; pIndex++) {
            //generate plainText
            String plainText = LinearApproximation.generateRandomBinary(16, rand);

            for (int keyIndex = 0; keyIndex < keysPerPlaintext; keyIndex++) {
                //generate round keys
            	String masterKey = LinearApproximation.generateRandomBinary(32, rand);
            	String[] keys = spn.generateRoundKeys(masterKey, 3);

                //compute sigmaK for the linear approximation
                int sigmaK = LinearApproximation.computeSigmaK(keys);

                //encrypt
                String currentState = spn.xor(plainText, keys[0]);
                currentState = spn.substitute(currentState, sBox);
                currentState = spn.permute(currentState, perm);

                currentState = spn.xor(currentState, keys[1]);
                currentState = spn.substitute(currentState, sBox);
                currentState = spn.permute(currentState, perm);

                //stop at third round
                String thirdRoundSboxOutput = spn.substitute(spn.xor(currentState, keys[2]), sBox);

                //compute linear approximation value
                int linearVal = LinearApproximation.computeLinearVal(plainText, thirdRoundSboxOutput);

                if (sigmaK == 0) {
                    countSigma0Total++;
                    if (linearVal == 0) countSigma0LA0++;
                } 
                else {
                    countSigma1Total++;
                    if (linearVal == 0) {
                    	countSigma1LA0++;
                    }
                }
            }
        }

        //compute probabilities
        double probSigma0;
        if (countSigma0Total == 0) {
            probSigma0 = 0.0;
        } else {
            probSigma0 = (double) countSigma0LA0 / countSigma0Total;
        }

        double probSigma1;
        if (countSigma1Total == 0) {
            probSigma1 = 0.0;
        } else {
            probSigma1 = (double) countSigma1LA0 / countSigma1Total;
        }

        System.out.printf("Probability sigmaK=0: %.4f\n", probSigma0);
        System.out.printf("Probability sigmaK=1: %.4f\n", probSigma1);
    }
}