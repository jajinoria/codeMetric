package org.codemetrics.saverTest;

import java.io.File;
import org.codemetrics.cubes.using.DataStoreFactory;
import org.codemetrics.saver.DataWereHouseSaver;
import org.junit.Test;
import org.sumus.dwh.datastore.DataStore;

public class DataWereHouseSaverTest {

    public DataWereHouseSaverTest() {
    }

    @Test
    public void testExecute() {
        DataWereHouseSaver dataWareHouse = new DataWereHouseSaver(getDataStore());
        dataWareHouse.execute(getFile());
    }

    private DataStore getDataStore() {
        DataStoreFactory factory = new DataStoreFactory(getDataWareHouse());
        DataStore dataStore = factory.createDataStore();       
        return dataStore;
    }

    private File getFile() {
        return new File("test/org/codemetrics/testFiles/integerToStringManually");
    }

    private File getDataWareHouse() {
        return new File("../CubeMetrics");
    }
}
