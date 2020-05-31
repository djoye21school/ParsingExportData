import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
/**
 * Write a description of class ParsingName here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ParsingName {
    private void totalBirths(FileResource fr){
        int countBoys = 0;
        int countGirls = 0;
        for (CSVRecord r : fr.getCSVParser(false)){
            if (r.get(1).equals("M"))
                countBoys++;
            if (r.get(1).equals("F"))
                countGirls++;
        }
        System.out.println("boys = " + countBoys);
        System.out.println("girls = " + countGirls);
        System.out.println("total = " + (countBoys + countGirls));
    }
    private int getRank(int year, String name, String gender){
        int count = 0;
        int flag = 0;
        FileResource fr = new FileResource("us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        for(CSVRecord r : fr.getCSVParser(false)){
            if (r.get(1).equals(gender)){
                count++;
                if (r.get(0).equals(name)){
                    flag = 1;
                    break;
                }
            }
        }
        return flag == 1 ? count : -1;
    }
    private String getName(int year, int rank, String gender){
        int i = 0;
        CSVRecord mem = null;
        FileResource fr = new FileResource("us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        for(CSVRecord r : fr.getCSVParser(false)){
            if(r.get(1).equals(gender))
                i++;
            mem = r;
            if(i == rank)
                break;
        }
        return i == rank ? mem.get(0) : "NO NAME";
    }
    private void whatIsNameInYear(String name, int year, int newYear, String gender){
        int rank = getRank(year, name, gender);
        String name2 = getName(newYear, rank, gender);
        System.out.println(name + " born in "+ Integer.toString(year) + " would be "+ name2 + " if " +
        (gender.equals("F") ? "she" : "he") + " was born in " + Integer.toString(newYear) + ".");
    }
    private int yearOfHighestRank(String name, String gender){
        int result = -1;
        int tmpRank = -1;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            int year = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(year, name, gender);
            if (currRank != -1 && (tmpRank == -1  || currRank < tmpRank)){
                tmpRank = currRank;
                result = year;
            }
        }
        return result;
    }
    private double getAverageRank(String name, String gender){
        int sum = 0;
        int count = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            int year = Integer.parseInt(f.getName().substring(3, 7));
            int currRank = getRank(year, name, gender);
            if (currRank != -1){
                count++;
                sum += currRank;
            }
        }
        double result = ((double) sum) / count;
        return result != 0 ? result : -1;
    }
    private int getTotalBirthsRankedHigher(int year, String name, String gender){
        int sum = 0;
        FileResource fr = new FileResource("us_babynames_by_year/yob" + Integer.toString(year) + ".csv");
        for(CSVRecord r : fr.getCSVParser(false)){
            if(r.get(1).equals(gender)){
                if (r.get(0).equals(name))
                    break;
                sum += Integer.parseInt(r.get(2));
            }
        }
        return sum;
    }
    public void testTotalBirths(){
        //FileResource fr = new FileResource();
        //totalBirths(fr);
        int year = 1990;
        int newYear = 2014;
        String name = "Drew";
        String gender = "M";
        int rank = 450;
        //System.out.println(getRank(year, name, gender));
        //System.out.println(getName(year, rank, gender));
        //whatIsNameInYear(name, year, newYear, gender);
        //System.out.println(yearOfHighestRank (name, gender));
        //System.out.println(getAverageRank(name, gender));
        System.out.println(getTotalBirthsRankedHigher(year, name, gender));
    }
}
