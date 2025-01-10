//import org.example.AlgebraOfMatrix;

import org.example.AsciiGronsfeldCipher;
import org.example.UnicodeGronsfeldCipher;
//import org.example.RSA;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CipherGUI {
    private static final Charset ENCODING = Charset.forName("windows-1251");
    private static byte indexToByte(int index) {
        if(index==1) return (byte) (9);
        else if(index==2) return (byte) (10);
        else if(index==3) return (byte) (13);
        else return (byte) (index+28);
    }
    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Ciphers");
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel inputLabel = new JLabel("Input Text:");
        JTextArea inputArea = new JTextArea("Hello\rWorld!");
//        inputArea.setEditable(false); // Make it read-only
//        inputArea.setLineWrap(true); // Enable line wrapping
//        inputArea.setWrapStyleWord(true);
        JLabel shiftOrKeyLabel = new JLabel("Shift / Key");
        JTextField shiftOrKeyField = new JTextField("information");
        JComboBox<String> cipherType = new JComboBox<>(new String[]{"Caesar", "Vigenere", "PlayFair","Vertical Transposition","Hill","Algebra of matrix","Gronsfeld","RSA"});
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        JButton testB=new JButton("Test");
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setEditable(false); // Make it read-only
        outputArea.setLineWrap(true); // Enable line wrapping
        outputArea.setWrapStyleWord(true);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        JComboBox<String> codingType=new JComboBox<>(new String[]{"ASCII","UNICODE"});
        JLabel rowcount=new JLabel("rows");
        JLabel colcount=new JLabel("columns");
        JTextField rows=new JTextField("1");
        JTextField cols=new JTextField("1");
        JTextField eee=new JTextField("1");
        JTextArea description = new JTextArea();
        description.setText("Тип шифра подстановочного типа, где каждая буква в открытом тексте заменяется на другую букву, смещенную на некоторое фиксированное количество позиций в алфавите");
        description.setFont(new Font("Arial", Font.PLAIN, 14)); // Set the font
//        description.setEditable(false); // Make it read-only
        description.setLineWrap(true); // Enable line wrapping
        description.setWrapStyleWord(true); // Wrap at word boundaries

// Add JScrollPane to make the description scrollable if needed
        JScrollPane descriptionScroll = new JScrollPane(description);
        descriptionScroll.setBounds(435, 200, 310, 200);

// Add description to the panel
//        panel.add(descriptionScroll);
        // Layout setup
        JPanel panel = new JPanel();
        panel.setLayout(null);

//        inputLabel.setBounds(50, 30, 100, 25);fk;gkl;sdfkgl;kdfl;gk;ldsfkg;lsdf
        inputScroll.setBounds(30, 30, 400, 500);
        shiftOrKeyLabel.setBounds(555, 10, 100, 25);
        shiftOrKeyField.setBounds(435, 30, 310, 25);
        cipherType.setBounds(435, 70, 310, 30);
        codingType.setBounds(435, 110, 310, 30);
        outputScroll.setBounds(750, 30, 400, 500);
        rowcount.setBounds(490,145,60,25);
        rowcount.setFont(new Font("Arial",Font.BOLD, 13));
        rows.setBounds(435, 155, 140, 25);
        eee.setBounds(435, 165, 140, 25);
        colcount.setBounds(650,145,60,25);
        colcount.setFont(new Font("Arial",Font.BOLD, 13));
        cols.setBounds(605,165,140,25);
        encryptButton.setBounds(435, 415, 310, 50);
        decryptButton.setBounds(435, 475, 310, 50);
        testB.setBounds(435,530,310,50);
//        description.setText("Тип шифра подстановочного типа, где каждая буква в открытом тексте заменяется на другую букву, смещенную на некоторое фиксированное количество позиций в алфавите");
        description.setFont(new Font("Arial", Font.PLAIN, 14)); // Set the font
        encryptButton.setFont(new Font("Arial", Font.BOLD, 20));
        decryptButton.setFont(new Font("Arial", Font.BOLD, 20));

//        encryptButton.setBackground(Color.RED); // Background color
//        encryptButton.setForeground(Color.WHITE);
//        decryptButton.setBackground(Color.GREEN); // Background color
//        decryptButton.setForeground(Color.WHITE);

        // Calculate text width
//        FontMetrics metrics = description.getFontMetrics(description.getFont());
//        int textWidth = metrics.stringWidth(description.getText());
//        int textHeight = metrics.getHeight();

        // Set bounds dynamically
        description.setBounds(435, 200, 310, 200);

//        panel.add(inputLabel);
        panel.add(inputScroll);
        panel.add(shiftOrKeyLabel);
        panel.add(shiftOrKeyField);
        panel.add(cipherType);
        panel.add(codingType);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(rowcount);
        panel.add(rows);
        panel.add(colcount);
        panel.add(cols);
        panel.add(outputScroll);
        panel.add(descriptionScroll);
        panel.add(eee);
        panel.add(testB);


        frame.add(panel);

        testB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = "E:\\Ulugbeks work\\7th semester\\Безопасность компьютера\\234\\Задание 2\\Ахлиллоев.txt";
                String keyOrShift=shiftOrKeyField.getText();
                //        String text="Hello\rWorld";
//        try {
//            // Записываем строку в файл (создается или перезаписывается)
//            Files.write(Path.of(file), text.getBytes());
//
//            System.out.println("Содержимое успешно записано в файл.");
//        } catch (IOException e) {
//            System.err.println("Ошибка при записи в файл: " + e.getMessage());
//        }
                try {
                    // Читаем все содержимое файла как строку
                    String content = Files.readString(Path.of(file));
//                    char[] contentArray = content.toCharArray();
//
//                    for (int i = 0; i < contentArray.length; i++) {
//                        byte[] byteArray = String.valueOf(contentArray[i]).getBytes(ENCODING);
//                        if (Byte.toUnsignedInt(byteArray[0]) == 63) {  // Код символа 63 (обычно это знак "?", ASCII)
//                            contentArray[i] = (char) 152;
////                            System.out.println("\nHEERE \n");// Заменяем на символ с кодом 13 (carriage return)
//                        }
//                    }
                    // Преобразуем обратно в строку
//                    String updatedContent = new String(contentArray);


                    // Перебираем каждый символ
                    for (char c : content.toCharArray()) {
                        inputArea.append(String.valueOf(c));
                        byte[] bytes = String.valueOf(c).getBytes(ENCODING); // Преобразуем символ в байты
                        System.out.printf("Символ: '%c', Байты: ", c);
                        for (byte b : bytes) {
                            System.out.printf("%d ", Byte.toUnsignedInt(b));
                        }
                        System.out.println();
                    }
                    HillCipher hillCipher = new HillCipher(keyOrShift);

//                            String message = input;
                    String encrypted = hillCipher.decrypt(content);
                    //String decrypted = hillCipher.decrypt(encrypted);

//                            System.out.println("Исходное сообщение: " + message);
                    outputArea.setText(encrypted);
                } catch (IOException eg) {
                    System.err.println("Ошибка при чтении файла: " + eg.getMessage());
                }



//                String kk="Hello\rWorld";
//                outputArea.setText(kk);
//                String keyOrShift = shiftOrKeyField.getText();
//                outputArea.setText("");
//                String text = inputArea.getText();
//                byte[] bytes=text.getBytes(ENCODING);
//                for(byte bb:bytes)
//                {
//                    outputArea.append(Byte.toUnsignedInt(bb)+" ");
//                }
//                Path filePath = Paths.get("E:\\Ulugbeks work\\7th semester\\Безопасность компьютера\\234\\Задание 2\\Ахлиллоев У.txt"); // Измените путь, если файл находится в другой папке
//                try(Scanner scan = new Scanner(new File("E:\\Ulugbeks work\\7th semester\\Безопасность компьютера\\234\\Задание 2\\Ахлиллоев У.txt"))){
//                    while(scan.hasNextLine()){
//                        text+= scan.nextLine();
//                        text+="\r\n";
//                    }
//                    inputArea.setText(text);

//                HillCipher hillCipher = new HillCipher(keyOrShift);
//                String encrypted = hillCipher.encrypt(text);
//                outputArea.setText(encrypted);
//                }catch(IOException ee){System.out.println("Error opening file: "+ee.getMessage());}
//                System.out.println("Text:"+text);
                //
//                try {
//                    // Читаем весь текст из файла
//                    String text = Files.readString(filePath);
//                    System.out.println("Число символов в файле: " + text.length());
//                    System.out.println("Содержимое файла:");
//
//                    System.out.println(text);
//                } catch (IOException ee) {
//                    System.out.println("Ошибка при чтении файла: " + ee.getMessage());
//                }
//                Path filePath = Paths.get("src/1.txt"); // Укажите путь к вашему файлу

//                try {
//                    // Чтение файла
//                    byte[] bytes = Files.readAllBytes(filePath,ENCODING);
//
////                    // Проверка и удаление BOM
////                    String content;
////                    if (bytes.length >= 3 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
////                        // Удаляем первые 3 байта BOM
////                        content = new String(bytes, 3, bytes.length - 3, "UTF-8");
////                    } else {
////                        // Если BOM отсутствует, читаем весь файл как есть
////                        content = new String(bytes, "UTF-8");
////                    }
//
//                    for(byte bb:bytes){
//                        System.out.print(Byte.toUnsignedInt(bb)+" ");
//                    }
//
////                    System.out.println("Содержимое файла:");
////                    System.out.println(content);
////                    inputArea.setText(content);
//
////                HillCipher hillCipher = new HillCipher(keyOrShift);
////                String encrypted = hillCipher.encrypt(content);
////                outputArea.setText(encrypted);
//                } catch (IOException ee) {
//                    System.out.println("Ошибка при чтении файла: " + ee.getMessage());
//                }

//                inputArea.setText(text);
//
//                HillCipher hillCipher = new HillCipher(keyOrShift);
//                String encrypted = hillCipher.encrypt(text);
//                outputArea.setText(encrypted);






//                    String input = inputArea.getText();
//                    byte[] bytes = input.getBytes(ENCODING);
////                    byte[] bb=new byte[1];
////                    bb[0]=indexToByte(2);
//                    StringBuilder ss;
//                    // Process each byte
//                    int i=0;
////                for (i = 0; i < input.length(); i++) {
////                    char currentChar = input.charAt(i);
////                    if ((int) currentChar == 13) {
////                        outputArea.append("Символ с кодом 13 найден на позиции: " + i);
////                    }
////                }
//                    outputArea.setText((new String(bb,ENCODING)));
//                    for (byte bb:bytes) {
//                        int tt = Byte.toUnsignedInt(bb);
//                        outputArea.append(/*input.charAt(i)+"->"+*/tt+"\n");
//                        i++;
//                }
            }
        });
// Add ActionListener to the cipherType combo box
        cipherType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCipher = (String) cipherType.getSelectedItem();
                switch (selectedCipher) {
                    case "Caesar":
                        description.setText("Шифр Цезаря: Каждая буква в тексте замещается буквой, сдвинутой на фиксированное количество позиций в алфавите.");
                        break;
                    case "Vigenere":
                        description.setText("Шифр Виженера: Использует ключевое слово для сдвига букв в тексте, обеспечивая полиалфавитное шифрование.");
                        break;
                    case "PlayFair":
                        description.setText("Шифр Плейфера: Подставочный шифр, который шифрует пары букв с использованием матрицы 5x5.");
                        break;
                    case "Vertical Transposition":
                        description.setText("Вертикальная транспозиция: Переставляет буквы текста в столбцах в соответствии с заданным ключом.");
                        break;
                    case "Hill":
                        description.setText("Шифр Хилла: Шифрует блоки текста с помощью матричного умножения с использованием ключевой матрицы.");
                        break;
                    case "Algebra of matrix":
                        description.setText("Алгебра матриц: Использует математические операции с матрицами для шифрования текста.");
                        break;
                    case "Gronsfeld":
                        description.setText("Шифр Гронсфельда: Вариация шифра Виженера, где ключ состоит из цифр, задающих сдвиг для каждой буквы текста.");
                        break;
                    default:
                        description.setText("Выберите тип шифра, чтобы увидеть его описание.");
                        break;
                }
            }
        });


        // Button actions
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = inputArea.getText();
                    String keyOrShift = shiftOrKeyField.getText();
                    String selectedCipher = (String) cipherType.getSelectedItem();
                    String selectedCoding = (String) codingType.getSelectedItem();
                    int row=Integer.parseInt(rows.getText());
                    int col=Integer.parseInt(cols.getText());


                    if (selectedCoding.equals("ASCII")) {
                        if (selectedCipher.equals("Caesar")) {
//                            description.setText("Тип шифра подстановочного типа, где каждая буква в открытом тексте заменяется на другую букву, смещенную на некоторое фиксированное количество позиций в алфавите");
                            int shift = Integer.parseInt(keyOrShift);
                            String encrypted = AsciiCaesarCipher.encrypt(input, shift);
                            outputArea.setText(encrypted);
                        } else if (selectedCipher.equals("Vigenere")) {
//                            description.setText("Метод полиалфавитного шифрования буквенного текста с использованием ключевого слова.");

                            String encrypted = AsciiVigenereCipher.encrypt(input, keyOrShift);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
//                            description.setText("Предусматривает шифрование пар символов (биграмм) вместо одиночных символов, как в шифре подстановки и в более сложных системах шифрования Виженера");

                            AsciiPlayfairCipher cipher = new AsciiPlayfairCipher(keyOrShift);
                            String ciphertext = cipher.encrypt(input);
                            outputArea.setText(ciphertext);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
//                           description.setText("Выписывается шифрограмма по вертикалям, при этом столбцы выбираются в порядке, определяемом ключом");
//                            String input = "Привет, мир! Это тестовое сообщение.";
//                            String key = "ключ"; // Encryption key

                            AsciiVerticalTranspositionCipher cipher = new AsciiVerticalTranspositionCipher(keyOrShift);
                            String encrypted = cipher.encrypt(input);
                            outputArea.setText(encrypted);

//                            String decrypted = cipher.decrypt(encrypted);
//                            System.out.println("Decrypted: " + decrypted);
                        }
                        else if(selectedCipher.equals("Hill"))
                        {
//                            description.setText("Полиграммный шифр подстановки, в котором буквы открытого текста заменяются группами с помощью линейной алгебры");
//
//                            String key = "КЛЮЧ123"; // Пример ключа
                            HillCipher hillCipher = new HillCipher(keyOrShift);

//                            String message = input;
                            String encrypted = hillCipher.encrypt(input);
                            //String decrypted = hillCipher.decrypt(encrypted);

//                            System.out.println("Исходное сообщение: " + message);
                            outputArea.setText(encrypted);
//                            System.out.println("Расшифрованное сообщение: " + decrypted);
                        }
                        else if(selectedCipher.equals("Algebra of matrix"))
                        {
                            AlgebraOfMatrix aom=new AlgebraOfMatrix(keyOrShift);
                            String encrypted=aom.encrypt(input);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("Gronsfeld"))
                        {
                            String encrypt= AsciiGronsfeldCipher.encrypt(input, keyOrShift);
                            outputArea.setText(encrypt);
                        }

                    }
                    else if(selectedCoding.equals("UNICODE")){
                        if(selectedCipher.equals("Caesar"))
                        {
//                            description.setText("Тип шифра подстановочного типа, где каждая буква в открытом тексте заменяется на другую букву, смещенную на некоторое фиксированное количество позиций в алфавите");
                            int shift=Integer.parseInt(keyOrShift);
                            String encrypted= UnicodeCaesarCipher.encrypt(input,shift);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("Vigenere"))
                        {
//                            description.setText("Метод полиалфавитного шифрования буквенного текста с использованием ключевого слова.");

                            String encrypted = UnicodeVigenereCipher.encrypt(input, keyOrShift);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
//                            description.setText("Предусматривает шифрование пар символов (биграмм) вместо одиночных символов, как в шифре подстановки и в более сложных системах шифрования Виженера");

                            if(row*col==65504){
                            UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift,row,col);
                            String encrypted = cipher.encrypt(input,row,col);
                            outputArea.setText(encrypted);
                            }
                            else JOptionPane.showMessageDialog(frame, "Rows * Columns is not 65504", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
//                            description.setText("Выписывается шифрограмма по вертикалям, при этом столбцы выбираются в порядке, определяемом ключом");
//
                            UnicodeVerticalTranspositionCipher cipher = new UnicodeVerticalTranspositionCipher(keyOrShift);
                            String encrypted = cipher.encrypt(input);
                            outputArea.setText(encrypted);
                        }
                        else if(selectedCipher.equals("Gronsfeld"))
                        {
                            String encrypted= UnicodeGronsfeldCipher.encrypt(input,keyOrShift);
                            outputArea.setText(encrypted);
                        }
//                        else if (selectedCipher.equals("RSA")) {
//                            RSA rsa = new RSA();
//                            String encrypted = rsa.encrypt(input);
//                            String publicKey = rsa.getPublicKey();
//                            JOptionPane.showMessageDialog(frame, "Public Key: " + publicKey, "Key Information", JOptionPane.INFORMATION_MESSAGE);
//                            outputArea.setText(encrypted);
//                        }
                        }
                    }

                    catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Shift value must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = inputArea.getText();
                    String keyOrShift = shiftOrKeyField.getText();
                    String selectedCipher = (String) cipherType.getSelectedItem();
                    String selectedCoding = (String) codingType.getSelectedItem();
                    int row=Integer.parseInt(rows.getText());
                    int col=Integer.parseInt(cols.getText());
                    int ee=Integer.parseInt(eee.getText());
                    if (selectedCoding.equals("ASCII")) {
                        if (selectedCipher.equals("Caesar")) {
                            int shift = Integer.parseInt(keyOrShift);
                            String decrypted = AsciiCaesarCipher.decrypt(input, shift);
                            outputArea.setText(decrypted);
                        } else if (selectedCipher.equals("Vigenere")) {
                            String decrypted = AsciiVigenereCipher.decrypt(input, keyOrShift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
                            AsciiPlayfairCipher cipher = new AsciiPlayfairCipher(keyOrShift);
                            String decryptedText = cipher.decrypt(input);
                            outputArea.setText(decryptedText);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
                            AsciiVerticalTranspositionCipher cipher = new AsciiVerticalTranspositionCipher(keyOrShift);
//                            String encrypted = cipher.encrypt(input);
//                            System.out.println(encrypted);

                            String decrypted = cipher.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Hill"))
                        {
//                            String key = "КЛЮЧ123"; // Пример ключа
                            HillCipher hillCipher = new HillCipher(keyOrShift);

//                            String message = input;
//                            String encrypted = hillCipher.encrypt(input);
                            String decrypted = hillCipher.decrypt(input);

//                            System.out.println("Исходное сообщение: " + message);
                            outputArea.setText(decrypted);
//                            System.out.println("Расшифрованное сообщение: " + decrypted);
                        }
                        else if(selectedCipher.equals("Algebra of matrix"))
                        {
                            AlgebraOfMatrix aom=new AlgebraOfMatrix(keyOrShift);
                            String decrypted=aom.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Gronsfeld"))
                        {
                            String decrypted=AsciiGronsfeldCipher.decrypt(input,keyOrShift);
                            outputArea.setText(decrypted);
                        }
//                        else if (selectedCipher.equals("RSA")) {
//                            RSA rsa = new RSA();
//                            String decrypted = rsa.decrypt(input);
//                            outputArea.setText(decrypted);
//                        }
                    }
                    else if(selectedCoding.equals("UNICODE"))
                    {
                        if(selectedCipher.equals("Caesar"))
                        {
                            int shift=Integer.parseInt(keyOrShift);
                            String decrypted= UnicodeCaesarCipher.decrypt(input,shift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Vigenere"))
                        {
                            String decrypted = UnicodeVigenereCipher.decrypt(input, keyOrShift);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("PlayFair"))
                        {
                                if(row*col==65504){
                                UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift,row,col);
    //                            UnicodePlayFairCipher cipher = new UnicodePlayFairCipher(keyOrShift);
                                String decrypted = cipher.decrypt(input,row,col);
                                outputArea.setText(decrypted);
                            } else JOptionPane.showMessageDialog(frame, "Rows * Columns is not 65504", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        else if(selectedCipher.equals("Vertical Transposition"))
                        {
                            UnicodeVerticalTranspositionCipher cipher = new UnicodeVerticalTranspositionCipher(keyOrShift);
                            String decrypted = cipher.decrypt(input);
                            outputArea.setText(decrypted);
                        }
                        else if(selectedCipher.equals("Gronsfeld"))
                        {
                            String decrypted= UnicodeGronsfeldCipher.decrypt(input,keyOrShift);
                            outputArea.setText(decrypted);
                        }
//                        else if(selectedCipher.equals("RSA")){
////                            int p1=row;
////                            int q1=col;
////                            int e1=ee;
////                            RSA rsa=new RSA(p1,q1,e1);
////                            String decr=rsa.decrypt(input);
////                            outputArea.setText(decr);
//
//
//
//                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Shift value must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show frame
        frame.setVisible(true);
    }
}
