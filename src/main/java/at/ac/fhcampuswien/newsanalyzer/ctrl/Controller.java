package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiException;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.beans.Source;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {
	public static final String APIKEY = "0db5ce9d6c1743fdb41549eb1f1f9840";  //TODO add your api key

	public void process(NewsApi newsApi) {
		System.out.println("Start process");
		//TODO implement Error handling
		//TODO load the news based on the parameters
		NewsResponse newsResponse = (NewsResponse) getData(newsApi);

		try {
			newsResponse = newsApi.getNews();
		} catch (NewsApiException e) {
			System.out.println("NewsResponse cannot be empty.");
			e.printStackTrace();
		} catch (NullPointerException n){
			System.out.println("NewsResponse cannot be NULL");
			n.printStackTrace();
		}
		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString() + "\n"));
			sortByLength(articles);
			numberOfArticles(articles);
			providerWithMostArticles(articles);
			shortestName(articles);
		}

		System.out.println("End process");
	}

	// TODO implement methods for analysis
	// a. Anzahl der Artikel (mit PageSize können maximal 100 Artikel geladen werden)
	public static void numberOfArticles(List<Article> articles) {
		long amount = articles.size();
		System.out.println("The amount of Articles: " + amount);
	}
	// b. Welcher Provider liefert die meisten Artikel?
	public static void providerWithMostArticles(List<Article> articles) {
		String provider =
				articles.stream()
						.map(Article::getSource)
						.map(Source::getName)
						.collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
						.entrySet().stream().max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse(null);

		System.out.println("Which provider has the most articles: " + provider);
	}
	// c. Welcher Autor hat den kürzesten Namen?
	public static void shortestName(List<Article> articles) {
		String shortest = articles.stream()
				.filter(article -> article.getAuthor() != null)
				.map(Article::getAuthor)
				.min(Comparator.comparing(String::length))
				.orElse(null);

		System.out.println("The author with the shortest name: " + shortest);
	}
	// d. Sortieren Sie den Titel nach dem längsten Titel nach dem Alphabet.
	public static void sortByLength(List<Article> articles) {
		System.out.println("Alle Artikel sortiert nach länge und Alphabet: ");

		articles.stream()
				.map(Article::getTitle)
				.sorted(Comparator.comparing(String::length).reversed())
				.forEach(System.out::println);
	}

	public Object getData(NewsApi newsApi) {
		NewsResponse newsResponse = null;
		try {
			newsResponse = newsApi.getNews();
		} catch (NewsApiException e) {
			System.out.println("Error:" + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("cause: " + e.getCause().getMessage());
			}
		}
		return newsResponse;
	}
}