/**
 *Licensed to the Apache Software Foundation (ASF) under one
 *or more contributor license agreements.  See the NOTICE file
 *distributed with this work for additional information
 *regarding copyright ownership.  The ASF licenses this file
 *to you under the Apache License, Version 2.0 (the"
 *License"); you may not use this file except in compliance
 *with the License.  You may obtain a copy of the License at
 *
  * http://www.apache.org/licenses/LICENSE-2.0
 * 
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */

package org.apache.giraph.io.gora.generated;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.HashMap;
import org.apache.avro.Protocol;
import org.apache.avro.Schema;
import org.apache.avro.AvroRuntimeException;
import org.apache.avro.Protocol;
import org.apache.avro.util.Utf8;
import org.apache.avro.ipc.AvroRemoteException;
import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.FixedSize;
import org.apache.avro.specific.SpecificExceptionBase;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.avro.specific.SpecificRecord;
import org.apache.avro.specific.SpecificFixed;
import org.apache.gora.persistency.StateManager;
import org.apache.gora.persistency.impl.PersistentBase;
import org.apache.gora.persistency.impl.StateManagerImpl;
import org.apache.gora.persistency.StatefulHashMap;
import org.apache.gora.persistency.ListGenericArray;

@SuppressWarnings("all")
public class GVertex extends PersistentBase {
  public static final Schema _SCHEMA = Schema.parse("{\"type\":\"record\",\"name\":\"Vertex\",\"namespace\":\"org.apache.giraph.gora.generated\",\"fields\":[{\"name\":\"vertexId\",\"type\":\"string\"},{\"name\":\"value\",\"type\":\"float\"},{\"name\":\"edges\",\"type\":{\"type\":\"map\",\"values\":\"string\"}}]}");
  public static enum Field {
    VERTEX_ID(0,"vertexId"),
    VALUE(1,"value"),
    EDGES(2,"edges"),
    ;
    private int index;
    private String name;
    Field(int index, String name) {this.index=index;this.name=name;}
    public int getIndex() {return index;}
    public String getName() {return name;}
    public String toString() {return name;}
  };
  public static final String[] _ALL_FIELDS = {"vertexId","value","edges",};
  static {
    PersistentBase.registerFields(GVertex.class, _ALL_FIELDS);
  }
  private Utf8 vertexId;
  private float value;
  private Map<Utf8,Utf8> edges;
  public GVertex() {
    this(new StateManagerImpl());
  }
  public GVertex(StateManager stateManager) {
    super(stateManager);
    edges = new StatefulHashMap<Utf8,Utf8>();
  }
  public GVertex newInstance(StateManager stateManager) {
    return new GVertex(stateManager);
  }
  public Schema getSchema() { return _SCHEMA; }
  public Object get(int _field) {
    switch (_field) {
    case 0: return vertexId;
    case 1: return value;
    case 2: return edges;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  @SuppressWarnings(value="unchecked")
  public void put(int _field, Object _value) {
    if(isFieldEqual(_field, _value)) return;
    getStateManager().setDirty(this, _field);
    switch (_field) {
    case 0:vertexId = (Utf8)_value; break;
    case 1:value = (Float)_value; break;
    case 2:edges = (Map<Utf8,Utf8>)_value; break;
    default: throw new AvroRuntimeException("Bad index");
    }
  }
  public Utf8 getVertexId() {
    return (Utf8) get(0);
  }
  public void setVertexId(Utf8 value) {
    put(0, value);
  }
  public float getValue() {
    return (Float) get(1);
  }
  public void setValue(float value) {
    put(1, value);
  }
  public Map<Utf8, Utf8> getEdges() {
    return (Map<Utf8, Utf8>) get(2);
  }
  public Utf8 getFromEdges(Utf8 key) {
    if (edges == null) { return null; }
    return edges.get(key);
  }
  public void putToEdges(Utf8 key, Utf8 value) {
    getStateManager().setDirty(this, 2);
    edges.put(key, value);
  }
  public Utf8 removeFromEdges(Utf8 key) {
    if (edges == null) { return null; }
    getStateManager().setDirty(this, 2);
    return edges.remove(key);
  }
}
