package org.codemetrics.metricparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassMetricParser {

    public int getNumberOfAttributes(Class classToAnalize) {
        return classToAnalize.getDeclaredFields().length;
    }

    public int getNumberOfMethods(Class classToAnalize) {
        return classToAnalize.getDeclaredMethods().length;
    }

    public int getNumberOfImports(String classFilename) {
        try {
            Scanner in = new Scanner(new FileReader(classFilename));
            String text = "";
            while (in.hasNext()) {
                text += in.nextLine();
            }
            
            int index = text.indexOf("import");
            int count = 0;
            while (index != -1) {
                count++;
                text = text.substring(index + 1);
                index = text.indexOf("import");
            }

            return count;
        } catch (FileNotFoundException ex) {
            
        }
        return 0;
    }
}
