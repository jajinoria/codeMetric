
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import org.codemetrics.classloader.FileCompiler;
import org.junit.Test;
import org.codemetrics.classloader.ClassLoader;

/**
 *
 * @author Johanna
 */
public class MethodMetricParserTest {
    
    private Method[] methods;
    
    @Test
    public void testSomeMethod() {
        MethodMetricParser methodParser = new MethodMetricParser();
        initializeMethods();
        System.out.println(methodParser.getCodeLines(getFile(), methods[0]).getEffectiveCodeLines());
    }
    
    private void initializeMethods(){
        FileCompiler compiler = new FileCompiler();
        String outPutFolder = "C:/Users/Johanna/Documents/NetBeansProjects";  
        

        if(
          compiler.compileJavaFile(getFile().getAbsolutePath(), outPutFolder) ){
          Class loadedClass = (new ClassLoader()).loadJavaClass(outPutFolder, "integerToStringManually.IntegerToStringManually");
          this.methods = loadedClass.getMethods();
        }
    }
    
    private File getFile() {
        File file = new File("C:/Users/Johanna/Documents/NetBeansProjects/interviews/src/integerToStringManually/IntegerToStringManually.java");
        return file;
    }
}
