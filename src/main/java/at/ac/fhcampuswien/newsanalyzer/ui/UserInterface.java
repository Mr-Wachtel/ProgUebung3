package at.ac.fhcampuswien.newsanalyzer.ui;

import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.enums.Language;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class UserInterface {
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("News zu zum Thema 'Gesundheit': ");
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("Corona")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.health)
				.createNewsApi();
		ctrl.process(newsApi);
	}

	public void getDataFromCtrl2(){
		// TODO implement me
		System.out.println("News zu zum Thema 'Entertainment': ");
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("Film")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.entertainment)
				.createNewsApi();
		ctrl.process(newsApi);
	}

	public void getDataFromCtrl3(){
		// TODO implement me
		System.out.println("News zu zum Thema 'Sport': ");
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ("Euro")
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.setSourceCategory(Category.sports)
				.createNewsApi();
		ctrl.process(newsApi);
	}
	public void getDataForCustomInput() {
		// TODO implement me								--> ERWEITERUNG
		String customQuery = "";
		System.out.println("News zu welchem Thema möchten Sie lesen? ");
		Scanner scanner = new Scanner(System.in);
		customQuery = scanner.next();

		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(Controller.APIKEY)
				.setQ(customQuery)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setSourceCountry(Country.at)
				.createNewsApi();
		ctrl.process(newsApi);
	}

	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "Choice 'Health'", this::getDataFromCtrl1);
		menu.insert("b", "Choice 'Entertainment'", this::getDataFromCtrl2);
		menu.insert("c", "Choice 'Sport'", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			choice.run();
		}
		System.out.println("Program finished");
	}

	protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
		while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			} catch (NumberFormatException e) {
				number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
				number = null;
			} else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
				number = null;
			}
		}
		return number;
	}
}