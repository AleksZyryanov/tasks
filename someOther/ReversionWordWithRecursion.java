package com.example.demo;

public class ReversionWordWithRecursion {
//    static int current = 0;
    public static void main(String[] args){
        StringBuffer res = new StringBuffer();
        NeedreverseString word = new NeedreverseString("Hiuyiuwydwqd");
        word.reversion(0);
    }
    static class NeedreverseString {
        private String str;
        public NeedreverseString(String str){
            this.str = str;
        }
        private void reversion(int current) {

//            while (current != 10) {
//                System.out.println(current);
//                res.append(str.charAt(current));
//                current++;
//                reversion(current);

//            }
//            System.out.println(res.toString());
        }
    }
}

