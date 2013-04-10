package org.codemetrics.metricparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import org.codemetrics.classloader.FileCompiler;

public class ClassMetricParser {

   // public int getNumberOfAttributes(String classFilename) {
       // Class classToAnalize = loadClass(classFilename);
       // return classToAnalize.getDeclaredFields().length;
   // }

    //public int getNumberOfMethods(String classFilename) {
      //  return classToAnalize.getDeclaredMethods().length;
   // }

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

   /* private void initializeMethods() {
        

        if (compiler.compileJavaFile(getFile().getAbsolutePath(), outPutFolder)) {
            Class loadedClass = (new org.codemetrics.classloader.ClassLoader()).loadJavaClass(outPutFolder, "integerToStringManually.IntegerToStringManually");
            this.methods = loadedClass.getMethods();
        }
    }

    private File getFile() {
        File file = new File("test/org/codemetrics/testFiles/"
                + "integerToStringManually/IntegerToStringManually.java");
        return file;
    }
    
    private Class loadClass(String filename){
        FileCompiler compiler = new FileCompiler();
        String outPutFolder = "test/org/codemetrics";
    }*/
}
