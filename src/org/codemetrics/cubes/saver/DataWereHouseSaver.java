package org.codemetrics.cubes.saver;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.cubes.definition.CubeDefinition;
import org.codemetrics.cubes.definition.DataStoreDefinition;
import org.codemetrics.cubes.definition.DimensionDefinition;
import org.codemetrics.metricparser.ClassMetricParser;
import org.codemetrics.metricparser.PackageMetricParser;
import org.sumus.dwh.cube.Cube;
import org.sumus.dwh.datastore.Context;
import org.sumus.dwh.datastore.DataStore;
import org.sumus.dwh.datastore.DataStoreException;
import org.sumus.dwh.datastore.Entity;
import org.sumus.dwh.datastore.State;
import org.sumus.dwh.datastore.Tuple;
import org.sumus.dwh.dimension.Dimension;

/**
 *
 * @author Jose
 */
public class DataWereHouseSaver {

    private final DataStore dataStore;
    private File sourceFolder;

    public DataWereHouseSaver(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public void execute(File sourceFolder) {
        this.sourceFolder = sourceFolder;
        insertPackageEntity();
        insertClassEntity();
        insertMethodEntity();
        insertTuples();
        try {
            this.dataStore.save();
        } catch (DataStoreException ex) {
            Logger.getLogger(DataWereHouseSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertPackageEntity() {
        Entity component = new Entity("org.core", getDimension(DataStoreDefinition.MODULES));
        component.addFeature(DataStoreDefinition.NAME.getName(), "org.core");
        component.addFeature(DataStoreDefinition.TYPE.getName(), "Package");
        add(component);
    }

    private void insertClassEntity() {
        Entity component = new Entity("org.core.Parser", getDimension(DataStoreDefinition.MODULES));
        component.addFeature(DataStoreDefinition.NAME.getName(), "org.core.Parser");
        component.addFeature(DataStoreDefinition.TYPE.getName(), "Class");
        add(component);
    }

    private void insertMethodEntity() {
        Entity component = new Entity("org.core.Parser.execute", getDimension(DataStoreDefinition.MODULES));
        component.addFeature(DataStoreDefinition.NAME.getName(), "org.core.Parser.execute");
        component.addFeature(DataStoreDefinition.TYPE.getName(), "Method");
        add(component);
    }

    private void insertTuples() {        
        buildPackageState();
        buildClassesState();
        buildMethodsState();
    }
    
    
        

    private Cube getCube(CubeDefinition definition) {
        return getDataStore().getCube(definition.getName());
    }

      
     private Dimension getDimension(DimensionDefinition definition) {
        return getDataStore().getDimension(definition.getName());
    }

    private void add(Entity entity) {
        getDataStore().insert(entity);
    }

    private void add(Tuple tuple) {
        getDataStore().insert(tuple);
    }

   

    
    private void buildPackageState() {
        State state = new State();
        MetricOfPackage packageMetricMethods = new MetricOfPackage();
        Context context = new Context(new Date());
        context.put(DataStoreDefinition.MODULES.getName(), this.sourceFolder.getName());
        state.put(DataStoreDefinition.CLASSES.getName(), packageMetricMethods.totalOfClasses(this.sourceFolder));
        state.put(DataStoreDefinition.LINES_OF_CODE.getName(),packageMetricMethods.totalLinesOfCode(this.sourceFolder));
        state.put(DataStoreDefinition.EMPTY_LINES_OF_CODE.getName(), packageMetricMethods.emptyLinesOfCode(this.sourceFolder));
        state.put(DataStoreDefinition.EFFECTIVE_LINES_OF_CODE.getName(), packageMetricMethods.effectiveLinesOfCode(this.sourceFolder));
        state.put(DataStoreDefinition.COMMENT_LINES_OF_CODE.getName(), packageMetricMethods.commentLinesOfCode(this.sourceFolder));
        Tuple factShip = new Tuple(getCube(DataStoreDefinition.PACKAGE_CUBE), context, state);
        add(factShip);  
       
    }

    
    private void buildClassesState() {
        PackageMetricParser packageParser = new PackageMetricParser();
        MetricOfClasses metricOfClasses = new MetricOfClasses();
        State state = new State();
        for (String path : packageParser.getPaths(this.sourceFolder.toString())) {
            Context context = new Context (new Date());
            context.put(DataStoreDefinition.MODULES.getName(), metricOfClasses.getFile(path).getName());
            state.put(DataStoreDefinition.LINES_OF_CODE.getName(), metricOfClasses.codeLinesOfClasses(path));
            state.put(DataStoreDefinition.EFFERENT_COUPLING_LIBRARY.getName(), metricOfClasses.numberOfImports(path));
            state.put(DataStoreDefinition.LACK_OF_COHESION_OF_METHODS.getName(),metricOfClasses.lackOfCohesion(path));
            state.put(DataStoreDefinition.COMMENT_LINES_OF_CODE.getName(), metricOfClasses.commentCodeLinesOfClasses(path));
            state.put(DataStoreDefinition.EMPTY_LINES_OF_CODE.getName(), metricOfClasses.emptyCodeLinesOfClasses(path));
            state.put(DataStoreDefinition.EFFECTIVE_LINES_OF_CODE.getName(), metricOfClasses.effectiveCodeLinesOfClasses(path));
            Tuple factShip = new Tuple(getCube(DataStoreDefinition.CLASS_CUBE), context, state);
            add(factShip);            
        }       
    }
       
    
    private void buildMethodsState() {
        PackageMetricParser packageParser = new PackageMetricParser();
        MetricOfMethods metricOfMethods = new MetricOfMethods();
        State state = new State();
        for (String path : packageParser.getPaths(this.sourceFolder.toString())) {
            ClassMetricParser metricParser = new ClassMetricParser(path);
            Method[] methods = metricParser.getMethods();
            for (Method method : methods) {
                Context context = new Context(new Date());
                context.put(DataStoreDefinition.MODULES.getName(), method.getName());
                state.put(DataStoreDefinition.CYCLOMATIC_COMPLEXITY.getName(), metricOfMethods.CyclomaticComplexity(getFile(path), method));
                state.put(DataStoreDefinition.PARAMETERS.getName(), metricOfMethods.NumberOfParameters(method));
                Tuple factShip = new Tuple(getCube(DataStoreDefinition.METHOD_CUBE), context, state);
                add(factShip);
            }

        }


    }
      private File getFile(String filesource) {
        return new File(filesource);
    }
    
   
}
