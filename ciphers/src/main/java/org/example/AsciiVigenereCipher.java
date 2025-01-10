import java.nio.charset.Charset;

public class AsciiVigenereCipher {
    private static final int ASCII_START = 32; // Minimum range
    private static final int ASCII_END = 255; // Maximum range
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1; // Total valid range
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    public static String encrypt(String text, String key) throws Exception {
        byte[] textBytes = text.getBytes(ENCODING);
        byte[] keyBytes = key.getBytes(ENCODING);

        byte[] encryptedBytes = new byte[textBytes.length];

        for (int i = 0; i < textBytes.length; i++) {
            int textChar = Byte.toUnsignedInt(textBytes[i]);
            int keyChar = Byte.toUnsignedInt(keyBytes[i % keyBytes.length]);

            if (textChar >= ASCII_START && textChar <= ASCII_END) {
                // Perform shift with wrapping using key character
                textChar = ((textChar - ASCII_START + (keyChar - ASCII_START)) % ASCII_RANGE) + ASCII_START;
            }

            encryptedBytes[i] = (byte) textChar;
        }

        return new String(encryptedBytes, ENCODING);
    }

    public static String decrypt(String text, String key) throws Exception {
        byte[] textBytes = text.getBytes(ENCODING);
        byte[] keyBytes = key.getBytes(ENCODING);

        byte[] decryptedBytes = new byte[textBytes.length];

        for (int i = 0; i < textBytes.length; i++) {
            int textChar = Byte.toUnsignedInt(textBytes[i]);
            int keyChar = Byte.toUnsignedInt(keyBytes[i % keyBytes.length]);

            if (textChar >= ASCII_START && textChar <= ASCII_END) {
                // Reverse shift with wrapping using key character
                textChar = ((textChar - ASCII_START - (keyChar - ASCII_START) + ASCII_RANGE) % ASCII_RANGE) + ASCII_START;
            }

            decryptedBytes[i] = (byte) textChar;
        }

        return new String(decryptedBytes, ENCODING);
    }

//    public static void main(String[] args) {
//        try {
//            String text = "Hello, World! Привет, мир!";
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
