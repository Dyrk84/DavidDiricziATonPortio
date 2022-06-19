import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MethodsForTests {

    public static List<String> fileReader(String filePath) {
        String myFileContent = "";
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                myFileContent = myFileContent + myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException exceptionFromFileReader) {
            System.out.println("exception in listFromFile(): " + exceptionFromFileReader);
        }

        String[] strArray = myFileContent.split(",");
        List<String> listFromFileReader = new ArrayList<>();
        for (int i = 0; i < strArray.length; i++) {
            listFromFileReader.add(strArray[i].trim());
        }
        return listFromFileReader;
    }
}
