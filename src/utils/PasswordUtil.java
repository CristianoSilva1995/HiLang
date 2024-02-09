package utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
/**
 * Password Util
 * Responsible to encrypt the password
 * @author Cristiano Silva
 */
public class PasswordUtil {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwyz@#!£&*^";
    private static final Integer ITERATIONS = 100;
    private static final Integer KEY_LENGTH = 256;

    public static String getSalt(int length){
        StringBuilder returnValue = new StringBuilder(length);
        for(int i = 0; i < length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public static byte[] hash(char[] password, byte[] salt){
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return  secretKeyFactory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } finally {
            spec.clearPassword();
            System.gc();
        }
    }

    public static String generateSecurePassword(String password, String salt){
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }
}
