package com.flk.streaming;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Main {
	
	static String ALGO = "AES";

	public static void main(String[] args) {
		//GenerateKeys gk;
		//GenerateKeys gk2;
		try {
			//gk = new GenerateKeys(1024);
			//gk.createKeys();
			
			//gk2 = new GenerateKeys(1024);
			//gk2.createKeys();
			
			FileOutputStream fileOutput = new FileOutputStream("CipherOutput.txt");
            FileInputStream fileInput = new FileInputStream("CipherInput.txt");
            
            final PrintWriter pw = new PrintWriter(fileOutput);
            pw.println("Cipher Streams are working correctly.");
            pw.flush();
            pw.close();
            fileOutput.close();
            
            //String initialString = "Hello world !\nMy name is Fathi";
            //InputStream in = new ByteArrayInputStream(initialString.getBytes());
            
            final KeyGenerator kg = KeyGenerator.getInstance(ALGO);
            kg.init(new SecureRandom(new byte[]{1, 2, 3}));
            final SecretKey key = kg.generateKey();
			
            InputStream encryptedSource = encrypt(fileInput, key);
			InputStream decryptedSource = decrypt(encryptedSource, key);
			

            final InputStreamReader r = new InputStreamReader(decryptedSource);
            final BufferedReader reader = new BufferedReader(r);
            String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			fileInput.close();
			encryptedSource.close();
			decryptedSource.close();
			
		} catch (NoSuchAlgorithmException | IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	public static InputStream encrypt(InputStream source, SecretKey key) throws IOException {
	    PipedOutputStream pos = null;
	    PipedInputStream pis = null;
        CipherInputStream cis = null;

	    try {

	        pos = new PipedOutputStream();
	        pis = new PipedInputStream(pos);
	        //PrintStream ps = new PrintStream(pos);

	        
	        final Cipher cipher = Cipher.getInstance(ALGO);
	        cipher.init(Cipher.ENCRYPT_MODE, key);
            cis = new CipherInputStream(source, cipher);
            
            int length;
            byte[] bytes = new byte[1024];
            // copy data from input stream to output stream
            while ((length = cis.read(bytes)) != -1) {
                pos.write(bytes, 0, length);
                pos.flush();
            }
            
            
            
            /*final InputStreamReader r = new InputStreamReader(input);
            final BufferedReader reader = new BufferedReader(r);
            final String line = reader.readLine();
            System.out.println("Line : " + line);*/

	        //new WriteReversedThread(ps, dis).start();

	    } catch (Exception e) {
	        System.out.println("encrypt: " + e);
	    } finally {
	    	if (pos != null) {
	    		pos.flush();
	    		pos.close();
	    	}
	    	if (cis != null) {
	    		cis.close();
	    	}
	    }
	    return pis;
	}
	
	public static InputStream decrypt(InputStream source, SecretKey key) throws IOException {
	    PipedOutputStream pos = null;
	    PipedInputStream pis = null;
        CipherInputStream cis = null;

	    try {

	        pos = new PipedOutputStream();
	        pis = new PipedInputStream(pos);

	        
	        final Cipher cipher = Cipher.getInstance(ALGO);
	        cipher.init(Cipher.DECRYPT_MODE, key);
            cis = new CipherInputStream(source, cipher);
            
            int length;
            byte[] bytes = new byte[1024];
            // copy data from input stream to output stream
            while ((length = cis.read(bytes)) != -1) {
                pos.write(bytes, 0, length);
                pos.flush();
            }
            

	    } catch (Exception e) {
	        System.out.println("decrypt: " + e);
	    } finally {
	    	if (pos != null) {
	    		pos.flush();
	    		pos.close();
	    	}
	    	if (cis != null) {
	    		cis.close();
	    	}
	    }
	    return pis;
	}
	
	
	private void runExperiments() {

        CipherOutputStream output = null;
        CipherInputStream input = null;
        FileOutputStream fileOutput = null;
        FileInputStream fileInput = null;

        try {

            fileOutput = new FileOutputStream("CipherOutput.txt");
            fileInput = new FileInputStream("CipherOutput.txt");

            final KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(new SecureRandom(new byte[]{1, 2, 3}));
            final SecretKey key = kg.generateKey();

            final Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, key);
            output = new CipherOutputStream(fileOutput, c);

            final PrintWriter pw = new PrintWriter(output);
            pw.println("Cipher Streams are working correctly.");
            pw.flush();
            pw.close();



            final KeyGenerator kg2 = KeyGenerator.getInstance("AES");
            kg2.init(new SecureRandom(new byte[]{1, 2, 3}));
            final SecretKey key2 = kg2.generateKey();

            final Cipher c2 = Cipher.getInstance("AES");
            c2.init(Cipher.DECRYPT_MODE, key2);
            input = new CipherInputStream(fileInput, c2);

            final InputStreamReader r = new InputStreamReader(input);
            final BufferedReader reader = new BufferedReader(r);
            final String line = reader.readLine();
            System.out.println("Line : " + line);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Specified Algorithm does not exist");
        } catch (NoSuchPaddingException e) {
            System.out.println("Specified Padding does not exist");
        } catch (FileNotFoundException e) {
            System.out.println("Could not find specified file to read / write to");
        } catch (InvalidKeyException e) {
            System.out.println("Specified key is invalid");
        } catch (IOException e) {
            System.out.println("IOException from BufferedReader when reading file");
        } finally {
            try {
                if (fileInput != null) {
                    fileInput.close();
                }
                if (fileOutput != null) {
                    fileOutput.flush();
                    fileOutput.close();
                }
                if (output != null) {
                    output.flush();
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
