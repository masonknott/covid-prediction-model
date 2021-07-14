import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;

public class ReadData extends Application{

    private String[][] array;   // reading all the rows and columns from the csv to
    private int[][] dataArray;  // storing the date and cases

    public void readArray(String fileName) throws IOException {
        Scanner input = new Scanner(new File(fileName));

        //check how many rows in the csv files
        int countLines = 0;
        while (input.hasNextLine()) {
            countLines++;
            input.nextLine();
        }

        input.close();
        input = new Scanner(new File(fileName));

        int y = input.nextLine().split(",").length;
        input.close();
        input = new Scanner(new File(fileName));

        // columns and rows in the csv files, sets the array
        array = new String [countLines][y];
        int i = 0;
        String temp;
        String[] line;

        // this reads each line of the csv into the array
        while (input.hasNextLine()) {
            temp = input.nextLine();
            line = temp.split(",");
            array[i][0] =line[0];
            array[i][1] = line[1];
            array[i][2] = line[2];
            array[i][3] = line[3];
            array[i][4] = line[4];
            array[i][5] = line[5];
            i++;
        }
    }

    // set the length of dataArray
    // set the cumCasesBySpecimenDate column of 'array' as the first column of 'dataArray'
    /* take the date column of 'array' make it into a date object then assign the month of that
        date object as the second column of 'dataArray'*/
    public int[][] setDataArray(){

        // make the dataArray the same length as array excluding the first row
        dataArray = new int[(array.length-1)][2];
        int b  = 0;
        int date_column = 0;
        int cases_column = 0;

        // checks which column the date and the cases are stored in
        for (int i = 0; i < array[0].length; i++){
            String temp = array[0][i];
            if(Objects.equals(temp, "date")){
                date_column = i;
            }
            if(Objects.equals(temp, "cumCasesBySpecimenDate")) {
                cases_column = i;
            }
        }

        // checks the number of columns in array, no longer needed with the .csv files used, but no need to delete
        for (int a = 1; a < array.length; a++) {
            dataArray[b][1] = Integer.parseInt(array[a][cases_column]);

            int[] dateList = Date.Y1_toDate(array[a][date_column]);
            Date myDate = new Date(dateList[2], dateList[1], dateList[0]);

            // storing the month value from the Date object
            dataArray[b][0] = myDate.getMonth();
            b++;
        }

        // reversing the array so the earlier dates are at the top
        for(int i = 0; i < dataArray.length / 2; i++) {
            int temp[] = dataArray[i];
            dataArray[i] = dataArray[dataArray.length - i - 1];
            dataArray[dataArray.length - i - 1] = temp;
        }
        return dataArray;
    }

    //testing the array works
    public void printArray(){
        for(int i = 0; i<dataArray.length; i++) {
            System.out.print(dataArray[i][0]+":"+dataArray[i][1]+"\n");
        }
    }

    /*@Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Team 12");

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel("Number of cases");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number, Number>(xAxis,yAxis);

        ReadData cases = new ReadData();

        // storing the different filenames
        String[] filename =
                {"COVID-19 Data CSV files/Cumulativedeaths4March.csv","COVID-19 Data CSV files/data_2021-Feb-18.csv",
                        "COVID-19 Data CSV files/dailycases4March.csv","COVID-19 Data CSV files/dailydeaths4March.csv",};
        cases.readArray(filename[3]);
        int[][] data = cases.setDataArray();

        int[] data2 = new int[data.length];// counting the length of the data

        // filling data2 with its row number
        for (int a = 0; a < data2.length; a++) {
            data2[a] = a;}

        lineChart.setTitle("COVID-19 cases in the UK in 2020");

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("case");

        //populating the series with data, looping through the array and assigning the X and Y values
        for (int i = 0; i < data.length; i++) {
            series.getData().add(new XYChart.Data(data2[i], data[i][1]));
        }

        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }*/

    // method to create a chart, to create different charts
    private LineChart createChart(String title, String yLabel, String filename, String plot) throws IOException {

        //defining the axis
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel(yLabel);

        LineChart <Number,Number> chart =
                new LineChart<Number, Number>(xAxis,yAxis);

        ReadData cases = new ReadData();
        cases.readArray(filename);

        int[][] data = cases.setDataArray(); // receives the data from the .csv
        int[] data2 = new int[data.length];// counting the length of the data

        // filling data2 with its row number to use as day
        for (int a = 0; a < data2.length; a++) {
            data2[a] = a;}

        chart.setTitle(title);
        XYChart.Series series = new XYChart.Series();
        series.setName(plot);

        //populating the series with data, looping through the array and assigning the X and Y values
        for (int i = 0; i < data.length; i++) {
            series.getData().add(new XYChart.Data(data2[i], data[i][1]));
        }

        chart.getData().add(series);
        return chart;
    }

    @Override
    public void start(Stage stage) throws IOException {
        TabPane tabPane = new TabPane();

        // the different charts
        LineChart  cumDeathChart = createChart("Cumulative COVID-19 deaths in the United Kingdom", "Number of deaths",
                "COVID-19 Data CSV files/Cumulativedeaths4March.csv","deaths");
        LineChart  dailyDeathChart = createChart("Daily COVID-19 deaths in the United Kingdom", "Number of deaths",
                "COVID-19 Data CSV files/dailydeaths4March.csv","deaths");
        LineChart  cumCasesChart = createChart("Cumulative COVID-19 cases in the United Kingdom", "Number of cases",
                "COVID-19 Data CSV files/data_2021-Feb-18.csv","cases");
        LineChart  DailyCasesChart = createChart("Daily COVID-19 cases in the UK in 2020", "Number of cases",
                "COVID-19 Data CSV files/dailycases4March.csv","cases");

        Tab cumDeaths = new Tab("Total Covid Death", cumDeathChart);
        Tab dailyDeaths = new Tab("Daily Covid Deaths", dailyDeathChart);
        Tab cumCases = new Tab("Cumulative Covid Cases", cumCasesChart);
        Tab dailyCases = new Tab("Daily covid cases",DailyCasesChart);

        tabPane.getTabs().add(cumDeaths);
        tabPane.getTabs().add(dailyDeaths);
        tabPane.getTabs().add(cumCases);
        tabPane.getTabs().add(dailyCases);

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox, 900,500);

        stage.setScene(scene);
        stage.setTitle("Team 12");

        stage.show();
    }

    //Date object for storing our date
    public static class Date {
        int year = 0000;
        int month = 00;
        int day = 00;

        public Date(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
        // date is formatted DD-MM-YYYY
        public static int[] toDate(String A) {
            int[] date = new int[3];
            String[] YMD = A.split("/");
            date[0] = Integer.parseInt(YMD[0]);
            date[1] = Integer.parseInt(YMD[1]);
            date[2] = Integer.parseInt(YMD[2]);

            return date;
        }
        // allows for use of different type of csv where the date is formatted YYYY-MM-DD
        public static int[] Y1_toDate(String A) {
            int[] date = new int[3];
            String[] YMD = A.split("-");
            date[0] = Integer.parseInt(YMD[2]);
            date[1] = Integer.parseInt(YMD[1]);
            date[2] = Integer.parseInt(YMD[0]);

            return date;
        }

        public int getYear(){ return year; }
        public int getMonth(){ return month; }
        public int getDay(){ return day; }

        public String toString() { return (day+" "+month+" "+year); }
        public float day_month(){
            return Float.parseFloat(month+"."+day);
        }
        public int longDate(){
            return Integer.valueOf(String.valueOf(month)+String.valueOf(day));
        }

        //trying to fix the issue where the
        public static String byPaddingZeros(int value, int paddingLength) {
            return String.format("%0" + paddingLength + "d", value);
        }
    }
}
