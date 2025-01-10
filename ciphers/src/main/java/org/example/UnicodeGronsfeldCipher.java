package org.example;

public class UnicodeGronsfeldCipher {
    public static String encrypt(String text, String key)
    {
        String result="";
        int i=0;
        int keyLength=key.length();
        while(key.length()!=text.length())
        {
            key+=key.charAt((i++)%keyLength);
        }
        for(int j=0;j<text.length();j++)
        {
            int shift=(int)(key.charAt(j))-32;
            int toshift=(int)(text.charAt(j))-32;
            int shifted=(toshift+shift)%65504+32;
            result+=(char)(shifted);
        }
        return result;
    }

    public static String decrypt(String text, String key)
    {
        String result="";
        int i=0;
        int keyLength=key.length();
        while(key.length()!=text.length())
        {
            key+=key.charAt((i++)%keyLength);
        }
        for(int j=0;j<text.length();j++)
        {
            int shift=(int)(key.charAt(j))-32;
            int toshift=(int)(text.charAt(j))-32;
            int shifted=(toshift-shift+65504)%65504+32;
            result+=(char)(shifted);
        }
        return result;
    }
}
