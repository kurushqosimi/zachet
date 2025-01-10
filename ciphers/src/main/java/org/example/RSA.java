package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RSA {
    private static boolean isPrime(int n){
        for(int i=2;i<=Math.sqrt(n);i++){
            if(n%i==0) return false;
        }
        return true;
    }
    //a=2, n=3,m=5
    private static int a_power_n_mod_m (int a,int n, int m){
        int res=1;
        for(int i=1;i<=n;i++)
        {
            res=(res*a)%m;
        }
        return res;
    }
    // n = p*q
    // m = (p-1)*(q-1)
    // d = n-1;
    private static boolean relPrime(int a,int b){
        if((a%2==0) && (b%2==0)) return false;
        for(int i=3;i<=Math.sqrt(a);i+=2){
            if((a%i==0) && (b%i==0)) return false;
        }
        return true;
    }
    private static int computeD(int p, int q){
        int n=p*q;
        int m=(p-1)*(q-1);
        int d=n-1;
        int i=2;

        while(i!=m)
        {
            if(((d%i)==(m%i)) && (d%i==0)) {d--;i=2;}
            else {i++;}
        }
        return d;
    }
    private static int good(int k)
    {
        if((k>1000)&&(k<1104)) return 1;
        else return 0;
    }
    private static int comp(char[] ss){

        for(int p=11;p<98;p+=2)
        {
            for(int q=101;q<300;q+=2)
            {
                if(isPrime(p)&& isPrime(q)){
//                    int dd=computeD(p,q);
                    int m=(p-1)*(q-1);
                    for(int ee=10;ee<(p-1)*(q-1);ee++){
                        if(relPrime(ee,(p-1)*(q-1))){
                            System.out.println("p="+p+" q="+q+" e="+ee+"\n");
                            for(int dd=1;dd<p*q;dd++) {
                                if((dd*ee)%m==1){
                                    int n = p * q;
                                    int sum=0;
                                    for(char c:ss){
                                        int k=c;
                                        int k1=a_power_n_mod_m(k,dd,n);
                                        sum+=good(k1);
                                    }
                                    if (sum>ss.length-4) {
                                        System.out.print("p=" + p + " q=" + q + " d=" + dd + "\n");
                                        return 1;
                                    }}
                            }
                        }
                    }
                }
            }
        }
return -1;
    }
    public static void main(String[] args) throws IOException {
        String file = "E:\\Ulugbeks work\\7th semester\\Безопасность компьютера\\234\\Задание 5\\Задание 5\\5.txt";
        String content = Files.readString(Path.of(file));
//        int a=2812;

//        System.out.println(relPrime(10,101));
//        int b=6271;
//        int c=1595;
//        int d=415;
//        int e=4719;
//        int f=3809;
//        for(int p=11;p<98;p+=2)
//        {
//            for(int q=101;q<300;q+=2)
//            {
//                if(isPrime(p)&& isPrime(q)){
////                    int dd=computeD(p,q);
//                    int m=(p-1)*(q-1);
//                    for(int ee=10;ee<(p-1)*(q-1);ee++){
//                        if(relPrime(ee,(p-1)*(q-1))){
//                    for(int dd=1;dd<p*q;dd++) {
//                        if((dd*ee)%m==1)){
//                        int n = p * q;
//                        int a1 = a_power_n_mod_m(a, dd, n);
//                        int b1 = a_power_n_mod_m(b, dd, n);
//                        int c1 = a_power_n_mod_m(c, dd, n);
//                        int d1 = a_power_n_mod_m(d, dd, n);
//                        int e1 = a_power_n_mod_m(e, dd, n);
//                        int f1 = a_power_n_mod_m(f, dd, n);
//
//                        if ((good(a1) + good(b1) + good(c1) + good(d1) + good(e1) + good(f1)) >= 5) {
//                            System.out.print("p=" + p + " q=" + q + " d=" + d + "\n");
//                        }}
//                    }
//                    }
//                    }
//                }
//            }
//        }

//                byte[] bytes = String.valueOf(c).getBytes(); // Преобразуем символ в байты
//            System.out.printf("Символ: '%c', Байты: '%d", c,(int) c);
//                    System.out.printf("%d ", Byte.toUnsignedInt(bytes));
//            int k=c;
        comp(content.toCharArray());
        int p=11;
        int q=101;
        int d=91;
                    int m=(p-1)*(q-1);
                    int n = p * q;
                    for (char c : content.toCharArray()) {
                        int k = c;
                        int pp = a_power_n_mod_m(k, d, n);
                        System.out.print((char) pp);
                                        }
                }
            }




