
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;


import java.io.IOException;

/**
 * Created by Lenovo on 2017-05-11.
 */
public class Main extends Application {

    Stage primaryStage;
    AnchorPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("test");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Kalambury");

        initRootLayout();

    }

    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            System.out.println("a");
            loader.setLocation(Main.class.getResource("view/rootLayout.fxml"));
            System.out.println("b");
            rootLayout = loader.load();
            System.out.println("c");

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tron");
            System.out.println("d");
            primaryStage.show();
            System.out.println("e");

            primaryStage.setOnCloseRequest(event -> {
                Platform.exit();
                System.exit(0);
            });




        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}