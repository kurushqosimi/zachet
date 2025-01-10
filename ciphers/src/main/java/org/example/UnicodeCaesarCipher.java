
public class UnicodeCaesarCipher {
    private static final int UNICODE_START = 32; // Minimum range
    private static final int UNICODE_END = 65535; // Maximum range
    private static final int UNICODE_RANGE = UNICODE_END - UNICODE_START + 1; // Total valid range

    public static String encrypt(String text, int shift) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (c >= UNICODE_START && c <= UNICODE_END) {
                // Perform shift with wrapping
                int shifted = ((c - UNICODE_START + shift) % UNICODE_RANGE) + UNICODE_START;
                encryptedText.append((char) shifted);
            } else {
                // Keep characters outside the range unchanged
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, UNICODE_RANGE - (shift % UNICODE_RANGE)); // Reverse shift logic
    }

//    public static void main(String[] args) {
//        try {
//            String originalText = "Hello, 🌍! Привет, мир!";
//            int shift = 5;
//
//            String encryptedText = encrypt(originalText, shift);
//            System.out.println("Encrypted Text: " + encryptedText);
//
//            String decryptedText = decrypt(encryptedText, shift);
//            System.out.println("Decrypted Text: " + decryptedText);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}