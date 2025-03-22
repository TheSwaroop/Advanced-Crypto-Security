import java.util.Scanner;

public class HillCipher {
    static int[][] keyMatrix = new int[2][2];  // The encryption key (2x2 matrix)
    static int[][] inverseKeyMatrix = new int[2][2];  // The decryption key (2x2 inverse)

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask the user to enter the key matrix (2x2)
        System.out.println("Enter a 2x2 key matrix (values between 0 and 25):");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[i][j] = sc.nextInt();
            }
        }

        // Ask for the message to encrypt
        System.out.print("Enter the message to encrypt (only uppercase letters, even length): ");
        String plaintext = sc.next().toUpperCase();
        if (plaintext.length() % 2 != 0) {
            plaintext += "X";  // If the length is odd, add 'X' to make it even
        }

        // Encrypt and display the result
        String encryptedText = encrypt(plaintext);
        System.out.println("Encrypted Text: " + encryptedText);

        // Calculate the inverse key matrix to allow decryption
        calculateInverseKey();

        // Decrypt the message and show the result
        String decryptedText = decrypt(encryptedText);
        System.out.println("Decrypted Text: " + decryptedText);

        sc.close();
    }

    // Function to encrypt the plaintext
    public static String encrypt(String plaintext) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            int x1 = plaintext.charAt(i) - 'A';         // Convert first letter to number (A = 0, B = 1, etc.)
            int x2 = plaintext.charAt(i + 1) - 'A';     // Convert second letter

            // Apply the key matrix to encrypt
            int y1 = (keyMatrix[0][0] * x1 + keyMatrix[0][1] * x2) % 26;
            int y2 = (keyMatrix[1][0] * x1 + keyMatrix[1][1] * x2) % 26;

            // Convert numbers back to letters and add to the encrypted text
            encryptedText.append((char) (y1 + 'A')).append((char) (y2 + 'A'));
        }
        return encryptedText.toString();
    }

    // Function to decrypt the ciphertext
    public static String decrypt(String ciphertext) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            int y1 = ciphertext.charAt(i) - 'A';       // Convert first encrypted letter to number
            int y2 = ciphertext.charAt(i + 1) - 'A';   // Convert second encrypted letter

            // Apply the inverse key matrix to decrypt
            int x1 = (inverseKeyMatrix[0][0] * y1 + inverseKeyMatrix[0][1] * y2) % 26;
            int x2 = (inverseKeyMatrix[1][0] * y1 + inverseKeyMatrix[1][1] * y2) % 26;

            // Handle negative results (to keep numbers in range)
            x1 = (x1 + 26) % 26;
            x2 = (x2 + 26) % 26;

            // Convert numbers back to letters and add to the decrypted text
            decryptedText.append((char) (x1 + 'A')).append((char) (x2 + 'A'));
        }
        return decryptedText.toString();
    }

    // Calculate the inverse of the key matrix (for decryption)
    public static void calculateInverseKey() {
        int determinant = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        determinant = determinant % 26;
        if (determinant < 0) {
            determinant += 26;  // Handle negative determinant
        }

        int inverseDeterminant = modularInverse(determinant, 26);
        if (inverseDeterminant == -1) {
            System.out.println("The key matrix is not invertible. Please use a different key.");
            System.exit(1);
        }

        // Calculate the inverse matrix values modulo 26
        inverseKeyMatrix[0][0] = (keyMatrix[1][1] * inverseDeterminant) % 26;
        inverseKeyMatrix[0][1] = (-keyMatrix[0][1] * inverseDeterminant + 26) % 26;
        inverseKeyMatrix[1][0] = (-keyMatrix[1][0] * inverseDeterminant + 26) % 26;
        inverseKeyMatrix[1][1] = (keyMatrix[0][0] * inverseDeterminant) % 26;
    }

    // Function to calculate modular inverse using the Extended Euclidean Algorithm
    public static int modularInverse(int a, int m) {
        int m0 = m, y = 0, x = 1;

        if (m == 1)
            return 0;

        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }

        if (x < 0)
            x += m0;

        return x;
    }
}
