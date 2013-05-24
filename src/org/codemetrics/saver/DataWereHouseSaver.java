
package org.codemetrics.saver;

import java.io.File;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codemetrics.cubes.definition.CubeDefinition;
import org.codemetrics.cubes.definition.DataStoreDefinition;
import org.codemetrics.cubes.definition.DimensionDefinition;
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
        this.sourceFolder=sourceFolder;
        insertPackageEntity();
        insertClassEntity();
	insertMethodEntity();
        insertPackageTuple();
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

    private void insertPackageTuple() {
        Context context = buildPackageContext();
        State state = buildPackageState();
        Tuple factShip = new Tuple(getCube(DataStoreDefinition.PACKAGE_CUBE), context, state);
        add(factShip);
    }

    private Cube getCube(CubeDefinition definition) {
        return getDataStore().getCube(definition.getName());
    }

    private Context buildPackageContext() {
        Context context = new Context(new Date());
        context.put(DataStoreDefinition.MODULES.getName(), "org.core");
        return context;
    }

    private State buildPackageState() {
        State state = new State();
        state.put(DataStoreDefinition.LINES_OF_CODE.getName(), linesOfCode());
        state.put(DataStoreDefinition.CLASSES.getName(), 5.);
        state.put(DataStoreDefinition.EFFERENT_COUPLING_INTERNAL.getName(), 4.);
        state.put(DataStoreDefinition.EFFERENT_COUPLING_LIBRARY.getName(), 2.);
        state.put(DataStoreDefinition.AFFERENT_COUPLING.getName(), 4.);
        return state;
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
    
    private double linesOfCode(){
        PackageMetricParser packageParser = new PackageMetricParser();
        return packageParser.getCodeLines(this.sourceFolder.getAbsolutePath()).getTotalCodeLines(); 
    }
    
   
}
