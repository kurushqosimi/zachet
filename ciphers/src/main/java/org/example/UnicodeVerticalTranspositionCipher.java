import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnicodeVerticalTranspositionCipher {
    private int columns;
    private List<Integer> columnOrder;

    public UnicodeVerticalTranspositionCipher(String key) {
        this.columns = key.length();
        this.columnOrder = generateColumnOrder(key);
    }

    private List<Integer> generateColumnOrder(String key) {
        List<Integer> order = new ArrayList<>();
        Integer[] indices = new Integer[key.length()];

        // Initialize indices
        for (int i = 0; i < key.length(); i++) {
            indices[i] = i;
        }

        // Sort indices based on the Unicode value of the characters in the key
        Arrays.sort(indices, (a, b) -> Character.compare(key.charAt(a), key.charAt(b)));

        // Populate the order list
        order.addAll(Arrays.asList(indices));
        return order;
    }

    public String encrypt(String text) {
        validateInput(text);

        int rows = (int) Math.ceil((double) text.length() / columns);
        char[][] matrix = new char[rows][columns];

        // Fill the matrix row by row
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (index < text.length()) {
                    matrix[r][c] = text.charAt(index++);
                } else {
                    matrix[r][c] = '\0'; // Null character padding
                }
            }
        }

        // Read the matrix column by column based on the column order
        StringBuilder encryptedText = new StringBuilder();
        for (int col : columnOrder) {
            for (int r = 0; r < rows; r++) {
                if (matrix[r][col] != '\0') { // Ignore null character padding
                    encryptedText.append(matrix[r][col]);
                }
            }
        }

        return encryptedText.toString();
    }

    public String decrypt(String encryptedText) {
        validateInput(encryptedText);

        int rows = (int) Math.ceil((double) encryptedText.length() / columns);
        char[][] matrix = new char[rows][columns];

        // Fill the matrix column by column based on the column order
        int index = 0;
//        for (int col : columnOrder) {
//            for (int r = 0; r < rows; r++) {
//                if (index < encryptedText.length()) {
//                    matrix[r][col] = encryptedText.charAt(index++);
//                } else {
//                    matrix[r][col] = '\0'; // Null character padding
//                }
//            }
//        }

        for (int orderIndex = 0; orderIndex < columnOrder.size(); orderIndex++) {
            int col = columnOrder.get(orderIndex);
            for (int r = 0; r < rows; r++) {
                if (index < encryptedText.length()) {
                    matrix[r][col] = encryptedText.charAt(index++);
                } else {
                    matrix[r][col] = '\0'; // Padding with null byte
                }
            }
        }

        // Read the matrix row by row
        StringBuilder decryptedText = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (matrix[r][c] != '\0') { // Ignore null character padding
                    decryptedText.append(matrix[r][c]);
                }
            }
        }

        return decryptedText.toString();
    }

    private void validateInput(String text) {
        for (int i = 0; i < text.length(); i++) {
            int codePoint = text.codePointAt(i);
            if (codePoint < 32 || codePoint > 65535) {
                throw new IllegalArgumentException("Input text contains invalid characters. Must be in range 32-65535.");
            }
        }
    }

    public static void main(String[] args) {
        String input = "Меня зовут Улугбек!";
        String key = "яблоко";

        UnicodeVerticalTranspositionCipher cipher = new UnicodeVerticalTranspositionCipher(key);

        String encrypted = cipher.encrypt(input);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = cipher.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
