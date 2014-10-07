/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.Mozilla.bugtracker.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.pixomania.crawler.gui.Browser;

public class BugtrackerGUI extends Application {

	public static Browser getBrowser() {
		return browser;
	}

	private static Browser browser;
	private static final VBox infopanel = new VBox();

	private static final ProgressBar pb = new ProgressBar(0);
	private static final Label count = new Label();

	private static int current = 0;
	private static int totalEmails = 0;

	@Override
    public void start(Stage primaryStage) throws Exception {
		primaryStage.setOnCloseRequest((event) -> {
			Platform.exit();
			System.exit(0);
		});

        primaryStage.setTitle("Hello World");

		pb.setPrefWidth(1000);

		infopanel.getChildren().add(pb);
		infopanel.getChildren().add(count);

		Scene scene = new Scene(infopanel, 1000, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

	public static void setTotalEmails(int total) {
		totalEmails = total;
		count.setText(current + "/" + totalEmails + " parsed");
	}

	public static void updateProgress() {
		current++;
		pb.setProgress((double) current/(double)totalEmails);
		count.setText(current + "/" + totalEmails + " parsed");
	}

}
