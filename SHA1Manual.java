import java.util.Scanner;

public class SHA1Manual {

    private static final int H0 = 0x67452301, H1 = 0xEFCDAB89, H2 = 0x98BADCFE, H3 = 0x10325476, H4 = 0xC3D2E1F0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text: ");
        String in = sc.nextLine();  
        System.out.println("SHA-1 Digest: " + sha1(in));
        sc.close();
    }

    public static String sha1(String msg) {
        byte[] p = pad(msg.getBytes());
        int[] h = {H0, H1, H2, H3, H4};

        for (int i = 0; i < p.length; i += 64) {
            process(p, i, h);
        }
        return String.format("%08x%08x%08x%08x%08x", h[0], h[1], h[2], h[3], h[4]);
    }

    private static byte[] pad(byte[] m) {
        int l = m.length, pl = ((l + 8) / 64 + 1) * 64;
        byte[] p = new byte[pl];
        System.arraycopy(m, 0, p, 0, l);
        p[l] = (byte) 0x80;

        long bl = (long) l * 8;
        for (int i = 0; i < 8; i++) p[pl - 1 - i] = (byte) (bl >>> (8 * i));

        return p;
    }

    private static void process(byte[] c, int o, int[] h) {
        int[] w = new int[80];

        for (int i = 0; i < 16; i++) {
            w[i] = ((c[o + i * 4] & 0xff) << 24) | ((c[o + i * 4 + 1] & 0xff) << 16) | ((c[o + i * 4 + 2] & 0xff) << 8) | (c[o + i * 4 + 3] & 0xff);
        }
        for (int i = 16; i < 80; i++) w[i] = leftR(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);

        int a = h[0], b = h[1], c0 = h[2], d = h[3], e = h[4];

        for (int i = 0; i < 80; i++) {
            int f, k;
            if (i < 20) { f = (b & c0) | (~b & d); k = 0x5A827999; } 
            else if (i < 40) { f = b ^ c0 ^ d; k = 0x6ED9EBA1; } 
            else if (i < 60) { f = (b & c0) | (b & d) | (c0 & d); k = 0x8F1BBCDC; } 
            else { f = b ^ c0 ^ d; k = 0xCA62C1D6; }

            int t = leftR(a, 5) + f + e + k + w[i];
            e = d; d = c0; c0 = leftR(b, 30); b = a; a = t;
        }

        h[0] += a; h[1] += b; h[2] += c0; h[3] += d; h[4] += e;
    }

    private static int leftR(int v, int b) {
        return (v << b) | (v >>> (32 - b));
    }
}
