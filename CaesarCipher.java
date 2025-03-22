import java.util.Scanner;

public class CaesarCipher {

    // Encrypt the message by shifting each letter by the given shift value
    public static String encrypt(String plaintext, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);

            if (Character.isUpperCase(ch)) {
                char encryptedChar = (char) (((ch - 'A' + shift) % 26) + 'A');
                encryptedText.append(encryptedChar);
            } else if (Character.isLowerCase(ch)) {
                char encryptedChar = (char) (((ch - 'a' + shift) % 26) + 'a');
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(ch);  // Keep spaces, punctuation, etc.
            }
        }
        return encryptedText.toString();
    }

    // Decrypt the message by reversing the shift
    public static String decrypt(String ciphertext, int shift) {
        return encrypt(ciphertext, 26 - shift);  // Reverse shift
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the message to encrypt: ");
        String plaintext = sc.nextLine();

        System.out.print("Enter the shift value (1 to 25): ");
        int shift = sc.nextInt();

        String encryptedText = encrypt(plaintext, shift);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, shift);
        System.out.println("Decrypted Text: " + decryptedText);

        sc.close();
    }
}
