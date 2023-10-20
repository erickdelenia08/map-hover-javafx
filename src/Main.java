
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a BackgroundImage
        Pane root = new Pane();

        Image backgroundImage = new Image("./image/map_unej.jpg");
        double width = backgroundImage.getWidth();
        double height = backgroundImage.getHeight();
        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        // Create a Background with the BackgroundImage
        Background backgroundWithImage = new Background(background);
        // Set the background to the root pane
        root.setBackground(backgroundWithImage);

        Rectangle hoverArea = creaRectangle(320, 120, 145, 145, Color.TRANSPARENT, true);
        Group gAudit = createLabelGroup(hoverArea, "G. Auditorium", 400, 200, "l_auditorium");

        Circle r = createCircle(590, 305, 50, Color.RED, true);
        Group gMasjid = createLabelGroup(r, "Masjid Unej", 590, 305, "l_masjid");

        Shape rt = creaRectangle(950, 170, 145, 145, Color.TRANSPARENT, true);
        rt = rotate(rt, 20, 950 + 145 / 2, 170 + 145 / 2);
        Group gFmipa = createLabelGroup(rt, "F. MIPA", 970 + 145 / 2, 180 + 145 / 2, "l_fmipa");

        Shape rt2 = creaRectangle(260, 730, 145, 230, Color.TRANSPARENT, true);
        rt2 = rotate(rt2, 20, 260 + 145 / 2, 730 + 145 / 2);
        Group gStad = createLabelGroup(rt2, "Stadion Unej", 260 + 145 / 2, 730 + 145 / 2, "l_stadion");

        Shape rt3 = creaRectangle(800, 400, 135, 150, Color.RED, true);
        rt3 = rotate(rt3, 20, 800 + 135 / 2, 400 + 150 / 2);
        Group gRekt = createLabelGroup(rt3, "Rektorat Unej", 830, 420 + 150 / 2, "l_rektorat");

        Shape rt4 = creaRectangle(1020, 520, 135, 170, Color.RED, true);
        rt4 = rotate(rt4, 20, 1020 + 135 / 2, 520 + 170 / 2);
        Group gLShaun = createLabelGroup(rt4, "Lap. Shaun the Sheep", 1020 + 135 / 2, 520 + 170 / 2, "l_shaunthesheep");

        root.getChildren().addAll(gAudit, gMasjid, gFmipa, gStad, gRekt, gLShaun);
        // Add hover effects

        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add("./style/styles.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Set the title of the stage
        primaryStage.setTitle("Peta Universitas Jember");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    Circle createCircle(double x, double y, double r, Paint color, boolean visibility) {
        Circle circle = new Circle(x, y, r);
        circle.setFill(color);
        circle.setVisible(visibility);
        circle.getStyleClass().add("circle");
        return circle;
    }

    Rectangle creaRectangle(double x, double y, double w, double h, Paint color, boolean visibilty) {
        Rectangle rectangle = new Rectangle(x, y, w, h);
        rectangle.setFill(color);
        rectangle.setVisible(visibilty);
        rectangle.getStyleClass().add("card");
        return rectangle;
    }

    Group createLabelGroup(Shape hoverArea, String txt, double x, double y, String idLabel) {
        Media media;
        if (txt.toLowerCase().contains("masjid")) {
            media = new Media(getClass().getResource("./audio/adzan.mp3").toString());
        } else if (txt.toLowerCase().contains("stadion")) {
            media = new Media(getClass().getResource("./audio/barcelona.mp3").toString());
        }else if (txt.toLowerCase().contains("shaun")) {
            media = new Media(getClass().getResource("./audio/Shaun the Sheep.mp3").toString());
        }  
        else {
            media = new Media(getClass().getResource("./audio/click.mp3").toString());
        }
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        Group group = new Group();
        hoverArea.setFill(Color.TRANSPARENT);
        Rectangle content = creaRectangle(x + 70, y - 70, 160, 30, Color.TRANSPARENT, true);

        Label label = new Label(txt);
        label.setLayoutX(x + 100);
        label.setLayoutY(y - 68);
        label.setId(idLabel);
        label.getStyleClass().add("label");
        label.setVisible(false);

        Line slopedLine = new Line(x, y, x + 70, y - 57);
        slopedLine.setStroke(Color.WHITE);
        slopedLine.setStrokeWidth(2.0);
        slopedLine.setVisible(false);

        Circle solidCircle = new Circle(x, y, 3);
        solidCircle.setFill(Color.RED);
        solidCircle.setVisible(false);

        hoverArea.setOnMouseEntered(event -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();

            content.setFill(Color.RED);
            label.setVisible(true);
            slopedLine.setVisible(true);
            solidCircle.setVisible(true);
        });

        hoverArea.setOnMouseExited(event -> {
            mediaPlayer.stop();

            content.setFill(Color.TRANSPARENT);
            label.setVisible(false);
            slopedLine.setVisible(false);
            solidCircle.setVisible(false);
        });

        group.getChildren().addAll(hoverArea, content, slopedLine, solidCircle, label);

        return group;
    }

    Shape rotate(Shape shape, double degree, double x, double y) {
        Rotate rotate = new Rotate();
        rotate.setAngle(degree);
        rotate.setPivotX(x);
        rotate.setPivotY(y);
        shape.getTransforms().add(rotate);
        return shape;
    }

}
