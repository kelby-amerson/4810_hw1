package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.paint.Color;

public class Main extends Application {

    PixelWriter pixelWriter;
    //https://docs.oracle.com/javafx/2/image_ops/jfxpub-image_ops.htm
    @Override
    public void start(Stage primaryStage) throws Exception{

        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);
        final Canvas canvas = new Canvas(300,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        //gc.setFill(Color.DARKGRAY);
        //gc.fillRect(75,75,100,100);

        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        long startTime = System.nanoTime();
        BasicAlgorithm(20,50,170,150);
        long endTime = System.nanoTime();
        long duration = endTime-startTime;
        System.out.println(duration);

        startTime = System.nanoTime();
        BresenhamAlg(20,50,170,150);
        endTime = System.nanoTime();
        duration = endTime-startTime;
        System.out.println(duration);

        root.getChildren().add(canvas);


        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(s);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void BasicAlgorithm(int x0, int y0, int x1, int y1){
        double deltax = x1-x0;
        double deltay = y1-y0;
        int m;
        if(deltay !=0) {
            m = (int) Math.round(deltay/deltax);
        }
        else {
            m = 0;
        }
       /* if(m<0){
            m *= -1;
        }
        */


        int tempx;
        int tempy;
        if(deltax != 0 || deltay != 0) {
            for (int i = 0; i <= (deltax - 1); i++) {
                tempx = x0 + i;
                tempy = m * i + y0;
                pixelWriter.setColor(tempx, tempy, Color.DARKGRAY);
            }
        }
        if(deltax == 0){
            for (int i = 0; i <= (deltay-1); i++) {
                tempx = x0;
                tempy = i + y0;
                pixelWriter.setColor(tempx, tempy, Color.DARKGRAY);
            }
        }
        else if(deltay == 0){
            for (int i = 0; i <= (deltax - 1); i++) {
                tempx = x0+i;
                tempy = y0;
                pixelWriter.setColor(tempx, tempy, Color.DARKGRAY);
            }
        }
    }



    public void BresenhamAlg(int x0, int y0, int x1, int y1){
        int deltax = x1-x0;
        int deltay = y1-y0;

        if(deltax>deltay && x1>x0 && y1>y0) {

            int m;
            int e = 2 * deltay - deltax;
            int increment1 = 2 * deltay;
            int increment2 = 2 * (deltay - deltax);


            pixelWriter.setColor(x0, y0, Color.DARKGRAY);
            int tempx = x0;
            int tempy = y0;

            while (tempx != x1) {
                pixelWriter.setColor(tempx, tempy, Color.DARKGRAY);
                if (e < 0) {
                    e += increment1;
                } else {
                    tempy = tempy + 1;
                    e += increment2;
                }

                tempx += 1;
            }
        }
        else {
            System.out.println("Requirements not met");
            



            //return;
        }
    }
}
