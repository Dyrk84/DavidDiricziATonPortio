import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static MultiValuedMap<Integer, String> multivaluedMapWriterForAccountHandling(int numberOfTestDataRow) {
        MultiValuedMap<Integer, String> multivaluedMap = new ArrayListValuedHashMap<>();
        for (int i = 0; i < numberOfTestDataRow; i++) {
            multivaluedMap.putAll(i + 1, Arrays.asList("testName" + (i + 1), "passwordForTest" + (i + 1), "testemail" + (i + 1) + "@address.com", "test description text with number " + (i + 1)));
        }
        return multivaluedMap;
    }

    public static void MapToFiles(String filePath, MultiValuedMap<Integer, String> multiValuedMap) {
        try {
            FileWriter accountHandlerFile = new FileWriter(filePath, true);
            for (int i = 1; i < multiValuedMap.size() / 4 + 1; i++) {
                String[] a = multiValuedMap.get(i).toArray(new String[4]);
                accountHandlerFile.write(a[0] + "," + a[1] + "," + a[2] + "," + a[3] + "," + "\n");
            }
            accountHandlerFile.close();
        } catch (Exception e) {
            System.out.println("Record not saved" + e);
        }
    }

    public static List<List<String>> fromFileToStringList(String filePath) {
        List<List<String>> listForAccountHandling = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("files/multivaluedMapForAccountHandlingInFile.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                listForAccountHandling.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return listForAccountHandling;
    }

    public static void deleteFile(String filePath) {
        File myObj = new File(filePath);
        if (myObj.exists() && myObj.isFile()) {
            myObj.delete();
        }
    }
}
