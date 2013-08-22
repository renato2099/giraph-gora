/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.giraph.io.gora;

import java.io.IOException;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.gora.generated.GVertex;
import org.apache.gora.cassandra.store.CassandraStore;
import org.apache.gora.persistency.Persistent;
import org.apache.gora.query.Query;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.gora.util.GoraException;
import org.apache.hadoop.conf.Configuration;

/**
 * Class used to handle the creation and querying of data stores through Gora.
 */
public class GoraUtils {

  /**
   * Cassandra data store name.
   */
  public static final String CASSANDRA_STORE = "cassandra";

  /**
   * Attribute handling the specific class to be created.
   */
  private static Class<? extends DataStore> DATASTORECLASS;

  private static Query query;

  /**
   * Attribute handling configuration for data stores.
   */
  private static Configuration CONF;

  /**
   * The default constructor is set to be private by default so that the
   * class is not instantiated.
   */
  private GoraUtils() { /* private constructor */ }

  /**
   * Creates a generic data store using the data store class.
   * set using the class property
   * @param <K> key class
   * @param <T> value class
   * @param keyClass key class used
   * @param persistentClass persistent class used
   * @return created data store
   * @throws GoraException exception threw
   */
  @SuppressWarnings("unchecked")
  public static <K, T extends Persistent> DataStore<K, T>
  createDataStore(Class<K> keyClass, Class<T> persistentClass)
    throws GoraException {
    DataStoreFactory.createProps();
    DataStore<K, T> dataStore =
        DataStoreFactory.createDataStore((Class<? extends DataStore<K, T>>)
                                          DATASTORECLASS,
                                          keyClass, persistentClass,
                                          getConf());

    return dataStore;
  }

  /**
   * Returns the specific type of class for the requested data store.
   * @param pDataStoreName data store name to be used
   * @return  Class of a specific data store
   */
  private static Class<? extends DataStore> getSpecificDataStore
  (String pDataStoreName) {
    if (pDataStoreName.toLowerCase().equals(CASSANDRA_STORE)) {
      return CassandraStore.class;
    }
    return null;
  }

  /**
   * Creates a specific data store specified by.
   * @param <K> key class
   * @param <T> value class
   * @param pDataStoreType  Defines the type of data store used.
   * @param keyClass  Handles the key class to be used.
   * @param persistentClass Handles the persistent class to be used.
   * @return DataStore created using parameters passed.
   * @throws GoraException  if an error occurs.
   */
  public static <K, T extends Persistent> DataStore<K, T>
  createSpecificDataStore(String pDataStoreType, Class<K> keyClass,
      Class<T> persistentClass) throws GoraException {
    // Getting the specific data store
    DATASTORECLASS = getSpecificDataStore(pDataStoreType);
    return createDataStore(keyClass, persistentClass);
  }

  /**
   * Performs a range query to Gora datastores
   * @param <K> key class
   * @param <T> value class
   * @param pDataStore  data store being used.
   * @param pStartKey start key for the range query.
   * @param pEndKey end key for the range query.
   * @return Result containing all results for the query.
   */
  public static <K, T extends Persistent> Result<K, T>
  getRequest(DataStore<K, T> pDataStore, K pStartKey, K pEndKey) {
    System.out.println("Intentando entrarle con todo a Gora");
    Query<K, T> query = getQuery(pDataStore, pStartKey, pEndKey);
    return getRequest(pDataStore, query);
  }

  /**
   * Performs a query to Gora datastores
   * @param pDataStore data store being used.
   * @param query query executed over data stores.
   * @return Result containing all results for the query.
   */
  //CHECKSTYLE: stop IllegalCatch
  public static <K, T extends Persistent> Result<K, T>
  getRequest(DataStore<K, T> pDataStore, Query<K, T> query) {
    System.out.println("Intentando leer 2");
    try {
      pDataStore= (DataStore<K, T>) createSpecificDataStore(CASSANDRA_STORE,
          String.class,
          GVertex.class);
    } catch (GoraException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int cc = 0;
    Result res = pDataStore.execute(query);
    System.out.println("Finalmente logramos leer objects");
    return res;
  }
  //CHECKSTYLE: resume IllegalCatch

  /**
   * Performs a range query to Gora datastores
   * @param <K> key class
   * @param <T> value class
   * @param pDataStore  data store being used.
   * @param pStartKey start key for the range query.
   * @param pEndKey end key for the range query.
   * @return  Result containing all results for the query.
   */
  public static <K, T extends Persistent> Result<K, T>
  getRequest(DataStore<K, T> pDataStore, K pStartKey) {
    return getRequest(pDataStore, pStartKey, null);
  }

  /*public static GoraInputFormat createGoraInputFormat(Configuration conf,
      Query query, DataStore<Object, Persistent> dataStore,
      boolean reuseObjects){
    GoraInputFormat goraInput = new GoraInputFormat();
    goraInput.setConf(conf);
  }*/

  /**
   * Gets a query object to be used as a range query.
   * @param pDataStore data store used.
   * @param pStartKey range start key.
   * @param pEndKey range end key.
   * @return range query object.
   */
  public static <K, T extends Persistent> Query<K, T>
  getQuery(DataStore<K, T> pDataStore, K pStartKey, K pEndKey) {
    // TODO the data store gets here FUCKED
    try {
      pDataStore= (DataStore<K, T>) createSpecificDataStore(CASSANDRA_STORE,
          String.class,
          GVertex.class);
    } catch (GoraException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Query<K, T> query = pDataStore.newQuery();
    query.setFields("vertexId", "value", "edges");
    query.setStartKey(pStartKey);
    query.setEndKey(pEndKey);
    return query;
  }

  /**
   * Gets a query object to be used as a simple get.
   * @param pDataStore data store used.
   * @param pStartKey range start key.
   * @return query object.
   */
  public static <K, T extends Persistent> Query<K, T>
  getQuery(DataStore<K, T> pDataStore, K pStartKey) {
    Query<K, T> query = pDataStore.newQuery();
    query.setStartKey(pStartKey);
    query.setEndKey(null);
    return query;
  }

  /**
   * @return the cONF
   */
  public static Configuration getConf() {
    return CONF;
  }

  /**
   * @param cONF the cONF to set
   */
  public static void setConf(Configuration cONF) {
    CONF = cONF;
  }
}
