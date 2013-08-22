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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.avro.util.Utf8;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.gora.generated.GVertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * Implementation of a specific reader for a generated data bean.
 */
public class GoraGVertexVertexInputFormat
  extends GoraVertexInputFormat<LongWritable, DoubleWritable,
          FloatWritable> {
//LongWritable, DoubleWritable, FloatWritable, DoubleWritable

  public GoraGVertexVertexInputFormat() {
    this.initialize();
  }

  public void initialize() {
    setKeyClass(String.class);
    setPersistentClass(GVertex.class);
    super.initialize();
  }

  @Override
  public GoraVertexReader createVertexReader(
      InputSplit split, TaskAttemptContext context) throws IOException {
    //System.out.println("Inside createVertexReader method");
    return new GoraGVertexVertexReader();
  }

  /**
   * Gora vertex reader
   */
  protected class GoraGVertexVertexReader extends GoraVertexReader {

    @Override
    protected Vertex<LongWritable, DoubleWritable, FloatWritable> transformVertex(
        Object goraObject) {
      if (goraObject == null) {
        System.out.println("El objeto en transformVertex es nulo - GoraGVertex");
      }
      System.out.println("transforming vertex in GVertex");
      Vertex<LongWritable, DoubleWritable, FloatWritable> vertex;
      /* create the actual vertex */
      vertex = getConf().createVertex();
      GVertex tmpGVertex = (GVertex) goraObject;
      
      LongWritable vrtxId = new LongWritable(
          Long.parseLong(tmpGVertex.getVertexId().toString()));
      DoubleWritable vrtxValue = new DoubleWritable(tmpGVertex.getValue());
      vertex.initialize(vrtxId, vrtxValue);
      if (tmpGVertex.getEdges() != null && !tmpGVertex.getEdges().isEmpty()){
        Set<Utf8> keyIt = tmpGVertex.getEdges().keySet();
        System.out.println(keyIt.toString());
        for(Utf8 key : keyIt ){
          System.out.println("trying to add edges");
          String keyVal = "1"; //key.toString();
          String valVal = "1";//tmpGVertex.getEdges().get(key).toString();
          Edge<LongWritable, FloatWritable> edge;
          edge = EdgeFactory.create(
              new LongWritable(Long.parseLong(keyVal)),
              new FloatWritable(Long.parseLong(valVal)));
          vertex.addEdge(edge);
          System.out.println("EdgeKey " + keyVal);
          System.out.println("EdgeValue " + valVal);
        }
      }
      System.out.println("Gora vertex: " + goraObject.toString());
      System.out.println("Giraph vertex: " + vertex.toString());
      return vertex;
    }
  }
}
