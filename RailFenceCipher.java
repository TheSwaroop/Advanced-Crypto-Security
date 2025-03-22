import java.util.Scanner;

public class RailFenceCipher {

    public static String encrypt(String plaintext, int numRails) {
        if (numRails <= 1) return plaintext;

        StringBuilder[] rails = new StringBuilder[numRails];
        for (int i = 0; i < numRails; i++) rails[i] = new StringBuilder();

        int rail = 0;
        boolean downDirection = true;

        for (char ch : plaintext.toCharArray()) {
            rails[rail].append(ch);
            if (rail == 0) downDirection = true;
            else if (rail == numRails - 1) downDirection = false;
            rail += downDirection ? 1 : -1;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (StringBuilder sb : rails) encryptedText.append(sb);

        return encryptedText.toString();
    }

    public static String decrypt(String ciphertext, int numRails) {
        if (numRails <= 1) return ciphertext;

        int[] railLengths = new int[numRails];
        int rail = 0;
        boolean downDirection = true;

        for (int i = 0; i < ciphertext.length(); i++) {
            railLengths[rail]++;
            if (rail == 0) downDirection = true;
            else if (rail == numRails - 1) downDirection = false;
            rail += downDirection ? 1 : -1;
        }

        StringBuilder[] rails = new StringBuilder[numRails];
        int index = 0;
        for (int i = 0; i < numRails; i++) {
            rails[i] = new StringBuilder(ciphertext.substring(index, index + railLengths[i]));
            index += railLengths[i];
        }

        StringBuilder decryptedText = new StringBuilder();
        int[] railIndexes = new int[numRails];
        rail = 0;
        downDirection = true;

        for (int i = 0; i < ciphertext.length(); i++) {
            decryptedText.append(rails[rail].charAt(railIndexes[rail]++));
            if (rail == 0) downDirection = true;
            else if (rail == numRails - 1) downDirection = false;
            rail += downDirection ? 1 : -1;
        }

        return decryptedText.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the message to encrypt: ");
        String plaintext = sc.nextLine();

        System.out.print("Enter the number of rails: ");
        int numRails = sc.nextInt();

        String encryptedText = encrypt(plaintext, numRails);
        System.out.println("Encrypted Text: " + encryptedText);

        String decryptedText = decrypt(encryptedText, numRails);
        System.out.println("Decrypted Text: " + decryptedText);

        sc.close();
    }
}
