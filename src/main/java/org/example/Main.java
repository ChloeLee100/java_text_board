package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Main {
    static void article_test_data(List<Article> articles) {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> articles.add(new Article(i, "Subject" + i, "Content" + i)));
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

            Rq rq = new Rq(cmd);

            if (rq.getUrlPath().equals("/usr/article/write")) {
                actionUsrArticleWrite(sc, articles, lastArticleId);
                lastArticleId++;
            }
            else if (rq.getUrlPath().equals("/usr/article/list")) {
                actionUsrArticleList(rq, articles);
            }
            else if (rq.getUrlPath().equals("/usr/article/detail")) {
                actionUsrArticleDetail(rq, articles);
            }
            else if (rq.getUrlPath().equals("exit")) {
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

    private static void actionUsrArticleWrite(Scanner sc, List<Article> articles, int lastArticleId) {
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

    private static void actionUsrArticleDetail(Rq rq, List<Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("No article created.");
            return;
        }

        Map <String, String> params = rq.getParams();

        if (!params.containsKey("id")) {
            System.out.println("Please enter the ID number.");
            return;
        }

        int id = 0;

        try {
            id = Integer.parseInt(params.get("id"));
        } catch (NumberFormatException e) {
            System.out.println("Please enter the proper ID number.");
            return;
        }

        if (id > articles.size()) {
            System.out.printf("Article %d is not existed.\n", id);
            return;
        }

        Article article = articles.get(id - 1);

        System.out.println("== Article Detail ==");
        System.out.printf("ID : %d\n", article.id);
        System.out.printf("Subject : %s\n", article.subject);
        System.out.printf("Content : %s\n", article.content);
    }

    private static void actionUsrArticleList(Rq rq, List<Article> articles) {
        if (articles.isEmpty()) {
            System.out.println("No article created.");
            return;
        }

        Map <String, String> params = rq.getParams();

        List<Article> filteredArticles = articles;

        if (params.containsKey("searchKeyword")) {
            String searchKeyword = params.get("searchKeyword");

            filteredArticles = new ArrayList<>();

            for (Article article : articles) {
                boolean matched = article.subject.contains(searchKeyword) || article.content.contains(searchKeyword);

                if (matched) filteredArticles.add(article);
            }
        }

        boolean orderByIdDesc = true;

        if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
            orderByIdDesc = false;
        }

        List<Article> sortedArticles = filteredArticles;

        if (orderByIdDesc) {
            sortedArticles = Util.reverseList(sortedArticles);
        }

        System.out.println("== Article List ==");

        System.out.println("Id | Subject");

        sortedArticles.forEach(
                article -> System.out.printf("%d | %s\n", article.id, article.subject)
        );
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

class Rq {
    String url;
    Map<String, String> params;
    String urlPath;

    Rq(String url) {
        this.url = url;
        params = Util.getParamsFromUrl(this.url);
        urlPath = Util.getPathFromUrl(this.url);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrlPath() {
        return urlPath;
    }
}

class Util {
    static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();
        String[] urlBits = url.split("\\?", 2);

        if (urlBits.length == 1) {
            return params;
        }

        String queryStr = urlBits[1];

        for (String bit : queryStr.split("&")) {
            String[] bits = bit.split("=", 2);

            if (bits.length == 1) {
                continue;
            }

            params.put(bits[0], bits[1]);
        }

        return params;
    }

    static String getPathFromUrl (String url) {
        return url.split("\\?", 2)[0];
    }

    // This function makes new list without affecting original list.
    public static<T> List<T> reverseList(List<T> list) {
        List<T> reverse = new ArrayList<>(list.size());

        for (int i = list.size() - 1; i >= 0; i--) {
            reverse.add(list.get(i));
        }

        return reverse;
    }
}