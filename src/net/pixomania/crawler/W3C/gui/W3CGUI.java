/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.gui;

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
import net.pixomania.crawler.W3C.ParserRunnable;
import net.pixomania.crawler.W3C.W3C;
import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;
import net.pixomania.crawler.gui.Browser;

public class W3CGUI extends Application {

	public static Browser getBrowser() {
		return browser;
	}

	private static Browser browser;
	private static final VBox infopanel = new VBox();



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

		synchronized (W3C.getParsers()) {
			W3C.getParsers().notify();
		}
    }

	/**
	 * Redraws the information panel with the current StandardVersion and information about
	 * all the standards
	 * @param currentStandard The standard currently being parsed
	 * @param sv The StandardVersion that was just parsed
	 */
	public static void redrawInfopanel(String currentStandard, StandardVersion sv) {
		infopanel.getChildren().clear();

		HBox container = new HBox();
		VBox current = new VBox();

		current.getChildren().add(new Label("Current standard: " +currentStandard));

		if (sv != null) {
			current.getChildren().add(new Label("Name (Rule: " + sv.getRules().get("title").getClass().getSimpleName() +"): " + sv.getTitle()));
			current.getChildren().add(new Label("Date (Rule: " + sv.getRules().get("date").getClass().getSimpleName() +"): " + sv.getDate()));
			current.getChildren().add(new Label("Status (Rule: " + sv.getRules().get("status").getClass().getSimpleName() + "): " + sv.getStatus()));
			current.getChildren().add(new Label("Link:" + sv.getLink()));

			if (sv.getEditors() != null) {
				current.getChildren().add(new Label("Editors (Rule: " + sv.getRules().get("editors").getClass().getSimpleName() + "):"));
				for (Person editor : sv.getEditors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			if (sv.getPreviousEditors() != null) {
				current.getChildren().add(new Label("Previous Editors (Rule: " + sv.getRules().get("previousEditors").getClass().getSimpleName() + "):"));

				for (Person editor : sv.getPreviousEditors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			if (sv.getSeriesEditors() != null) {
				current.getChildren().add(new Label("Series Editors (Rule: " + sv.getRules().get("seriesEditors").getClass().getSimpleName() + "):"));

				for (Person editor : sv.getSeriesEditors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			if (sv.getAuthors() != null) {
				current.getChildren().add(new Label("Authors (Rule: " + sv.getRules().get("authors").getClass().getSimpleName() + "):"));

				for (Person editor : sv.getAuthors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			if (sv.getContributors() != null) {
				current.getChildren().add(new Label("Contributors (Rule: " + sv.getRules().get("contributors").getClass().getSimpleName() + "):"));

				for (Person editor : sv.getContributors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			if (sv.getContributingAuthors() != null) {
				current.getChildren().add(new Label("Contributing Authors (Rule: " + sv.getRules().get("contributingAuthors").getClass().getSimpleName() + "):"));

				for (Person editor : sv.getContributingAuthors()) {
					current.getChildren().add(new Label("  " + editor.getName() + ", " + editor.getStandardAffiliation() +
							" until " + editor.getStandardAffiliationUntil() + " current " + editor.getCurrentAffiliation() +
							" until " + editor.getCurrentAffiliationUntil() + " via " + editor.getViaAffiliation() +
							" former " + editor.getFormerAffiliation() + " email " + editor.getEmail() + " WG " + editor.getWorkgroup() + " website " + editor.getWebsite()));
				}
			}

			ScrollPane sp1 = new ScrollPane();
			sp1.setContent(current);
			container.getChildren().add(sp1);

			Button btn = new Button("Correct");
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					synchronized (W3C.getParser()) {
						btn.setDisable(true);
						W3C.getParser().notify();
					}
				}
			});

			current.getChildren().add(btn);
		}


		infopanel.getChildren().add(container);
	}

	/**
	 * In case we found an "orphan", a standard that might not belong to the
	 * current Standard being parsed, we ask for manual confirmation if it's
	 * just a simple name change
	 * @param name The standard name
	 * @param url The URL
	 */
	public static void confirmDialog(String name, String url) {
		infopanel.getChildren().clear();

		infopanel.getChildren().add(new Label("Does " + url + " belong to standard " + name + "?"));

		Button yes = new Button("Yes");
		Button no = new Button("No");

		ParserRunnable parser = W3C.getParser();

	/*	yes.setOnAction((event) -> {
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
		});*/

		infopanel.getChildren().addAll(yes, no);
	}

}
