import java.io.*;
import java.util.*;
import java.lang.Process;
import java.net.*;

public class AttendanceChecker 
{
    public static void main(String[] args) throws IOException 
    {
        String studentFileName = "students.txt";
        String googleDocDownloadLink = "https://docs.google.com/document/u/1/export?format=txt&id=1GPt_AJY5sTg_DsbowLdhyDfGwLPY6QO_myahjTz88nU&token=AC4w5VjQT9OGU5WzLmx5CO3Rb5Ew7SpxFQ%3A1631132910116&includes_info_params=true&inspectorResult=%7B%22pc%22%3A9%2C%22lplc%22%3A3%7D";
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(googleDocDownloadLink).openStream());
        FileOutputStream fileOS = new FileOutputStream(studentFileName)) 
        {
            byte data[] = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) 
            {
                fileOS.write(data, 0, byteContent);
            }
        }
        catch (IOException e) 
        {
            System.out.println("Link doesnt work!!!");
        }    
        File studentFile = new File(studentFileName);
        BufferedReader bufferReader = new BufferedReader(new FileReader(studentFile));
        String line;
        List<String> fullList = new ArrayList<>();
        List<String> firstNameList = new ArrayList<>();
        List<String> lastNameList = new ArrayList<>();
        List<String> responseList = new ArrayList<>();
        while ((line = bufferReader.readLine())!=null)
            fullList.add(line);
        fullList.subList(0,fullList.indexOf("***** START ATTENDANCE HERE *****")).clear();
        fullList = fullList.subList(1,fullList.size());
        for (String fullListLine: fullList)
        {
            String[] fullListLineItems = fullListLine.split(",");
            try
            {
            firstNameList.add(fullListLineItems[0]);
            lastNameList.add(fullListLineItems[1]);
            responseList.add(fullListLineItems[2]);
            }
            catch(Exception e)
            {
                //This is to show if they didnt put attendance in correctly
                continue;
            }
        }
        bufferReader.close(); 
        // Testing Code AREA //
        int counterStudents = 0;
        for (String counterString: fullList)
        {
            counterStudents++;
        }
        System.out.println(counterStudents + " Students have put in their attendance");
        //List<Integer> whoDidntList = whoDidntAnswerCorrectly(fullList);
        //if (whoDidntList.size() == 1)
        //{
        //    System.out.println("Student at line " + whoDidntList + " did not format their attendance properly!!!");
        //}
        //else if(whoDidntList.size() > 1)
        //{
        //    System.out.println("Students at lines " + whoDidntList + " did not format their attendance properly!!!");
        //}
        System.out.println("The random student choosen is: "+getRandomStudent(fullList));
        //System.out.println(fullList);
    }

    public static List<Integer> whoDidntAnswerCorrectly(List<String> inputlist)
    {
        int counter = 0;
        List<Integer> returnList=  new ArrayList<>();;
        for (String line : inputlist)
        {
            String[] lineCheckerArray = line.split(",");
            if (lineCheckerArray.length < 3)
            {
                returnList.add(counter+1);
            }
            counter = counter +1;
        }
        return returnList;
    }
    public static String getRandomStudent(List<String> list)
    {
        Random random = new Random();
        if (list.size() == 0)
        {
            return "There are no students";
        }
        String stringTemp = "";
        String[] listTemp= list.get(random.nextInt(list.size())).split(",");
        try{
        stringTemp = listTemp[0]+ " "+ listTemp[1];
        }
        catch(Exception e){
        stringTemp = listTemp[0];
        }
        return stringTemp;
    }
}
