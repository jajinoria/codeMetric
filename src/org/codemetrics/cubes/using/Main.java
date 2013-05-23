
package org.codemetrics.cubes.using;

import java.io.File;
import org.sumus.dwh.datastore.DataStore;
import org.sumus.dwh.datastore.DataStoreException;

public class Main {

    public static void example(String[] args) throws DataStoreException {
        checkArgs(args);
        DataStore dataStore = createDataStore(new File(args[1]));
        MockParser parser = new MockParser(dataStore);
        parser.execute(new File(args[0]));
        dataStore.save();
    }

    private static DataStore createDataStore(File folder) {
        DataStoreFactory factory = new DataStoreFactory(folder);
        DataStore dataStore = factory.createDataStore();
        return dataStore;
    }

    private static void checkArgs(String[] args) {
        checkIsNotNull(args);
        checkSourceExists(new File(args[0]));
    }

    private static void checkSourceExists(File sourceDirectory) {
        if (!sourceDirectory.exists()) {
            throw new RuntimeException("Source path does not exist");
        }
    }

    private static void checkIsNotNull(String[] args) {
        if (args[0] == null || args[1] == null) {
            throw new RuntimeException("Paths not introduced");
        }
    }
}