package com.marco.unicorsi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/* 
    Questa classe gestisce una chiave per la cifratura AES rendendola una proprietà della 
    applicazione, accessibile da varie componenti come Controller e Configurazioni.
*/

@ConfigurationProperties("cript")
public class KeyProperties{

    /* IMPORTANTE: la dimensione deve essere ben gestita, in questo caso 16 byte
    vedere AES per dimensioni chiavi*/ 
    private String key = "1234567812345678";

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

}