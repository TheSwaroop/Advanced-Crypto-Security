import java.math.BigInteger;
import java.util.Base64;
import java.util.Scanner;

public class RSA {
    private BigInteger p, q, n, phi, e, d;

    public RSA() {
generateKeys();
    }

    private void generateKeys() {                                   
        p = BigInteger.valueOf(61); 
        q = BigInteger.valueOf(53); 
        n = p.multiply(q);                   
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(17); 
        d = e.modInverse(phi);
System.out.println("Public Key (e, n): (" + e + ", " + n + ")");
System.out.println("Private Key (d, n): (" + d + ", " + n + ")");
    }

public BigInteger encrypt(BigInteger msg) {
    return msg.modPow(e, n); // C = msg^e mod n
}

public BigInteger decrypt(BigInteger C) {
    return C.modPow(d, n); // msg = C^d mod n
}


    private static String bigIntegerToBase64(BigInteger number) {
byte[] bytes = number.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static void main(String[] args) {
        RSA rsa = new RSA();
        Scanner sc = new Scanner(System.in);
System.out.print("Enter a message to encrypt (keep it short, max value < " + rsa.n + "): ");
        String text = sc.nextLine();
System.out.println("Original Message: " + text);
BigInteger msg = BigInteger.ZERO;
        for (char c :text.toCharArray()) {
            msg = msg.add(BigInteger.valueOf((int) c));
        }
System.out.println("Message as Number: " + msg);
        if (msg.compareTo(rsa.n) >= 0) {
System.out.println("Error: Message number is too large for this modulus (" + rsa.n + "). Try a shorter message.");
sc.close();
            return;
        }
BigInteger C = rsa.encrypt(msg);
        String base64C = bigIntegerToBase64(C);
System.out.println("Encrypted (Ciphertext in Base64): " + base64C);
BigInteger decrypted = rsa.decrypt(C);
System.out.println("Decrypted (Number): " + decrypted);
        if (decrypted.equals(msg)) {
System.out.println("Decryption successful! Matches original message number.");
        } else {
System.out.println("Decryption failed.");
        }
sc.close();
    }
}
