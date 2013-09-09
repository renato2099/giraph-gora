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
 * Class for defining a Giraph-Edge.
 */
@SuppressWarnings("all")
public class GEdge extends PersistentBase {
  /**
   * Schema used for the class.
   */
  public static final Schema OBJ_SCHEMA = Schema.parse(
      "{\"type\":\"record\",\"name\":\"Edge\"," +
      "\"namespace\":\"org.apache.giraph.gora.generated\"," +
      "\"fields\":[{\"name\":\"vertexId\",\"type\":\"string\"}," +
      "{\"name\":\"edgeValue\",\"type\":\"float\"}]}");

  /**
   * Field enum
   */
  public static enum Field {
    /**
     * VertexId
     */
    VERTEX_ID(0, "vertexId"),

    /**
     * Edge value
     */
    EDGE_VALUE(1, "edgeValue");

    /**
     * Field index
     */
    private int index;

    /**
     * Field name
     */
    private String name;

    /**
     * Field constructor
     * @param index of attribute
     * @param name of attribute
     */
    Field(int index, String name) {
      this.index = index;
      this.name = name;
    }

    /**
     * Gets index
     * @return int of attribute.
     */
    public int getIndex() {
      return index;
    }

    /**
     * Gets name
     * @return String of name.
     */
    public String getName() {
      return name;
    }

    /**
     * Gets name
     * @return String of name.
     */
    public String toString() {
      return name;
    }
  };

  /**
   * Array containing all fields/
   */
  private static final String[] ALL_FIELDS = {
    "vertexId", "edgeValue"
  };

  static {
    PersistentBase.registerFields(GEdge.class, ALL_FIELDS);
  }

  /**
   * vertexId
   */
  private Utf8 vertexId;

  /**
   * edgeValue
   */
  private float edgeValue;

  /**
   * Default constructor
   */
  public GEdge() {
    this(new StateManagerImpl());
  }

  /**
   * Constructor
   * @param stateManager from which the object will be created.
   */
  public GEdge(StateManager stateManager) {
    super(stateManager);
  }

  /**
   * Creates a new instance
   * @param stateManager from which the object will be created.
   * @return GEdge created
   */
  public GEdge newInstance(StateManager stateManager) {
    return new GEdge(stateManager);
  }

  /**
   * Gets the object schema
   * @return Schema of the object.
   */
  public Schema getSchema() {
    return OBJ_SCHEMA;
  }

  /**
   * Gets field
   * @param fieldIndex index field.
   * @return Object from an index.
   */
  public Object get(int fieldIndex) {
    switch (fieldIndex) {
    case 0:
      return vertexId;
    case 1:
      return edgeValue;
    default:
      throw new AvroRuntimeException("Bad index");
    }
  }

  /**
   * Puts a value into a field.
   * @param fieldIndex index of field used.
   * @param fieldValue value of field used.
   */
  @SuppressWarnings(value = "unchecked")
  public void put(int fieldIndex, Object fieldValue) {
    if (isFieldEqual(fieldIndex, fieldValue)) {
      return;
    }
    getStateManager().setDirty(this, fieldIndex);
    switch (fieldIndex) {
    case 0:
      vertexId = (Utf8) fieldValue; break;
    case 1:
      edgeValue = (Float) fieldValue; break;
    default:
      throw new AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets vertexId
   * @return Utf8 vertexId
   */
  public Utf8 getVertexId() {
    return (Utf8) get(0);
  }

  /**
   * Sets vertexId
   * @param value vertexId
   */
  public void setVertexId(Utf8 value) {
    put(0, value);
  }

  /**
   * Gets EdgeValue
   * @return Utf8 EdgeValue
   */
  public float getEdgeValue() {
    return (Float) get(1);
  }

  /**
   * Sets EdgeValue.
   * @param value EdgeValue
   */
  public void setEdgeValue(float value) {
    put(1, value);
  }
}
