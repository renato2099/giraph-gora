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

package org.apache.giraph.io.gora.generated;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.util.Utf8;
import org.apache.gora.persistency.StateManager;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.persistency.impl.StateManagerImpl;
import org.apache.gora.persistency.StatefulHashMap;

/**
 * Class containing a graph edge abstraction.
 */
@SuppressWarnings("all")
public class GVertex extends PersistentBase {

  /**
   * Variable holding the data bean schema.
   */
  public static final Schema SCHEMA = Schema.parse("{\"type\":\"record\"," +
      "\"name\":\"Vertex\",\"namespace\":" +
      "\"org.apache.giraph.gora.generated\",\"fields\":" +
      "[{\"name\":\"vertexId\",\"type\":\"string\"}," +
      "{\"name\":\"vertexValue\",\"type\":\"float\"}," +
      "{\"name\":\"edges\",\"type\":" +
      "{\"type\":\"map\",\"values\":\"string\"}}]}");

  /**
   * Enum containing all data bean's fields.
   */
  public static enum Field {

    /**
     * VertexId.
     */
    VERTEX_ID(0, "vertexId"),

    /**
     * EdgeValue.
     */
    VERTEX_VALUE(1, "vertexValue"),

    /**
     * Edges.
     */
    EDGES(2, "edges");

    /**
     * Field's index.
     */
    private int index;

    /**
     * Field's name.
     */
    private String name;

    /**
     * Field's constructor
     * @param index field's index.
     * @param name field's name.
     */
    Field(int index, String name) {
      this.index = index;
      this.name = name;
    }

    /**
     * Gets field's index.
     * @return int field's index.
     */
    public int getIndex() {
      return index;
    }

    /**
     * Gets field's name.
     * @return String field's name.
     */
    public String getName() {
      return name;
    }

    /**
     * Gets field's attributes to string.
     * @return String field's attributes to string.
     */
    public String toString() {
      return name;
    }
  };

  /**
   * Contains all field's names.
   */
  private static final String[] ALL_FIELDS = {"vertexId",
    "vertexValue",
    "edges", };
  static {
    PersistentBase.registerFields(GVertex.class, ALL_FIELDS);
  }

  /**
   * VertexId.
   */
  private Utf8 vertexId;

  /**
   * Value.
   */
  private float vertexValue;

  /**
   * Edges using key as vertexId and value as edge value.
   */
  private Map<Utf8, Utf8> edges;

  /**
   * Default Constructor
   */
  public GVertex() {
    this(new StateManagerImpl());
  }

  /**
   * Constructor.
   * @param stateManager for the data bean.
   */
  public GVertex(StateManager stateManager) {
    super(stateManager);
    edges = new StatefulHashMap<Utf8, Utf8>();
  }

  /**
   * Returns a new instance by using a state manager.
   * @param stateManager for the data bean.
   * @return vertex created.
   */
  public GVertex newInstance(StateManager stateManager) {
    return new GVertex(stateManager);
  }

  /**
   * Returns the schema of the data bean.
   * @return Schema for the data bean.
   */
  public Schema getSchema() {
    return SCHEMA;
  }

  /**
   * Gets a specific field.
   * @param field index of a field for the data bean.
   * @return Object representing a data bean's field.
   */
  public Object get(int field) {
    switch (field) {
    case 0:
      return vertexId;
    case 1:
      return vertexValue;
    case 2:
      return edges;
    default:
      throw new AvroRuntimeException("Bad index");
    }
  }

  /**
   * Puts a value for a specific field.
   * @param field index of a field for the data bean.
   * @param value value of a field for the data bean.
   */
  @SuppressWarnings(value = "unchecked")
  public void put(int field, Object value) {
    if (isFieldEqual(field, value)) {
      return;
    }
    getStateManager().setDirty(this, field);
    switch (field) {
    case 0:
      vertexId = (Utf8) value; break;
    case 1:
      vertexValue = (Float) value; break;
    case 2:
      edges = (Map<Utf8, Utf8>) value; break;
    default:
      throw new AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the vertex ID.
   * @return Utf8 representing vertexID.
   */
  public Utf8 getVertexId() {
    return (Utf8) get(0);
  }

  /**
   * Sets the vertex ID.
   * @param value containing vertexID.
   */
  public void setVertexId(Utf8 value) {
    put(0, value);
  }

  /**
   * Gets the vertex value.
   * @return float representing vertex value.
   */
  public float getValue() {
    return (Float) get(1);
  }

  /**
   * Sets the vertex value.
   * @param value containing vertex value.
   */
  public void setValue(float value) {
    put(1, value);
  }

  /**
   * Gets edges.
   * @return Map containing edges value.
   */
  public Map<Utf8, Utf8> getEdges() {
    return (Map<Utf8, Utf8>) get(2);
  }

  /**
   * Gets edge's value using a key.
   * @param key gets an specific edge using a vertexID.
   * @return Utf8 containing edges value.
   */
  public Utf8 getFromEdges(Utf8 key) {
    if (edges == null) {
      return null;
    }
    return edges.get(key);
  }

  /**
   * Puts an edge into a vertex
   * @param key vertex ID.
   * @param value value of the edge.
   */
  public void putToEdges(Utf8 key, Utf8 value) {
    getStateManager().setDirty(this, 2);
    edges.put(key, value);
  }

  /**
   * Removes an edge from a vertex.
   * @param key vertex ID to be removed.
   * @return Utf8 holding vertex ID removed.
   */
  public Utf8 removeFromEdges(Utf8 key) {
    if (edges == null) {
      return null;
    }
    getStateManager().setDirty(this, 2);
    return edges.remove(key);
  }
}
