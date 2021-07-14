import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // reading the files
        ReadData cases = new ReadData();

        cases.readArray("COVID-19 Data CSV files/data_2021-Feb-18.csv");
        //cases.setDataArray();

        // instantiating the Frame
        /*JFrame frame = new JFrame();

        // Add the graph
        frame.add(new Graph());
        //frame.add(deaths);

        // frame size
        frame.setSize(400, 400);
        frame.setLocation(200,200);

        frame.setTitle("Data Visualisation");
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);*/
    }

}
