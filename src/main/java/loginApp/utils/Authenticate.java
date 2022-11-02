package loginApp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Authenticate {

    public static String generateSalt() {
        byte[] saltByte = new byte[6];
        SecureRandom sec = new SecureRandom();
        sec.nextBytes(saltByte);
        StringBuilder str = new StringBuilder();

        for (byte b : saltByte) {
            str.append(Integer.toString((b & 255) + 256, 16).substring(1));
        }
        return str.toString();
    }

    public static String hashPassword(String passToHash, String salt) {
        passToHash = passToHash + salt;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashData = md.digest(passToHash.getBytes());
            StringBuilder str = new StringBuilder();

            for (byte hashDatum : hashData) {
                str.append(Integer.toString((hashDatum & 255) + 256, 16).substring(1));
            }

            return str.toString();
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return null;
        }
    }
}

