import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;

/**
 * Write a description of class Part2_temperature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2_temperature {
    private CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord memo = null;
        double cur;
        for(CSVRecord record : parser){
            cur = Double.parseDouble(record.get("TemperatureF"));
            if (memo != null && cur > -9999){
                 if (cur < Double.parseDouble(memo.get("TemperatureF")))
                    memo = record;
            }
            else if(memo == null && cur > -9999)
                    memo = record;
        }
        return memo;
    }
    private String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord tmp = null;
        File file = null;
        Double min = 0.0;
        Double curr_temp;
        String result = "";
        int flag = 0;
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            tmp = coldestHourInFile(fr.getCSVParser());
            curr_temp = Double.parseDouble(tmp.get("TemperatureF"));
            if (flag == 0 || (flag == 1 && min > curr_temp)){
                flag = 1;
                min = curr_temp;
                file = f;
            }
        }
        result += "Coldest day was in file " + file.getName() + "\n";
        result += "Coldest temperature on that day was " + Double.toString(min) + "\n";
        result += "All the Temperatures on the coldest day were:\n";
        FileResource fr = new FileResource(file);
        CSVParser parser = fr.getCSVParser();
        for(CSVRecord record : parser){
            result += record.get("DateUTC") + ": " + record.get("TemperatureF") + "\n";
        }
    return result;
    }
    private CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord memo = null;
        double cur;
        for(CSVRecord record : parser){
            if (!record.get("Humidity").equals("N/A")){
                cur = Double.parseDouble(record.get("Humidity"));
                if (memo == null || cur < Double.parseDouble(memo.get("Humidity")))
                    memo = record;
            }
        }
        return memo;
    }
    private CSVRecord lowestHumidityInManyFiles(){
        CSVRecord result = null;
        CSVRecord tmp = null;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            tmp = lowestHumidityInFile(fr.getCSVParser());
            if (result == null || Double.parseDouble(tmp.get("Humidity")) < 
            Double.parseDouble(result.get("Humidity")))
                result = tmp;
        }
        return result;
    }
    private double averageTemperatureInFile(CSVParser parser){
        double sum = 0.0;
        int count = 0;
        for(CSVRecord record : parser){
            count++;
            sum += Double.parseDouble(record.get("TemperatureF"));
        }
        return sum / count;
    }
    private double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        int count = 0;
        double sum = 0.0;
        for (CSVRecord record : parser){
            if (!record.get("Humidity").equals("N/A") && !record.get("TemperatureF").equals("-9999") &&
            Integer.parseInt(record.get("Humidity")) >= value){
                count++;
                sum += Double.parseDouble(record.get("TemperatureF"));
            }
        }
        return sum / count;
    }
        public void testFileWithColdestTemperature(){
        System.out.println(fileWithColdestTemperature());
    }
    public void testLowestHumidityInFile (){
        CSVRecord tmp = null;
        FileResource fr = new FileResource();
        tmp = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("Lowest Humidity was " + tmp.get("Humidity") + 
        " at " + tmp.get("DateUTC"));
    }
    public void testLowestHumidityInManyFiles(){
        CSVRecord tmp = null;
        tmp = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + tmp.get("Humidity") + 
        " at " + tmp.get("DateUTC"));
    }
    public void testAverageTemperatureInFile(){
        double tmp;
        FileResource fr = new FileResource();
        tmp = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average temperature in file is " + Double.toString(tmp));
    }
    public void testAverageTemperatureWithHighHumidityInFile(){
        double tmp;
        int value = 80;
        FileResource fr = new FileResource();
        tmp = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), value);
        if (tmp != tmp)
            System.out.println("No temperatures with that humidity");
        else
            System.out.println("Average Temp when high Humidity is " + Double.toString(tmp));
       
            
    }
}
