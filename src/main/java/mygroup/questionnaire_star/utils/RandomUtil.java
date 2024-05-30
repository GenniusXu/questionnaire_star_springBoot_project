package mygroup.questionnaire_star.utils;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {

    private static final String CHAR_SET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomCode(int length) {
        if (length <= 0) {
            return null;
        }
        int charSetLength = CHAR_SET.length();
        StringBuilder randomCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            randomCode.append(CHAR_SET.charAt(RANDOM.nextInt(charSetLength)));
        }
        return randomCode.toString();
    }

    public static String buildCheckCode(int digit){
        String str = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < digit; i++) {
            char ch = str.charAt(random.nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
