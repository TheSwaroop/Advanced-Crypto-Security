import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class AESEncryption {
    public static void main(String[] args) {
        try {
            // Generate AES key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // You can also use 192 or 256 if supported
            SecretKey secretKey = keyGenerator.generateKey();

            // Create AES cipher instance
            Cipher cipher = Cipher.getInstance("AES");

            // Read plaintext input
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter plaintext: ");
            String plaintext = scanner.nextLine();
            scanner.close();

            // Encrypt the plaintext
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            System.out.println("Encrypted: " + encryptedText);

            // Decrypt the ciphertext
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            String decryptedText = new String(decryptedBytes);
            System.out.println("Decrypted: " + decryptedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
