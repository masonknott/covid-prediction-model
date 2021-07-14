import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.io.IOException;

// Completely copied and pasted from here - https://www.codespeedy.com/plot-graph-in-java/.
// https://kodejava.org/how-do-i-draw-plot-a-graph/
// need to properly understand so we can use something similar for our data.

public class Graph extends JPanel{
        int[] coordinates = {100,10};
        int mar = 50;
        ReadData cases = new ReadData();

        /*protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g1 = (Graphics2D)g;
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();


            g1.draw(new Line2D.Double(mar,mar,mar,height-mar));
            g1.draw(new Line2D.Double(mar,height-mar,width-mar,height-mar));

            double x = (double)(width - 2 * mar)/(coordinates.length - 1);
            double scale=(double)(height - 2 * mar)/getMax();
            g1.setPaint(Color.BLUE);

            for(int i = 0; i < coordinates.length; i++){
                double x1 = mar + i * x;
                double y1 = height- mar -scale * coordinates[i];
                g1.fill(new Ellipse2D.Double(x1-2,y1-2,4,4));
            }
        }*/
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g1 = (Graphics2D)g;
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();

            try {
                cases.readArray("");
            } catch (IOException e) {
                e.printStackTrace();
            }
            int[][]data = cases.setDataArray();

            g1.draw(new Line2D.Double(mar,mar,mar,height-mar));
            g1.draw(new Line2D.Double(mar,height-mar,width-mar,height-mar));

            double x = (double)(width - 2 * mar)/(data.length - 1);
            double scale=(double)(height - 2 * mar)/getMax(data);
            g1.setPaint(Color.BLUE);


            for(int i = 0; i < data.length; i++){
                double x1 = mar + i * x;
                double y1 = height- mar -scale * data[i][1];
                g1.fill(new Ellipse2D.Double(x1-2,y1-2,4,4));
            }
        }

        private int getMax( int [][] data){
            int max=-Integer.MAX_VALUE;
            for(int i = 0; i < data.length; i++){
                if(data[i][1] > max)
                    max=data[i][1];
            }return max;
        }



}
