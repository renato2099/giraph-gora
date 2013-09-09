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
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.util.Utf8;
import org.apache.giraph.io.gora.generated.GVertex;
import org.apache.gora.memory.store.MemStore;
import org.apache.gora.store.DataStore;
import org.apache.gora.store.DataStoreFactory;
import org.apache.hadoop.conf.Configuration;
import org.junit.After;
import org.junit.Before;

/**
 * Abstract Test class for Gora vertex input/output formats.
 */
public abstract class AbstractTestGoraVertexFormat{

  /**
   * In-memory data store used for testing API.
   */
  DataStore<String, GVertex> memStore;

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    memStore = DataStoreFactory.getDataStore(
        MemStore.class, String.class, GVertex.class, new Configuration());
    memStore.put("1", createVertex("1", null));
    memStore.put("100", createVertex("100", null));
    Map<String, String> edges = new HashMap<String, String> ();
    edges.put("1", "1");
    edges.put("1", "100");
    memStore.put("10", createVertex("10", edges));
    memStore.flush();
  }

  @After
  public void tearDown() throws IOException {
    memStore.close();
  }

  /**
   * Creates a vertex using an id and a set of edges.
   * @param id Vertex id.
   * @param edges Set of edges.
   * @return GVertex created.
   */
  public static GVertex createVertex(String id, Map<String, String> edges) {
    GVertex newVrtx = new GVertex();
    newVrtx.setVertexId(new Utf8(id));
    if (edges != null) {
      for (String edgeId : edges.keySet())
        newVrtx.putToEdges(new Utf8(edgeId), new Utf8(edges.get(edgeId)));
    }
    return newVrtx;
  }
}
