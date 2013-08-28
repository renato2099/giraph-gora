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
import java.util.ArrayList;
import java.util.List;

import org.apache.gora.mapreduce.GoraInputSplit;
import org.apache.gora.mapreduce.GoraRecordReader;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.query.PartitionQuery;
import org.apache.gora.query.Query;
import org.apache.gora.store.DataStore;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * {@link InputFormat} to fetch the input from Gora data stores. The
 * query to fetch the items from the datastore should be prepared and
 * set via {@link #setQuery(Job, Query)}, before submitting the job.
 *
 * Hadoop jobs can be either configured through static
 * <code>setInput()</code> methods, or from GoraMapper.
 * @see GoraMapper.
 * @param <K> KeyClass.
 * @param <T> PersistentClass.
 */
public class ExtraGoraInputFormat<K, T extends PersistentBase>
  extends InputFormat<K, T> {

  /**
   * Data store to be used.
   */
  private DataStore<K, T> dataStore;

  /**
   * Query to be performed.
   */
  private Query<K, T> query;

  /**
   * @param split InputSplit to be used.
   * @param context JobContext to be used.
   * @return RecordReader record reader used inside Hadoop job.
   */
  @Override
  @SuppressWarnings("unchecked")
  public RecordReader<K, T> createRecordReader(InputSplit split,
      TaskAttemptContext context) throws IOException, InterruptedException {

    PartitionQuery<K, T> partitionQuery = (PartitionQuery<K, T>)
        ((GoraInputSplit) split).getQuery();

    //setInputPath(partitionQuery, context);
    return new GoraRecordReader<K, T>(partitionQuery, context);
  }

  @Override
  public List<InputSplit> getSplits(JobContext context) throws IOException,
      InterruptedException {
    List<PartitionQuery<K, T>> queries =
        getDataStore().getPartitions(getQuery());
    List<InputSplit> splits = new ArrayList<InputSplit>(queries.size());

    for (PartitionQuery<K, T> query : queries) {
      splits.add(new GoraInputSplit(context.getConfiguration(), query));
    }

    return splits;
  }

  /**
   * @return the dataStore
   */
  public DataStore<K, T> getDataStore() {
    return dataStore;
  }

  /**
   * @param datStore the dataStore to set
   */
  public void setDataStore(DataStore<K, T> datStore) {
    this.dataStore = datStore;
  }

  /**
   * @return the query
   */
  public Query<K, T> getQuery() {
    return query;
  }

  /**
   * @param query the query to set
   */
  public void setQuery(Query<K, T> query) {
    this.query = query;
  }

}
