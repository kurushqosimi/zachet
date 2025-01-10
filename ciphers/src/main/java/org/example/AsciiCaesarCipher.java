import java.nio.charset.Charset;

public class AsciiCaesarCipher {
    private static final int ASCII_START = 32; // Minimum range
    private static final int ASCII_END = 255; // Maximum range
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1; // Total valid range
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    public static String encrypt(String text, int shift) throws Exception {
        byte[] bytes = text.getBytes(ENCODING); // Encode text to Windows-1251
        for (int i = 0; i < bytes.length; i++) {
            int b = Byte.toUnsignedInt(bytes[i]);
            if (b >= ASCII_START && b <= ASCII_END) {
                // Perform shift with wrapping
                b = ((b - ASCII_START + shift) % ASCII_RANGE) + ASCII_START;
            }
            bytes[i] = (byte) b;
        }
        return new String(bytes, ENCODING); // Decode back to String
    }

    public static String decrypt(String text, int shift) throws Exception {
        return encrypt(text, ASCII_RANGE - (shift % ASCII_RANGE)); // Reverse shift logic
    }


}


