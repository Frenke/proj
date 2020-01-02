package com.marco.unicorsi.config;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesUtil {

    

    public static String decrypt(String toBeDec) {
        System.err.println(toBeDec);
        try{    
            String key = "1234567812345678";
            Decoder decoder = Base64.getDecoder();
            byte[] enc = decoder.decode(toBeDec);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(enc);
            String originalString = new String(original);
            System.out.println(originalString.trim());
            return originalString.trim();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


}