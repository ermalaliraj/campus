package org.sg.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

@Slf4j
public class Cryptor {

    private static final Logger logger = LoggerFactory.getLogger(Cryptor.class);

    private static final String UNICODE_FORMAT = "UTF8";
    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private byte[] arrayBytes;
    private SecretKey key;

    public String decrypt(String encryptedString) {
        String decryptedText = null;
        Configuration config = new Configuration();

        String myEncryptionKey = config.getProperty("encKey");
        String myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;

        boolean skipDecrypt = Boolean.parseBoolean(config.getProperty("skipDecrypt"));
        if (skipDecrypt) {
            return encryptedString;
        }
        try {
            arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
            ks = new DESedeKeySpec(arrayBytes);
            skf = SecretKeyFactory.getInstance(myEncryptionScheme);
            cipher = Cipher.getInstance(myEncryptionScheme);
            key = skf.generateSecret(ks);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            logger.error("Error decrypting", e);
        }
        return decryptedText;
    }
}