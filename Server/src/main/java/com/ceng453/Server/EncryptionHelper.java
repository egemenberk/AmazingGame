package com.ceng453.Server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

class EncryptionHelper {

    /*
     * Standart encrpytion method, for storing user password in hashed form.
     */
    public static String encrypt( String s ) throws NoSuchAlgorithmException {
        MessageDigest message_digest = MessageDigest.getInstance("MD5");
        byte[] digested_bytes = message_digest.digest(s.getBytes());
        StringBuffer hex_string = new StringBuffer();
        for(int i=0; i<digested_bytes.length; i++){
            hex_string.append(Integer.toHexString(0xff & digested_bytes[i]));
        }
        return hex_string.toString();
    }

    /*
     * A random token generator code, for generation session_tokens for users
     */
    public static String generateToken() throws NoSuchAlgorithmException {
        String uuid = UUID.randomUUID().toString();
        return encrypt(uuid);
    }
}
