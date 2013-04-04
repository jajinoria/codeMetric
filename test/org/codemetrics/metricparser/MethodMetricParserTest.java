
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.junit.Test;

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
        compileJavaFile(); 
        Class loadedClass = loadJavaFile();
        this.methods = loadedClass.getMethods();
    }

    private void compileJavaFile() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compUnits =  fileManager.getJavaFileObjects(getFile());
        Iterable options = Arrays.asList("-d", "C:/Users/Johanna/Documents/NetBeansProjects" );
        
        JavaCompiler.CompilationTask task = compiler.getTask(null, null,
                                                             null, options, null,
                                                             compUnits);
        task.call();
    }
    
    private File getFile() {
        File file = new File("C:/Users/Johanna/Documents/NetBeansProjects/interviews/src/integerToStringManually/IntegerToStringManually.java");
        return file;
    }

    private Class loadJavaFile() {
        File file = new File("C:/Users/Johanna/Documents/NetBeansProjects/");
 
        try
        {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[] { url };
 
            ClassLoader loader = new URLClassLoader(urls);
 
            return loader.loadClass("integerToStringManually.IntegerToStringManually");  
        } catch (ClassNotFoundException | MalformedURLException ex) {
            Logger.getLogger(MethodMetricParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException();
    }
}
