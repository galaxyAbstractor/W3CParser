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
			current.getChildren().add(new Label("Name: " + sv.getTitle()));
			current.getChildren().add(new Label("Date: " + sv.getDate()));
			current.getChildren().add(new Label("Status: " + sv.getStatus()));
			current.getChildren().add(new Label("Link: " + sv.getLink()));
			current.getChildren().add(new Label("Editors: "));

			for (String[] editor : sv.getEditors()) {
				current.getChildren().add(new Label("  " + editor[0] + ", " + editor[1]+
						((editor[2].equals("")) ? "" : " " + editor[2])));
			}

			current.setPrefWidth(500);
			container.getChildren().add(current);

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

		VBox all = new VBox();

		for (Standard standard : W3C.getStandards()) {
			all.getChildren().add(new Label("Name: " + standard.getName()));
			for (StandardVersion s : standard.getVersions()) {
				all.getChildren().add(new Label("Name: " + s.getTitle()));
				all.getChildren().add(new Label("Date: " + s.getDate()));
				all.getChildren().add(new Label("Status: " + s.getStatus()));
				all.getChildren().add(new Label("Link: " + s.getLink()));
				all.getChildren().add(new Label("Editors: "));

				for (String[] editor : s.getEditors()) {
					all.getChildren().add(new Label("  " + editor[0] + ", " + editor[1] +
							((editor[2].equals("")) ? "" : " " + editor[2])));
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

}
