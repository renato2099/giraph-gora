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

import org.apache.avro.util.Utf8;
import org.apache.giraph.io.gora.generated.GEdge;
import org.apache.gora.memory.store.MemStore;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.junit.After;
import org.junit.Before;

/**
 * Abstract Test class for Gora edge input/output formats.
 */
public abstract class AbstractTestGoraEdgeFormat{

  /**
   * In-memory data store used for testing API.
   */
  DataStore<String, GEdge> memStore;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    memStore = DataStoreFactory.getDataStore(
        MemStore.class, String.class, GEdge.class, new Configuration());
    memStore.put("1", createEdge("1", "11", "22", "edge1", (float)1.0));
    memStore.put("2", createEdge("2", "22", "11", "edge2", (float)2.0));
    memStore.put("3", createEdge("3", "11", "33", "edge3", (float)3.0));
    memStore.flush();
  }

  @After
  public void tearDown() throws IOException {
    memStore.close();
  }

  /**
   * Creates an edge using an id and a set of edges.
   * @param id Vertex id.
   * @param vertexInId Vertex source Id.
   * @param vertexOutId Vertex destination Id.
   * @param edgeLabel Edge label.
   * @param edgeWeight Edge wight.
   * @return GEdge created.
   */
  public static GEdge createEdge(String id, String vertexInId,
      String vertexOutId, String edgeLabel, float edgeWeight) {
    GEdge newEdge = new GEdge();
    newEdge.setEdgeId(new Utf8(id));
    newEdge.setVertexInId(new Utf8(vertexInId));
    newEdge.setVertexOutId(new Utf8(vertexOutId));
    newEdge.setLabel(new Utf8(edgeLabel));
    newEdge.setEdgeWeight(edgeWeight);
    return newEdge;
  }
}
