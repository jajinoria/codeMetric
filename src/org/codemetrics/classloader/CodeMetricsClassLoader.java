package org.codemetrics.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class CodeMetricsClassLoader {

    String outPutFolder = "compiledTestFiles/";

    public Class loadFileAsClass(String relativePath) {
        relativePath = relativePath.replace("/", "\\");        
        compileJavaFile(relativePath);
        return loadJavaClass(getFormattedFilePath(relativePath));
    }

    private boolean compileJavaFile(String relativePath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compUnits = fileManager.getJavaFileObjects(relativePath);
        Iterable options = Arrays.asList("-d", outPutFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, options, null, compUnits);
        return task.call();
    }

    private Class loadJavaClass(String parentChildName) throws RuntimeException {
        File file = new File(outPutFolder);
        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            java.lang.ClassLoader loader = new URLClassLoader(urls);
            return loader.loadClass(parentChildName);
        } catch (ClassNotFoundException | MalformedURLException ex) {
            Logger.getLogger(CodeMetricsClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException(" There was a problema with custom ClassLoader");
    }

    private String getFormattedFilePath(String path) {
        String finalPath = path.replace("\\", ".");
        finalPath = finalPath.substring(finalPath.indexOf(".") + 1, finalPath.lastIndexOf("."));
        return finalPath;
    }
}
