import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UnicodePlayFairCipher {
    private static final int UNICODE_START = 32; // Minimum Unicode value
    private static final int UNICODE_END = 65535; // Maximum Unicode value
//    private static final int MATRIX_ROWS;  // Number of rows
//    private static final int MATRIX_COLUMNS; // Number of columns
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    private char[][] playfairMatrix;

    public UnicodePlayFairCipher(String key, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        this.playfairMatrix = generatePlayfairMatrix(key,MATRIX_ROWS,MATRIX_COLUMNS);
    }
//    private static final int MATRIX_ROWS;  // Number of rows
//    private static final int MATRIX_COLUMNS;
    private char[][] generatePlayfairMatrix(String key, int MATRIX_ROWS,int MATRIX_COLUMNS) {
        List<Character> uniqueChars = new ArrayList<>();
        boolean[] used = new boolean[UNICODE_END + 1];

        // Add key characters first
        for (char c : key.toCharArray()) {
            if (c >= UNICODE_START && c <= UNICODE_END && !used[c]) {
                uniqueChars.add(c);
                used[c] = true;
            }
        }

        // Add remaining characters from Unicode range
        for (int i = UNICODE_START; i <= UNICODE_END; i++) {
            if (!used[i]) {
                uniqueChars.add((char) i);
                used[i] = true;
            }
        }

        // Populate the matrix
        char[][] matrix = new char[MATRIX_ROWS][MATRIX_COLUMNS];
        int index = 0;
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLUMNS; col++) {
                matrix[row][col] = uniqueChars.get(index++);
            }
        }

        return matrix;
    }

    private int[] findPosition(char c, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        for (int i = 0; i < MATRIX_ROWS; i++) {
            for (int j = 0; j < MATRIX_COLUMNS; j++) {
                if (playfairMatrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not in matrix: " + c);
    }

    public String encrypt(String text, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        char[] textChars = text.toCharArray();
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < textChars.length; i += 2) {
            char char1 = textChars[i];
            char char2 = (i + 1 < textChars.length) ? textChars[i + 1] : 'X';

            if (char1 == char2) {
                char2 = 'X';  // Insert 'X' if characters are the same
                i--;
            }

            String encryptedPair = encryptPair(char1, char2,MATRIX_ROWS,MATRIX_COLUMNS);
            encryptedText.append(encryptedPair);
        }
        return encryptedText.toString();
    }

    private String encryptPair(char char1, char char2, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        int[] pos1 = findPosition(char1, MATRIX_ROWS, MATRIX_COLUMNS);
        int[] pos2 = findPosition(char2, MATRIX_ROWS, MATRIX_COLUMNS);

        if (pos1[0] == pos2[0]) { // Same row
            char1 = playfairMatrix[pos1[0]][(pos1[1] + 1) % MATRIX_COLUMNS];
            char2 = playfairMatrix[pos2[0]][(pos2[1] + 1) % MATRIX_COLUMNS];
        } else if (pos1[1] == pos2[1]) { // Same column
            char1 = playfairMatrix[(pos1[0] + 1) % MATRIX_ROWS][pos1[1]];
            char2 = playfairMatrix[(pos2[0] + 1) % MATRIX_ROWS][pos2[1]];
        } else { // Rectangle
            char1 = playfairMatrix[pos1[0]][pos2[1]];
            char2 = playfairMatrix[pos2[0]][pos1[1]];
        }

        return "" + char1 + char2;
    }

    public String decrypt(String text, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        char[] textChars = text.toCharArray();
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < textChars.length; i += 2) {
            char char1 = textChars[i];
            char char2 = (i + 1 < textChars.length) ? textChars[i + 1] : 'X';

            String decryptedPair = decryptPair(char1, char2,MATRIX_ROWS,MATRIX_COLUMNS);
            decryptedText.append(decryptedPair);
        }
        return decryptedText.toString();
    }

    private String decryptPair(char char1, char char2, int MATRIX_ROWS, int MATRIX_COLUMNS) {
        int[] pos1 = findPosition(char1, MATRIX_ROWS, MATRIX_COLUMNS);
        int[] pos2 = findPosition(char2, MATRIX_ROWS, MATRIX_COLUMNS);

        if (pos1[0] == pos2[0]) { // Same row
            char1 = playfairMatrix[pos1[0]][(pos1[1] - 1 + MATRIX_COLUMNS) % MATRIX_COLUMNS];
            char2 = playfairMatrix[pos2[0]][(pos2[1] - 1 + MATRIX_COLUMNS) % MATRIX_COLUMNS];
        } else if (pos1[1] == pos2[1]) { // Same column
            char1 = playfairMatrix[(pos1[0] - 1 + MATRIX_ROWS) % MATRIX_ROWS][pos1[1]];
            char2 = playfairMatrix[(pos2[0] - 1 + MATRIX_ROWS) % MATRIX_ROWS][pos2[1]];
        } else { // Rectangle
            char1 = playfairMatrix[pos1[0]][pos2[1]];
            char2 = playfairMatrix[pos2[0]][pos1[1]];
        }

        return "" + char1 + char2;
    }

//    public static void main(String[] args) {
//        String key="mykey";
//        String input="Hello world!";
//        UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift,row,col);
////      UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift);
//        String decrypted = cipher.decrypt(input,row,col);
//        outputArea.setText(decrypted);
//    }
//    public static void main(String[] args) {
//        try {
//            String key = "good";
//            String text = "hello world!";
//
//            UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(key,2,32752);
//
//            System.out.println("Playfair Matrix:");
//            for (char[] row : cipher.playfairMatrix) {
//                for (char c : row) {
//                    System.out.print(c + " ");
//                }
//                System.out.println();
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
