package org.example;

import java.nio.charset.Charset;

public class AsciiGronsfeldCipher {
    private static final Charset ENCODING = Charset.forName("windows-1251"); // Cyrillic-compatible encoding
    public static String encrypt(String text,String key)
    {
        byte[] textBytes=text.getBytes(ENCODING);
        byte[] cipher=new byte[textBytes.length];
        int keylength=key.length();
        int k=0;
        while(text.length()!=key.length())
        {
            key+=key.charAt((k++)%keylength);
        }
        byte[] keyBytes = key.getBytes(ENCODING);
        for(int i=0;i<textBytes.length;i++)
        {
            int b = Byte.toUnsignedInt(textBytes[i])-32;
            int shift=Byte.toUnsignedInt(keyBytes[i])-32;
            if (b >=0 && b < 224) {
                // Perform shift with wrapping
//                b = ((b - 32 + shift) % 224) + 32;
                  b=(b+shift)%224+32;
            }
            cipher[i] = (byte) b;
        }
        return new String(cipher, ENCODING); // Decode back to String
    }

    public static String decrypt(String text,String key)
    {
        byte[] textBytes=text.getBytes(ENCODING);
        byte[] cipher=new byte[textBytes.length];
        byte[] keybytes=key.getBytes(ENCODING);
        int keylength=key.length();
        int k=0;
        while(text.length()!=key.length())
        {
            key+=key.charAt((k++)%keylength);
        }
        byte[] keyBytes = key.getBytes(ENCODING);
        for(int i=0;i<textBytes.length;i++)
        {
            int b = Byte.toUnsignedInt(textBytes[i])-32;
            int shift=Byte.toUnsignedInt(keyBytes[i])-32;
            if (b >= 0 && b <= 223) {
                // Perform shift with wrapping
                b =(b-shift+224)%224+32;
            }
            cipher[i] = (byte) b;
        }
        return new String(cipher, ENCODING); // Decode back to String
    }
}
