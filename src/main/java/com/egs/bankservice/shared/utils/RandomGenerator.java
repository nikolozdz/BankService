package com.egs.bankservice.shared.utils;

import java.util.Random;

public class RandomGenerator {
    public static String generatePin(){
        Random random = new Random();
        int pin = random.nextInt(10000);
        return String.format("%04d", pin);
    }

    public static String generateCardNumber(int start, int length) {
        String prefix = Integer.toString(start);
        Random random = new Random();
        int rnd = random.nextInt();
        rnd = (rnd > 0) ? rnd : -rnd;
        return prefix + format(length, rnd);
    }

    private static String format(int targetLength, int generatedCardNumber){
        String genCardNum = Integer.toString(generatedCardNumber);

        if (genCardNum.length() >= targetLength){
            return genCardNum.substring(0,targetLength);
        } else {
            StringBuilder sb = new StringBuilder();
            int zeroesToAdd = targetLength - genCardNum.length();
            for (int i = 0; i < zeroesToAdd; i++)
                sb.append('0');
            return sb.toString() + genCardNum;
        }
    }
}
