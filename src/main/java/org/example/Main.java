package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static void article_test_data(List<Article> articles) {
        articles.add(new Article(1, "Subject1", "Content1"));
        articles.add(new Article(2, "Subject2", "Content2"));
        articles.add(new Article(3, "Subject3", "Content3"));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Article> articles = new ArrayList<>();

        article_test_data(articles);

        int lastArticleId = articles.get(articles.size() - 1).id;

        System.out.println("== Java Text Board Start ==");

        while (true) {
            System.out.print("Order) ");
            String cmd = sc.nextLine();

            if (cmd.equals("/usr/article/write")) {
                System.out.println("== Article Writing ==");
                System.out.print("Title : ");
                String subject = sc.nextLine();

                System.out.print("Content : ");
                String content = sc.nextLine();

                int id = ++lastArticleId;

                Article article = new Article(id, subject, content);

                articles.add(article);

                System.out.println("Created Article : " + article);
                System.out.printf("Article %d created.\n", article.id);
            }
            else if (cmd.equals("/usr/article/list")) {
                if (articles.isEmpty()) {
                    System.out.println("No article created.");
                    continue;
                }

                System.out.println("== Article List ==");

                System.out.println("Id | Subject");

                for (int i = articles.size() - 1; i >= 0; i--) {
                    Article article = articles.get(i);
                    System.out.printf("%d | %s\n", article.id, article.subject);
                }

//                articles.forEach(
//                        article -> System.out.printf("%d | %s\n", article.id, article.subject)
//                );
            }
            else if (cmd.equals("/usr/article/detail")) {
                if (articles.isEmpty()) {
                    System.out.println("No article created.");
                    continue;
                }

                Article article = articles.get(articles.size() - 1);

                if (article == null) {
                    System.out.println("No article created.");
                    continue;
                }

                System.out.println("== Article Detail ==");
                System.out.printf("ID : %d\n", article.id);
                System.out.printf("Subject : %s\n", article.subject);
                System.out.printf("Content : %s\n", article.content);
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

    Article(int id, String subject, String content) {
        this.id = id;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public String toString() {
        return "{id: %d, subject: \"%s\", content: \"%s\"}".formatted(id, subject, content);
    }
}