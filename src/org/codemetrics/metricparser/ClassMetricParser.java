package org.codemetrics.metricparser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import org.codemetrics.classloader.CodeMetricsClassLoader;

public class ClassMetricParser {

    public int getNumberOfAttributes(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
        return classToAnalize.getDeclaredFields().length;
    }

    public int getNumberOfMethods(String classFilename) {
        Class classToAnalize = loadClass(classFilename);
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

    private Class loadClass(String classFilename) {
        CodeMetricsClassLoader loader = new CodeMetricsClassLoader();
        return loader.loadFileAsClass(classFilename);
    }
}
