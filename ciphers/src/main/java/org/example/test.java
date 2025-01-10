package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class test {
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding

    private static int bytetoindex(byte b) {
        int value = Byte.toUnsignedInt(b); // Convert the byte to an unsigned integer
        if(value==9) return 1;
        else if(value==10) return 2;
        else if(value==13) return 3;
        else return value-28;// Example transformation
    }

    public static void main(String[] args) throws IOException {
        String file = "E:\\Ulugbeks work\\7th semester\\Безопасность компьютера\\234\\Задание 5\\Задание 5\\6.txt";
        {String content = Files.readString(Path.of(file));
        for(char c:content.toCharArray()){
            int k = c;
            System.out.println(c+"--" +k);
        }
//        String text="Hello\rWorld";

//        try {
//            // Записываем строку в файл (создается или перезаписывается)
//            Files.write(Path.of(file), text.getBytes());
//
//            System.out.println("Содержимое успешно записано в файл.");
//        } catch (IOException e) {
//            System.err.println("Ошибка при записи в файл: " + e.getMessage());
////        }
//        try {
//            // Читаем все содержимое файла как строку
//            String content = Files.readString(Path.of(file));
//
//            // Перебираем каждый символ
//            for (char c : content.toCharArray()) {
//                byte[] bytes = String.valueOf(c).getBytes(ENCODING); // Преобразуем символ в байты
//                System.out.printf("Символ: '%c', Байты: ", c);
//                for (byte b : bytes) {
//                    System.out.printf("%d ", Byte.toUnsignedInt(b));
//                }
//                System.out.println();
//            }
//        } catch (IOException e) {
//            System.err.println("Ошибка при чтении файла: " + e.getMessage());
//        }
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Enter text (including tabs):");
//        String input = scanner.nextLine(); // Read input from the console
//
//        // Encode the string into bytes using Windows-1251 encoding
//        byte[] bytes = input.getBytes();
//
//        // Process each byte
//        Integer i=0;
//        for (byte bb:bytes) {
//            int tt = Byte.toUnsignedInt(bb);
//            System.out.println(input.charAt(i)+"->"+tt);
//            i++;
//
//        }
////        private String indexTochar(int a)
////        {
////        int a=181;
////            System.out.println((char)(a));
//////        }
//
//        scanner.close(); // Close the scanner
    }
}}
