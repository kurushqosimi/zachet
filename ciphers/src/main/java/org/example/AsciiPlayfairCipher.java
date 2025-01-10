import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AsciiPlayfairCipher {
    private static final int ASCII_START = 32; // Minimum range
    private static final int ASCII_END = 255; // Maximum range
    private static final int ASCII_RANGE = ASCII_END - ASCII_START + 1; // Maximum range
    private static final int MATRIX_ROWS = 14;  // Number of rows
    private static final int MATRIX_COLUMNS = 16; // Number of columns
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    private char[][] playfairMatrix;

    public AsciiPlayfairCipher(String key) {
        this.playfairMatrix = generatePlayfairMatrix(key);
    }

    private char[][] generatePlayfairMatrix(String key) {
        byte[][] matrix = new byte[MATRIX_ROWS][MATRIX_COLUMNS];
        boolean[] used = new boolean[ASCII_END + 1];
        List<Byte> uniqueBytes = new ArrayList<>();

        // Add key characters first
        for (byte c : key.getBytes(ENCODING)) {
            int d = Byte.toUnsignedInt(c);
            if (d >= ASCII_START && d <= ASCII_END && !used[d]) {
                uniqueBytes.add(c);
                used[d] = true;
            }
        }

        // Add remaining characters from Windows-1251
        for (int i = ASCII_START; i <= ASCII_END; i++) {
            if (!used[i]) {
                uniqueBytes.add((byte) i);
                used[i] = true;
            }
        }

        // Populate the matrix
        int index = 0;
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLUMNS; col++) {
                matrix[row][col] = uniqueBytes.get(index++);
            }
        }

        // Convert byte matrix to char matrix using Windows-1251 encoding
        char[][] charMatrix = new char[MATRIX_ROWS][MATRIX_COLUMNS];
        for (int row = 0; row < MATRIX_ROWS; row++) {
            for (int col = 0; col < MATRIX_COLUMNS; col++) {
                charMatrix[row][col] = new String(new byte[]{matrix[row][col]}, ENCODING).charAt(0);
            }
        }
//        char[][] charmatrix;
        showmatrix(charMatrix,14,16);
        return charMatrix;
    }

    private void showmatrix(char[][] mm,int row,int col){
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++){
                System.out.print(mm[i][j]+" ");
            }
            System.out.println();
        }
    }


    private int[] findPosition(char c) {
        for (int i = 0; i < MATRIX_ROWS; i++) {
            for (int j = 0; j < MATRIX_COLUMNS; j++) {
                if (playfairMatrix[i][j] == c) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not in matrix: " + c);
    }


    public String encrypt(String text) {
        byte[] textBytes = text.getBytes(ENCODING);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < textBytes.length; i += 2) {
            char char1 =new String(new byte[]{textBytes[i]}, ENCODING).charAt(0);
            char char2 = (i + 1 < textBytes.length) ? new String(new byte[]{textBytes[i+1]}, ENCODING).charAt(0) : 'X';

//            if (char1 == char2) {
//                char2 = 'X';  // Insert 'X' if characters are the same
//                i--;
//            }

            String encryptedPair = encryptPair(char1, char2);
            encryptedText.append(encryptedPair);
        }
        return encryptedText.toString();
    }

    private String encryptPair(char char1, char char2) {
        int[] pos1 = findPosition(char1);
        int[] pos2 = findPosition(char2);

        if(char1==char2) return "" + char1 + char2;
        else if (pos1[0] == pos2[0]) { // Same row
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

    public String decrypt(String text) {
        byte[] textBytes = text.getBytes(ENCODING);
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < textBytes.length; i += 2) {
            char char1 = new String(new byte[]{textBytes[i]}, ENCODING).charAt(0);
            char char2 = (i + 1 < textBytes.length) ? new String(new byte[]{textBytes[i+1]}, ENCODING).charAt(0) : 'X';

            String decryptedPair = decryptPair(char1, char2);
            decryptedText.append(decryptedPair);
        }
        return decryptedText.toString();
    }

    private String decryptPair(char char1, char char2) {
        int[] pos1 = findPosition(char1);
        int[] pos2 = findPosition(char2);
        if(char1==char2) return "" + char1 + char2;
        else if (pos1[0] == pos2[0]) { // Same row
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

    }


