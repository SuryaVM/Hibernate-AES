package com;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
	 // Generate a random AES key
    public static SecretKey generateKey(int n) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n); // 128, 192, or 256 bits
        return keyGenerator.generateKey();
    }

    // Encrypt using AES/CBC/PKCS5Padding
    public static String encrypt(String data, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Decrypt using AES/CBC/PKCS5Padding
    public static String decrypt(String encryptedData, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decrypted);
    }

    public static void main(String a[]) {
    	Data d1 = new Data();
    	String plain_txt;
        try {

        	try (Scanner sc = new Scanner(System.in)) {
				System.out.print("Enter the plain text:");
				plain_txt = sc.nextLine();
			}

        	byte[] byte_iv = new byte[16]; // 16 bytes for AES
        	SecureRandom random = new SecureRandom(); // random data filling in iv
        	random.nextBytes(byte_iv);
        	IvParameterSpec iv = new IvParameterSpec(byte_iv);// 16-byte IV for AES-128/192/256
            String str_iv=Base64.getEncoder().encodeToString(byte_iv);

			SecretKey key = generateKey(256);
			String str_key=Base64.getEncoder().encodeToString(key.getEncoded()); //get.Encoded() gives byte[] raw data which is expected here

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
	        byte[] encrypted = cipher.doFinal(plain_txt.getBytes());
	        String cipher_txt = Base64.getEncoder().encodeToString(encrypted);

			d1.setPlain_txt(plain_txt);
	    	d1.setInitial_vector(str_iv);
	    	d1.setKey(str_key);
	    	d1.setCipher_txt(cipher_txt);

		}
        catch (Exception e) {
			e.printStackTrace();
		}
        Configuration config = new Configuration();
        config.addAnnotatedClass(com.Data.class); //com.data is a java file in package, .class tells it is a java class
        config.configure();
        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(d1);
        transaction.commit();
        System.out.println(d1.toString());
    }

}
