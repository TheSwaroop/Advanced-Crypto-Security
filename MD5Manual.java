import java.util.Scanner;

public class MD5Manual {

    // Initial constants (MD5 buffer initialization)
    private static int A = 0x67452301, B = 0xefcdab89, C = 0x98badcfe, D = 0x10325476;

    // MD5 Constants and Left Rotations (for 64 rounds)
    private static final int[] K = {0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
            0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
            0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
            0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
            0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
            0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
            0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
            0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391};
    private static final int[] S = {7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
            5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
            4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
            6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter text to hash: ");
        String input = sc.nextLine();
        System.out.println("MD5 Digest: " + md5(input));
    }

    public static String md5(String m) {
        byte[] padded = pad(m.getBytes());
        int[] buf = {A, B, C, D};

        for (int i = 0; i < padded.length; i += 64) {
            int[] w = new int[16];
            for (int j = 0; j < 16; j++) {
                w[j] = ((padded[i + j * 4] & 0xff)) | ((padded[i + j * 4 + 1] & 0xff) << 8) |
                        ((padded[i + j * 4 + 2] & 0xff) << 16) | ((padded[i + j * 4 + 3] & 0xff) << 24);
            }
            process(w, buf);
        }

        return String.format("%08x%08x%08x%08x", buf[0], buf[1], buf[2], buf[3]);
    }

    private static byte[] pad(byte[] m) {
        int len = m.length;
        int padLen = (len % 64 < 56) ? (56 - len % 64) : (120 - len % 64);
        byte[] p = new byte[len + padLen + 8];
        System.arraycopy(m, 0, p, 0, len);
        p[len] = (byte) 0x80;

        long bitLen = (long) len * 8;
        for (int i = 0; i < 8; i++) {
            p[p.length - 8 + i] = (byte) (bitLen >>> (8 * i));
        }
        return p;
    }

    private static void process(int[] w, int[] buf) {
        int a = buf[0], b = buf[1], c = buf[2], d = buf[3];

        for (int i = 0; i < 64; i++) {
            int f, g;
            if (i < 16) { f = (b & c) | (~b & d); g = i; }
            else if (i < 32) { f = (d & b) | (~d & c); g = (5 * i + 1) % 16; }
            else if (i < 48) { f = b ^ c ^ d; g = (3 * i + 5) % 16; }
            else { f = c ^ (b | ~d); g = (7 * i) % 16; }

            int tmp = d;
            d = c;
            c = b;
            b = b + rotate(a + f + K[i] + w[g], S[i]);
            a = tmp;
        }
        buf[0] += a; buf[1] += b; buf[2] += c; buf[3] += d;
    }

    private static int rotate(int val, int s) {
        return (val << s) | (val >>> (32 - s));
    }
}
