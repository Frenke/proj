package com.marco.unicorsi.config;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AesUtil {

    /*
        Il metodo consente di decifrare una stringa cifrata tramite AES,
        un sistema di cifratura simmetrica, al metodo viene passata una stringa cifrata
        e la chiave da utilizzare per la decifratura.
        Le eccezioni generate possono riguardare la composizione della chiave o del vettore
        di inizializzazione.
    */
    public String decrypt(String toBeDec, String keyString) {
        try{          
            Decoder decoder = Base64.getDecoder();
            byte[] enc = decoder.decode(toBeDec);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(keyString.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyString.getBytes());
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