package com.urlshortener.util;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import com.urlshortener.service.ShortenerService;

public class HashUtils {
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private HashUtils(){}

    public static String base62Hasher(long id){
        var sb = new StringBuilder ();
        while(id != 0){
            var index = (id % 62);
            var charToAppend = BASE62.charAt ((int) index);
            sb.append (charToAppend);
            id /= 62;
        }
        while (sb.length() < 6) {
            sb.append(0);
        }
        return ShortenerService.BASE_SHORTENED_URL.concat (sb.toString ());
    }

    public static Optional <Long> sha256Hasher (String stringToHash){
        MessageDigest encryptor;
        try {
            encryptor = MessageDigest.getInstance ("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace ();
            return Optional.empty ();
        }

        encryptor.update (stringToHash.getBytes(StandardCharsets.UTF_8));

        //A bad practice which may cause a data loss hence a possible hash collision.
        return Optional.of (new BigInteger (1,encryptor.digest ()).longValue ());
    }

}
