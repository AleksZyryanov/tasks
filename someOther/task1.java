package com.example.demo;

public class task1 {
    public static void main(String[] args) {

    }

    public void solution(String S, String C) {
        // write your code in Java SE 8
        String[] myList = S.split("/n");

        for (int i = 1; i < myList.length; i++) {
            String[] temp = myList[i].split(",");
          System.out.printf("| " + C + " | ", temp[stringToFieldInt(C)]);
        }

    }
    public int stringToFieldInt(String C){
        int res;
    switch(C){
        case"id":
        res = 0;
        break;
        case "name":
        res = 1;
        break;
        default:
            res = -1;
            break;
    }
    return res;
    }
}
