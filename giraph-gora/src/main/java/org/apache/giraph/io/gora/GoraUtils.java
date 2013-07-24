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
   * HBase data store name.
   */
  public static final String HBASE_STORE = "hbase";

  /**
   * Attribute handling the specific class to be created.
   */
  protected static Class<? extends DataStore> DATASTORECLASS;

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
                                          CONF);

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
    //if (pDataStoreName.toLowerCase().equals(HBASE_STORE)){
      //return HbaseStore.class;
    //}
    //if (pDataStoreName == "DynamoDB"){
      //  return DynamoDBStore.class;
    //}
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
   * @return  Result containing all results for the query.
   */
  public static <K, T extends Persistent> Result<K, T>
  getRequests(DataStore<K, T> pDataStore, K pStartKey, K pEndKey) {
    Query<K, T> query = pDataStore.newQuery();
    query.setStartKey(pStartKey);
    query.setEndKey(pEndKey);
    return pDataStore.execute(query);
  }
}
