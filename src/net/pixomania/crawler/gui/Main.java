/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.pixomania.crawler.ParserRunnable;
import net.pixomania.crawler.datatypes.SpecificRule;
import net.pixomania.crawler.datatypes.Standard;
import net.pixomania.crawler.datatypes.StandardVersion;
import net.pixomania.crawler.parser.Parser;
import net.pixomania.crawler.parser.rules.date.DateRule1;
import net.pixomania.crawler.parser.rules.date.DateRule2;
import net.pixomania.crawler.parser.rules.editors.EditorsRule1;
import net.pixomania.crawler.parser.rules.editors.EditorsRule2;
import net.pixomania.crawler.parser.rules.editors.EditorsRule3;
import net.pixomania.crawler.parser.rules.editors.SpecificEditorsRule1;
import net.pixomania.crawler.parser.rules.previous.PreviousRule1;
import net.pixomania.crawler.parser.rules.status.StatusRule1;
import net.pixomania.crawler.parser.rules.status.StatusRule2;
import net.pixomania.crawler.parser.rules.status.StatusRule3;
import net.pixomania.crawler.parser.rules.title.TitleRule1;
import net.pixomania.crawler.parser.rules.title.TitleRule2;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

	public static Browser getBrowser() {
		return browser;
	}

	private static Browser browser;
	private static final VBox infopanel = new VBox();

	private static final ParserRunnable parser = new ParserRunnable();
	private final Thread parserThread = new Thread(parser);

	private final static ArrayList<Standard> standards = new ArrayList<>();
	private final static HashMap<String, Parser> parsers = new HashMap<>();

	public static ArrayList<Standard> getStandards() {
		return standards;
	}

	public static HashMap<String, Parser> getParsers() {
		return parsers;
	}

	@Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest((event) -> {
			Platform.exit();
			System.exit(0);
		});

        primaryStage.setTitle("Hello World");

		browser = new Browser();

		browser.setPrefHeight(600);
		infopanel.setPrefHeight(300);

		VBox box = new VBox();
		box.getChildren().add(browser);

		box.getChildren().add(infopanel);

		Scene scene = new Scene(box, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

		parsers.put("date", new Parser<>(new DateRule1(), new DateRule2()));
		parsers.put("title", new Parser<>(new TitleRule1(), new TitleRule2()));
		parsers.put("status", new Parser<>(new StatusRule1(), new StatusRule2(), new StatusRule3()));
		parsers.put("editors", new Parser<>(new EditorsRule1(), new EditorsRule2(), new EditorsRule3()));
		parsers.put("previous", new Parser<>(new PreviousRule1()));

//		standards.add(new Standard("IndexedDB", "http://www.w3.org/TR/IndexedDB/"));
//		standards.add(new Standard("webrtc", "http://www.w3.org/TR/webrtc/"));
//		standards.add(new Standard("geolocation-API", "http://www.w3.org/TR/geolocation-API/"));
//		standards.add(new Standard("webdatabase", "http://www.w3.org/TR/webdatabase/"));
//		standards.add(new Standard("webstorage", "http://www.w3.org/TR/webstorage/"));
//		standards.add(new Standard("touch-events", "http://www.w3.org/TR/touch-events/"));
		standards.add(new Standard("selectors-api", "http://www.w3.org/TR/selectors-api/"));

		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/", new SpecificEditorsRule1());

		parserThread.start();
    }

	public static void redrawInfopanel(String currentStandard, StandardVersion sv) {
		infopanel.getChildren().clear();

		HBox container = new HBox();
		VBox current = new VBox();

		current.getChildren().add(new Label("Current standard: " +currentStandard));

		if (sv != null) {
			current.getChildren().add(new Label("Name: " + sv.getTitle()));
			current.getChildren().add(new Label("Date: " + sv.getDate()));
			current.getChildren().add(new Label("Status: " + sv.getStatus()));
			current.getChildren().add(new Label("Link: " + sv.getLink()));
			current.getChildren().add(new Label("Editors: "));

			for (String[] editor : sv.getEditors()) {
				current.getChildren().add(new Label("  " + editor[0] + ", " + editor[1]));
			}

			current.setPrefWidth(500);
			container.getChildren().add(current);

			Button btn = new Button("Correct");
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					synchronized (parser) {
						btn.setDisable(true);
						parser.notify();
					}
				}
			});

			current.getChildren().add(btn);
		}

		VBox all = new VBox();

		for (Standard standard : standards) {
			all.getChildren().add(new Label("Name: " + standard.getName()));
			for (StandardVersion s : standard.getVersions()) {
				all.getChildren().add(new Label("Name: " + s.getTitle()));
				all.getChildren().add(new Label("Date: " + s.getDate()));
				all.getChildren().add(new Label("Status: " + s.getStatus()));
				all.getChildren().add(new Label("Link: " + s.getLink()));
				all.getChildren().add(new Label("Editors: "));

				for (String[] editor : s.getEditors()) {
					all.getChildren().add(new Label("  " + editor[0] + ", " + editor[1]));
				}

				all.getChildren().add(new Label("Previous: "));

				for (StandardVersion prev : s.getPrev()) {
					all.getChildren().add(new Label("  " + prev.getLink()));
				}

				all.getChildren().add(new Label("Next: "));

				for (StandardVersion next : s.getNext()) {
					all.getChildren().add(new Label("  " + next.getLink()));
				}

				Separator sep = new Separator();
				sep.setPrefWidth(500);
				all.getChildren().add(sep);
			}
			Separator sep = new Separator();
			sep.setStyle("-fx-background-color: #000000;-fx-background-radius: 3px;");
			sep.setPrefWidth(500);
			all.getChildren().add(sep);

		}

		ScrollPane sp = new ScrollPane();
		sp.setContent(all);
		sp.setPrefWidth(500);
		container.getChildren().add(sp);

		infopanel.getChildren().add(container);
	}

	public static void confirmDialog(String name, String url) {
		infopanel.getChildren().clear();

		infopanel.getChildren().add(new Label("Does " + url + " belong to standard " + name + "?"));

		Button yes = new Button("Yes");
		Button no = new Button("No");

		yes.setOnAction((event) -> {
				synchronized (parser) {
					parser.setOrphan(false);
					parser.notify();
				}
		});

		no.setOnAction((event) -> {
				synchronized (parser) {
					parser.setOrphan(true);
					parser.notify();
				}
		});

		infopanel.getChildren().addAll(yes, no);
	}

    public static void main(String[] args) {
        launch(args);
    }
}
