import java.nio.charset.Charset;

public class HillCipher {
    private static final int ASCII_START = 9; // Минимальный номер символа
    private static final int ASCII_END = 255; // Максимальный номер символа
    private static final int MOD = 227; // Модуль для алфавита
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Кодировка
    private int[][] keyMatrix;
    private int[][] inverseKeyMatrix;
    private final int matrixSize = 3;

    // Конструктор
    public HillCipher(String key) {
        this.keyMatrix = createKeyMatrix(key);
        this.inverseKeyMatrix = computeInverseKeyMatrix(keyMatrix);
    }

    // Подготовка матрицы ключа
    private int[][] createKeyMatrix(String key) {
        key = prepareKey(key);
        int[][] matrix = new int[matrixSize][matrixSize];
        byte[] keyBytes = key.getBytes(ENCODING);
        int index = 0;

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                matrix[i][j] = byteToIndex(keyBytes[index++]);
            }
        }

        System.out.println("Матрица ключа:");
        printMatrix(matrix);  // Выводим матрицу ключа
        return matrix;
    }

    // Подготовка ключа: обрезка или дополнение пробелами
    private String prepareKey(String key) {
        if (key.length() > 9) {
            return key.substring(0, 9);
        } else if (key.length() < 9) {
            return String.format("%-9s", key); // Дополнение пробелами
        }
        return key;
    }

    // Шифрование
    public String encrypt(String message) {
        message = prepareMessage(message);
        byte[] messageBytes = message.getBytes(ENCODING);
        byte[] cipherBytes = new byte[messageBytes.length];

        for (int i = 0; i < messageBytes.length; i += matrixSize) {
            int[] messageVector = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                messageVector[j] = byteToIndex(messageBytes[i + j]);
            }

            System.out.println("\nВектор сообщения:");
            printVector(messageVector);  // Выводим вектор сообщения

            int[] resultVector = multiplyMatrixAndVector(keyMatrix, messageVector);

            System.out.println("Результирующий вектор:");
            printVector(resultVector);  // Выводим результат умножения

            for (int j = 0; j < matrixSize; j++) {
                cipherBytes[i + j] = indexToByte(resultVector[j]);
            }
        }
        return new String(cipherBytes, ENCODING);
    }

    // Расшифровка
    public String decrypt(String cipherText) {
        cipherText=prepareMessage(cipherText);
        byte[] cipherBytes = cipherText.getBytes(ENCODING);
        System.out.println("BYTES IN DECRYPTION\n");
        for(int i=0;i<cipherBytes.length;i++){
            System.out.print(byteToIndex(cipherBytes[i])+" ");
        }
        byte[] plainBytes = new byte[cipherBytes.length];

        for (int i = 0; i < cipherBytes.length; i += matrixSize) {
            int[] cipherVector = new int[matrixSize];
            for (int j = 0; j < matrixSize; j++) {
                cipherVector[j] = byteToIndex(cipherBytes[i + j]);
            }

            int[] resultVector = multiplyMatrixAndVector(inverseKeyMatrix, cipherVector);
            System.out.println("Result vector: ");
            printVector(resultVector);
            for (int j = 0; j < matrixSize; j++) {
                plainBytes[i + j] = indexToByte(resultVector[j]);
            }
        }
//        return new String(plainBytes, ENCODING).trim();
        System.out.println("plain text: ");
        System.out.println(new String(plainBytes,ENCODING));
        return new String(plainBytes, ENCODING);
    }

    // Умножение матрицы на вектор
    private int[] multiplyMatrixAndVector(int[][] matrix, int[] vector) {
        int[] result = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            result[i] = 0;
            for (int j = 0; j < matrixSize; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
            result[i] =(result[i]-1)%MOD+1;
            if (result[i] < 0) {
                result[i] += MOD;
            }
        }
        return result;
    }

    // Вычисление обратной матрицы
    private int[][] computeInverseKeyMatrix(int[][] matrix) {
        int deter = determinant(matrix) % MOD;
        if(deter<0) deter+=MOD;
        if (deter == 0) {
            throw new IllegalArgumentException("Матрица ключа не является обратимой.");
        }
        int modInverse = modInverse(deter, MOD);

        int[][] adjugate = adjugateMatrix(matrix);
        int[][] inverse = new int[matrixSize][matrixSize];

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                inverse[i][j] = (adjugate[i][j] * modInverse-1) % MOD+1;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += MOD;

                }
            }
        }
        System.out.println("reverse matrix: ");
        printMatrix(inverse);
        return inverse;
    }

    // Вычисление детерминанта
    private int determinant(int[][] matrix) {
        int det = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1]) -
                matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]) +
                matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);
        System.out.println("Determinant= "+det%MOD);
        return det;
    }

    // Вычисление адъюнкт-матрицы
    private int[][] adjugateMatrix(int[][] matrix) {
        int[][] adj = new int[matrixSize][matrixSize];
        adj[0][0] = matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1];
        adj[0][1] = -(matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0]);
        adj[0][2] = matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0];
        adj[1][0] = -(matrix[0][1] * matrix[2][2] - matrix[0][2] * matrix[2][1]);
        adj[1][1] = matrix[0][0] * matrix[2][2] - matrix[0][2] * matrix[2][0];
        adj[1][2] = -(matrix[0][0] * matrix[2][1] - matrix[0][1] * matrix[2][0]);
        adj[2][0] = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
        adj[2][1] = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
        adj[2][2] = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        adj=transponseMatrix(adj);
        return adj;
    }
    private int[][] transponseMatrix(int[][] mm)
    {
        int [][] tr = new int[3][3];
        tr[0][0]=mm[0][0];
        tr[1][1]=mm[1][1];
        tr[2][2]=mm[2][2];
        tr[0][1]=mm[1][0];
        tr[0][2]=mm[2][0];
        tr[1][0]=mm[0][1];
        tr[1][2]=mm[2][1];
        tr[2][0]=mm[0][2];
        tr[2][1]=mm[1][2];
        return tr;

    }

    // Обратное по модулю
    private int modInverse(int a, int m) {
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    // Подготовка сообщения
    private String prepareMessage(String message) {
        byte[] bytes = message.getBytes(ENCODING);
        while (message.length() % 3 != 0) {
            message += " ";
        }

        // Replace '\n' with '\r'
//        StringBuilder modifiedMessage = new StringBuilder();
//        for (int i = 0; i < message.length(); i++) {
//            char currentChar = message.charAt(i);
//            if (currentChar == '\n') {
////                modifiedMessage.append('\n');
////                modifiedMessage.append('\r');
//            } else {
//                modifiedMessage.append(currentChar);
//            }
//        }
//        return modifiedMessage.toString();
//        System.out.println("Bytes of the message BEFORE");
//        for(int i = 0; i< bytes.length;i++){
////            if(Byte.toUnsignedInt(bytes[i])==10) bytes[i]= (byte) 13;
//
//            System.out.print(Byte.toUnsignedInt(bytes[i])+" ");
//        }
//        while (bytes.length % matrixSize != 0) {
//            message += " ";
//            bytes = message.getBytes(ENCODING);
//        }
//        System.out.println("Bytes of the message AFTER");
//        for(int i = 0; i< bytes.length;i++){
//            if(Byte.toUnsignedInt(bytes[i])==10) bytes[i]= (byte) 13;
//
//          System.out.print(Byte.toUnsignedInt(bytes[i])+" ");
//        }
        return message;
    }

    // Преобразование байта в индекс
    private int byteToIndex(byte b) {
        int value = Byte.toUnsignedInt(b);
//        System.out.print(value + "\t");
//        System.out.println();
        if (value < ASCII_START || value > ASCII_END) {
            throw new IllegalArgumentException("Неподдерживаемый символ: " + (char) value);
        }
        else if(value==9) return 1;
        else if(value==10) return 2;
        else if(value==13) return 3;
        else return value-28;// Example transformation value - ASCII_START;
    }

    // Преобразование индекса в байт
    private byte indexToByte(int index) {
        if(index==1) return (byte) (9);
        else if(index==2) return (byte) (10);
        else if(index==3) return (byte) (13);
        else return (byte) (index+28);
    }

    // Вывод матрицы
    private void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    // Вывод вектора
    private void printVector(int[] vector) {
        for (int value : vector) {
            System.out.print(value + "\t");
        }
        System.out.println();
    }


}
