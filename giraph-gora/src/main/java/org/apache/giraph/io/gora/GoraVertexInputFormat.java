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
import java.util.List;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.VertexInputFormat;
import org.apache.giraph.io.VertexReader;
import org.apache.gora.mapreduce.GoraInputFormat;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.query.Result;
import org.apache.gora.store.DataStore;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.log4j.Logger;

/**
 *  Class which wraps the GoraInputFormat. It's designed
 *  as an extension point to VertexInputFormat subclasses who wish
 *  to read from Gora data sources.
 *
 *  Works with
 *  {@link GoraVertexOutputFormat}
 *
 * @param <I> vertex id type
 * @param <V>  vertex value type
 * @param <E>  edge type
 */
public abstract class GoraVertexInputFormat<
        I extends WritableComparable,
        V extends Writable,
        E extends Writable>
        extends VertexInputFormat<I, V, E> {

  /** Logger for Gora's vertex input format. */
  private static final Logger LOG =
          Logger.getLogger(GoraVertexInputFormat.class);

  /**
   * delegate input format for gora operations.
   */
  protected GoraInputFormat goraInputFormat =
      new GoraInputFormat();

  /**
   * Data store used for querying data.
   */
  private DataStore dataStore;

  /** @param conf configuration parameters */
  public void checkInputSpecs(Configuration conf) { }

  /**
   * Create a vertex reader for a given split. Guaranteed to have been
   * configured with setConf() prior to use.  The framework will also call
   * {@link VertexReader#initialize(InputSplit, TaskAttemptContext)} before
   * the split is used.
   *
   * @param split the split to be read
   * @param context the information about the task
   * @return a new record reader
   * @throws IOException
   */
  public abstract GoraVertexReader createVertexReader(InputSplit split,
    TaskAttemptContext context) throws IOException;

  @Override
  public List<InputSplit> getSplits(JobContext context, int minSplitCountHint)
    throws IOException, InterruptedException {
    List<InputSplit> splits = null;
    try {
      splits = goraInputFormat.getSplits(context);
    } catch (IOException e) {
      if (e.getMessage().contains("Input info has not been set")) {
        throw new IOException(e.getMessage() +
                " Make sure you initialized" +
                " GoraInputFormat static setters " +
                "before passing the config to GiraphJob.");
      }
    }
    return splits;
  }

  /**
   * Abstract class to be implemented by the user based on their specific
   * vertex input. Easiest to ignore the key value separator and only use
   * key instead.
   */
  protected abstract class GoraVertexReader extends VertexReader<I, V, E> {
    /** Current vertex */
    private Vertex<I, V, E> vertex;
    /** The vertex ID will be used as a key inside Gora. */
    private Class<?> keyClass;
    /** The vertex itself will be used as a value inside Gora. */
    private Class<? extends PersistentBase> persistentClass;
    /** Results gotten from Gora data store. */
    private Result readResults;
    /** Start key for querying Gora data store. */
    private Object startKey;
    /** End key for querying Gora data store. */
    private Object endKey;

    @Override
    public void initialize(InputSplit inputSplit, TaskAttemptContext context)
      throws IOException, InterruptedException {
      // initialize gora data store
      dataStore = GoraUtils.createSpecificDataStore(GoraUtils.CASSANDRA_STORE,
          getKeyClass(), getPersistentClass());
    }

    /**
     * Gets the next vertex from Gora data store.
     * @return true/false depending on the existence of vertices.
     * @throws IOException exceptions passed along.
     * @throws InterruptedException exceptions passed along.
     */
    @Override
    public boolean nextVertex() throws IOException, InterruptedException {
      LOG.debug("Reading vertices from Gora");
      if (getReadResults() != null) {
        while (getReadResults().next()) {
          vertex = transformVertex(getReadResults().get());
        }
        return true;
      }
      return false;
    }

    /**
     * Gets the progress of reading results from Gora.
     * @return the progress of reading results from Gora.
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
      float progress = 0.0f;
      if (getReadResults() != null) {
        progress = getReadResults().getProgress();
      }
      return progress;
    }

    /**
     * Gets current vertex.
     *
     * @return  The vertex object represented by a Gora object
     */
    @Override
    public Vertex<I, V, E> getCurrentVertex()
      throws IOException, InterruptedException {
      return this.vertex;
    }

    /**
     * Parser for a single Gora object
     *
     * @param   goraObject vertex represented as a GoraObject
     * @return  The vertex object represented by a Gora object
     */
    protected abstract Vertex<I, V, E> transformVertex(Object goraObject);

    /**
     * Performs a range query to a Gora data store.
     */
    protected void getResults() {
      setReadResults(GoraUtils.getRequests(dataStore,
          getStartKey(), getEndKey()));
    }

    /**
     * Finishes the reading process.
     * @throws IOException.
     */
    @Override
    public void close() throws IOException {
    }

    /**
     * Gets the start key for querying.
     * @return the start key.
     */
    public Object getStartKey() {
      return startKey;
    }

    /**
     * Gets the start key for querying.
     * @param startKey start key.
     */
    public void setStartKey(Object startKey) {
      this.startKey = startKey;
    }

    /**
     * Gets the end key for querying.
     * @return the end key.
     */
    Object getEndKey() {
      return endKey;
    }

    /**
     * Sets the end key for querying.
     * @param pEndKey start key.
     */
    void setEndKey(Object pEndKey) {
      this.endKey = pEndKey;
    }

    Result getReadResults() {
      return readResults;
    }

    void setReadResults(Result readResults) {
      this.readResults = readResults;
    }

    Class<? extends PersistentBase> getPersistentClass() {
      return persistentClass;
    }

    void setPersistentClass(Class<? extends PersistentBase> persistentClass) {
      this.persistentClass = persistentClass;
    }

    Class<?> getKeyClass() {
      return keyClass;
    }

    void setKeyClass(Class<?> keyClass) {
      this.keyClass = keyClass;
    }
  }
}
