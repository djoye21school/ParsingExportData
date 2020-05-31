import edu.duke.*;
import org.apache.commons.csv.*;
/**
 * Write a description of class Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String country = "Nauru";
        String item1 = "cocoa";
        String item2 = "flowers";
        String amount = "$999,999,999,999";
        //countryInfo(parser, country);
        //listExportersTwoProducts(parser, item1, item2);
        //System.out.println(numberOfExporters(parser, item1));
        bigExporters(parser,amount);
    }
    private void countryInfo(CSVParser parser, String country){
        int flag = 0;
        for(CSVRecord record : parser){
            String arrCountry;
            arrCountry = record.get("Country");
            //System.out.println(arrCountry);
            if (arrCountry.equals(country)){
                System.out.println(record.get("Country") + ": " +
                record.get("Exports") + ": " + record.get("Value (dollars)"));
                flag = 1;
            }
        }
        if (flag == 0)
            System.out.println("NOT FOUND");
    }
    private void listExportersTwoProducts(CSVParser parser, String item1, String item2){
        for(CSVRecord record : parser){
            String export = record.get("Exports");
            if (export.contains(item1) && export.contains(item2))
                System.out.println(record.get("Country"));
            }
    }
    private int numberOfExporters(CSVParser parser, String item){
        int i = 0;
        for (CSVRecord record : parser){
            if (record.get("Exports").contains(item))
                i++;
        }
        return i;
    }
    private void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record : parser){
            if (record.get("Value (dollars)").length() > amount.length())
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
        }
    }
}
