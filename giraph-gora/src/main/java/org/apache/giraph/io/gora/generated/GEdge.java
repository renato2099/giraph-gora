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

import org.apache.avro.Schema;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.util.Utf8;
import org.apache.gora.persistency.StateManager;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.persistency.impl.StateManagerImpl;

/**
 * Class containing a graph edge abstraction.
 */
@SuppressWarnings("all")
public class GEdge extends PersistentBase {

  /**
   * Variable holding the data bean schema.
   */
  public static final Schema SCHEMA = Schema.parse("{\"type\":\"record\"," +
      "\"name\":\"Edge\",\"namespace\":" +
      "\"org.apache.giraph.gora.generated\",\"fields\":" +
      "[{\"name\":\"vertexId\",\"type\":\"string\"}," +
      "{\"name\":\"edgeValue\",\"type\":\"float\"}]}");

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
    EDGE_VALUE(1, "edgeValue");

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
  private static final String[] ALL_FIELDS = {"vertexId", "edgeValue", };
  static {
    PersistentBase.registerFields(GEdge.class, ALL_FIELDS);
  }

  /**
   * VertexId.
   */
  private Utf8 vertexId;

  /**
   * EdgeValue.
   */
  private float edgeValue;

  /**
   * Default Constructor
   */
  public GEdge() {
    this(new StateManagerImpl());
  }

  /**
   * Constructor.
   * @param stateManager for the data bean.
   */
  public GEdge(StateManager stateManager) {
    super(stateManager);
  }

  /**
   * Returns a new instance by using a state manager.
   * @param stateManager for the data bean.
   * @return edge created.
   */
  public GEdge newInstance(StateManager stateManager) {
    return new GEdge(stateManager);
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
   * @return value representing a data bean's field.
   */
  public Object get(int field) {
    switch (field) {
    case 0:
      return vertexId;
    case 1:
      return edgeValue;
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
      edgeValue = (Float) value; break;
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
   * Gets the edge value.
   * @return float representing edge value.
   */
  public float getEdgeValue() {
    return (Float) get(1);
  }

  /**
   * Sets the edge value.
   * @param value containing edgeValue.
   */
  public void setEdgeValue(float value) {
    put(1, value);
  }
}
