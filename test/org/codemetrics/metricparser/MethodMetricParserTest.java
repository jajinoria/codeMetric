/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.codemetrics.metricparser;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Daniel & Jose & Johanna
 */
public class MethodMetricParserTest {
    
    private ArrayList<Method> methods;
    
    @Before
    public void initialize(){
        getMethods();
    }

    @Test
    public void testGetNumberOfParameters() {
        //TODO: Jose te toca implementarlo a ti
    }

    @Test
    public void testGetCodeLines() {
        MethodMetricParser methodMetricParser = new MethodMetricParser();
        methodMetricParser.getCodeLines(getFile(), this.methods.get(0) ).getEffectiveCodeLines();
        System.out.println();
    }
    
    private File getFile(){
        return 
        new File("C:/Users/usuario/Documents/NetBeansProjects/CodeMetrics/src/org/codemetrics/metricparser/MethodMetricParser.java");
    }

    private void getMethods() {
        try {
            this.methods = new ArrayList<>();
            
            // Compile source file.
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, getFile().getPath());

           // Load and instantiate compiled class.
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { getFile().toURI().toURL() });
            Class<?> cls = Class.forName("metricparser.MethodMetricParser", true, classLoader);
            Object instance = cls.newInstance(); 
            System.out.println(instance);  
            
        } catch (InstantiationException ex) {
            Logger.getLogger(MethodMetricParserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MethodMetricParserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MethodMetricParserTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(MethodMetricParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
