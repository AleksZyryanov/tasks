package com.example.demo;

public class RecursionTesting {

    public  static void main(String[] args){

        System.out.println(reverseString("JHGJHGJ12134567jhjhjjjj"));
    }

	public static String reverseString(String s){
	    if (s.length() == 0) 
	         return s;

	    return reverseString(s.substring(1)) + s.charAt(0);
}

}
