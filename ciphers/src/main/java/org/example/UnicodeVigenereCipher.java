public class UnicodeVigenereCipher {
    private static final int UNICODE_START = 32; // Minimum range
    private static final int UNICODE_END = 65535; // Maximum range
    private static final int UNICODE_RANGE = UNICODE_END - UNICODE_START + 1; // Total valid range

    public static String encrypt(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = key.charAt(i % key.length());

            if (textChar >= UNICODE_START && textChar <= UNICODE_END) {
                // Perform shift with wrapping using key character
                int shifted = ((textChar - UNICODE_START + (keyChar - UNICODE_START)) % UNICODE_RANGE) + UNICODE_START;
                encryptedText.append((char) shifted);
            } else {
                // Keep characters outside the range unchanged
                encryptedText.append(textChar);
            }
        }

        return encryptedText.toString();
    }

    public static String decrypt(String text, String key) {
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char textChar = text.charAt(i);
            char keyChar = key.charAt(i % key.length());

            if (textChar >= UNICODE_START && textChar <= UNICODE_END) {
                // Reverse shift with wrapping using key character
                int shifted = ((textChar - UNICODE_START - (keyChar - UNICODE_START) + UNICODE_RANGE) % UNICODE_RANGE) + UNICODE_START;
                decryptedText.append((char) shifted);
            } else {
                // Keep characters outside the range unchanged
                decryptedText.append(textChar);
            }
        }

        return decryptedText.toString();
    }
//
//    public static void main(String[] args) {
//        try {
//            String text = "Hello, 🌍! Привет, мир!";
//            String key = "SecretKey";
//
//            String encryptedText = encrypt(text, key);
//            System.out.println("Encrypted Text: " + encryptedText);
//
//            String decryptedText = decrypt(encryptedText, key);
//            System.out.println("Decrypted Text: " + decryptedText);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
