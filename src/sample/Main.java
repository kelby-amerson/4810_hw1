package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Scanner;

public class Main extends Application {

    PixelWriter pixelWriter;
    //https://docs.oracle.com/javafx/2/image_ops/jfxpub-image_ops.htm
    @Override
    public void start(Stage primaryStage) throws Exception{
        //Prompts
        Scanner scan = new Scanner(System.in);
        System.out.println("How many lines do you want to draw?");
        int numOfLines = scan.nextInt();
        scan.close();

        //Setting up canvas to draw pixels on
        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);
        final Canvas canvas = new Canvas(300,300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //for loop to write each line
        for(int i=0; i<numOfLines;i++) {

            //random number generator
            int x0, x1, y0, y1;
            int deltax, deltay;

            Random randomNumGen = new Random();
            x0 = randomNumGen.nextInt(300);
            x1 = randomNumGen.nextInt(300);
            y0 = randomNumGen.nextInt(300);
            y1 = randomNumGen.nextInt(300);

            //206, 241 to 100, 285

            pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

            //writes and times the BasicAlgorithm Method
            long startTime = System.nanoTime();
            BasicAlgorithm(x0, y0, x1, y1);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Basic Number " +i+ ": "+duration);
            System.out.println(x0+", "+y0+" to "+x1+", "+y1);


            //writes and times the Bresenham's Algorithm Method
            startTime = System.nanoTime();
            BresenhamAlg(206, 241, 100, 285);
            endTime = System.nanoTime();
            duration = endTime - startTime;
            System.out.println("Bresenham Number "+i+": "+duration);
            System.out.println(x0+", "+y0+" to "+x1+", "+y1);

        }

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
        double m;
        if(deltay !=0) {
            m = deltay/deltax;
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
                tempy = (int)Math.round(m*i+y0);
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
        /*int deltax = x1-x0;
        int deltay = y1-y0;
        int tempx = x0;
        int tempy = y0;

        if(deltax>deltay && deltax !=0 || deltay != 0){

            int e = 2 * deltax - deltay;
            int increment1 = 2 * deltax;
            int increment2 = 2 * (deltax - deltay);


            pixelWriter.setColor(x0, y0, Color.DARKGRAY);
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



            //return;
        }*/

        int deltax = x1-x0;
        int deltay = y1-y0;
        int fraction, xstep, ystep;
        //fraction = -1;

        if(deltay<0){
            deltay = -deltay;
            ystep = -1;
        }
        else{
            ystep = 1;
        }
        if(deltax<0){
            deltax = -deltax;
            xstep = -1;
        }
        else{
            xstep = 1;
        }

        deltay <<= 1;
        deltax <<= 1;
        pixelWriter.setColor(x0,y0, Color.DARKGRAY);

        if(deltax>deltay){
            fraction = deltay - (deltax >> 1);
        }
        else{
            fraction = deltax >>1;
        }
        while (x0 != x1){
            if(fraction >=0){
                y0 += ystep;
                fraction -= deltax;
                x0 += xstep;
                fraction += deltay;
                pixelWriter.setColor(x0, y0, Color.DARKGRAY);
            }
            else{
                fraction = deltax - (deltay >>1);
                while (y0 != y1){
                    if (fraction >= 0){
                        x0 += xstep;
                        fraction -= deltay;
                    }
                    y0 += ystep;
                    fraction += deltax;
                    pixelWriter.setColor(x0,y0, Color.DARKGRAY);
                }
            }

        }

    }




}
