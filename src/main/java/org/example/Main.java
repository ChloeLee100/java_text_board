package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("== Java Text Board Start ==");

        while (true) {
            System.out.print("Order) ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")) {
                System.out.println("Program Ends");
                break;
            }

            System.out.printf("Given Order : %s\n", cmd);
        }

        System.out.println("== Java Text Board End ==");
        sc.close();
    }
}