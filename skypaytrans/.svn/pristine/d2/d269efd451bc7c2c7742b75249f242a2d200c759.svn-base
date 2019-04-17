package com.saifintex.utils;

import ru.bullyboo.encoder.Encoder;
import ru.bullyboo.encoder.methods.AES;

public class EncryptionDecryptionUtils {
	
	private static String key="9a56cd6e-0af8-4947-abd5-e64558fc2ae9";
    private static String vectorKey="test_vector";
    
	public static String encryptQRData(String message){
        String encrypt = Encoder.BuilderAES()
                .message(message)
                .method(AES.Method.AES_CBC_PKCS5PADDING)
                .key(key)
                .keySize(AES.Key.SIZE_128)
                .iVector(vectorKey)
                .encrypt();
        return encrypt;
    }
}
