package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int lastArticleId = 0;
        System.out.println("== Java Text Board Start ==");

        while (true) {
            System.out.print("Order) ");
            String cmd = sc.nextLine();

            if (cmd.equals("/usr/article/write")) {
                System.out.println("== Aricle Writing ==");
                System.out.print("Title : ");
                String subject = sc.nextLine();

                System.out.print("Content : ");
                String content = sc.nextLine();

                int id = ++lastArticleId;

                Article article = new Article();
                article.id = id;
                article.subject = subject;
                article.content = content;

                System.out.println("Created Article : " + article);
                System.out.printf("Aricle %d created.\n", article.id);
            }
            else if (cmd.equals("exit")) {
                System.out.println("Program Ends");
                break;
            }
            else {
                System.out.println("Incorrect Order");
            }
        }

        System.out.println("== Java Text Board End ==");
        sc.close();
    }
}

class Article {
    int id;
    String subject;
    String content;

    @Override
    public String toString() {
        return "{id: %d, subject: \"%s\", content: \"%s\"}".formatted(id, subject, content);
    }
}