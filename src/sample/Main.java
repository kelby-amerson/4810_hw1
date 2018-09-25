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
        Scene s = new Scene(root, 500, 500, Color.WHITE);
        final Canvas canvas = new Canvas(500,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //for loop to write each line
        for(int i=0; i<numOfLines;i++) {

            //random number generator
            int x0, x1, y0, y1;
            int deltax, deltay;

            Random randomNumGen = new Random();
            x0 = randomNumGen.nextInt(500);
            x1 = randomNumGen.nextInt(500);
            y0 = randomNumGen.nextInt(500);
            y1 = randomNumGen.nextInt(500);

            //206, 241 to 100, 285

            pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();

            //writes and times the BasicAlgorithm Method
            long startTime = System.nanoTime();
            //BasicAlgorithm(x0, y0, x1, y1);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("Basic Number " +i+ ": "+duration);
            System.out.println(x0+", "+y0+" to "+x1+", "+y1);


            //writes and times the Bresenham's Algorithm Method
            startTime = System.nanoTime();
            BresenhamAlg(x0, y0, x1, y1);
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
                pixelWriter.setColor(tempx, tempy, Color.BLACK);
            }
        }
        if(deltax == 0){
            for (int i = 0; i <= (deltay-1); i++) {
                tempx = x0;
                tempy = i + y0;
                pixelWriter.setColor(tempx, tempy, Color.BLACK);
            }
        }
        else if(deltay == 0){
            for (int i = 0; i <= (deltax - 1); i++) {
                tempx = x0+i;
                tempy = y0;
                pixelWriter.setColor(tempx, tempy, Color.BLACK);
            }
        }
    }



    public void BresenhamAlg(int x0, int y0, int x1, int y1){
        //straight from https://en.wikipedia.org/wiki/Bresenham's_line_algorithm
        //and https://rosettacode.org/wiki/Bitmap/Bresenham%27s_line_algorithm#Java
        int deltax = Math.abs(x1-x0);
        int deltay = Math.abs(y1-y0);
        int tempx = x0;
        int tempy = y0;
        int xincrement = x0<x1 ? 1 : -1;
        int yincrement = y0<y1 ? 1 : -1;
        int e=0;
        int deltax2 = 2*deltax;
        int deltay2 = 2*deltay;

        //vertical line
        if(deltax==0){
            for(int i=y0;i<y1;i++){
                pixelWriter.setColor(tempx,i,Color.BLUE);
            }
            return;
        }
        //horizontal line
        if (deltay == 0) {
            for (int i=x0;i<x1;i++){
                pixelWriter.setColor(i,tempy,Color.BLUE);
            }
            return;
        }

        if(deltax >= deltay) {
            while(true) {
                pixelWriter.setColor(tempx, tempy, Color.BLUE);
                if(tempx==x1)
                    break;
                tempx += xincrement;
                e += deltay2;
                if(e > deltax) {
                    tempy += yincrement;
                    e -= deltax2;
                }

            }
        }else {
            while(true) {
                pixelWriter.setColor(tempx,tempy, Color.BLUE);
                if(tempy == y1)
                    break;
                tempy += yincrement;
                e += deltax2;
                if(e> deltay) {
                    tempx += xincrement;
                    e -= deltay2;
                }
            }
        }

    }




}
