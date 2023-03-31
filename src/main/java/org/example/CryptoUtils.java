package org.example;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtils {
    public static KeyPair genereateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(512);
        return keyPairGenerator.generateKeyPair();
    }
    public  static String   encodeBase64(byte [] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static byte[] decodeBase64(String encoded){
        return Base64.getDecoder().decode(encoded);
    }
    public static String cryptRSA(String message, String encodedPublicKey) throws Exception {
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        byte[] decodedPK = CryptoUtils.decodeBase64(encodedPublicKey);
        PublicKey publicKey=keyFactory.generatePublic(new X509EncodedKeySpec(decodedPK));
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] enryptedMessage = cipher.doFinal(message.getBytes());
        String encodedMSG = CryptoUtils.encodeBase64(enryptedMessage);
        return  encodedMSG;
    }
    public static String decryptRSA(String  encodedCryptedMsg, String encodedPrivateKey) throws Exception {
        byte[] decodedMSG = CryptoUtils.decodeBase64(encodedCryptedMsg);
        byte[] decodedPrivateKey = CryptoUtils.decodeBase64(encodedPrivateKey);
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        PrivateKey privateKey=keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));
        Cipher cipher=Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] decryptedMSG = cipher.doFinal(decodedMSG);
        return new String(decryptedMSG);
    }
    public static PrivateKey  getPrivateKeyFromKeyStore(String fileName,String password,String alias)throws Exception{
        FileInputStream fileInputStream1=new FileInputStream(fileName);
        KeyStore keyStore=KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(fileInputStream1,password.toCharArray());
        PrivateKey privateKey =(PrivateKey) keyStore.getKey(alias, password.toCharArray());
        return privateKey;

    }
    public static PublicKey getPublicKeyFromCertificate(String fileName)throws Exception{
        FileInputStream fileInputStream=new FileInputStream(fileName);
        CertificateFactory certificateFactory=CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
        //System.out.println(certificate.toString());
        PublicKey publicKey= certificate.getPublicKey();
        return publicKey;
    }

}
