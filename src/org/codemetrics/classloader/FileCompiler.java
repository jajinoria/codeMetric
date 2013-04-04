
package org.codemetrics.classloader;

import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 *
 * @author Johanna
 */
public class FileCompiler {

    public boolean compileJavaFile(String absoluteFilePath, String outPutFolder) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> compUnits = fileManager.getJavaFileObjects(absoluteFilePath);
        Iterable options = Arrays.asList("-d", outPutFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, null, options, null, compUnits);
        return task.call();
    }
   
    
}
