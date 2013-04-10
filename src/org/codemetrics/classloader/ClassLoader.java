
package org.codemetrics.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Johanna
 */
public class ClassLoader {

    public Class loadJavaClass(String outPutFolder, String classPath) {
        File file = new File(outPutFolder);
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            java.lang.ClassLoader loader = new URLClassLoader(urls);
            return loader.loadClass(classPath);
        } catch (ClassNotFoundException | MalformedURLException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException(" There was a problema with custom ClassLoader");
    }
    
}
