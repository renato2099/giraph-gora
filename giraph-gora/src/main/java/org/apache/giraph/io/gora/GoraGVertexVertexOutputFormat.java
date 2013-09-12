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
import java.io.InterruptedIOException;

import org.apache.giraph.graph.Vertex;
import org.apache.giraph.io.VertexWriter;
import org.apache.gora.persistency.Persistent;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

/**
 * Implementation of a specific reader for a generated data bean.
 */
public class GoraGVertexVertexOutputFormat
  extends GoraVertexOutputFormat<LongWritable, DoubleWritable,
  FloatWritable> {

  /**
   * DEfault constructor
   */
  public GoraGVertexVertexOutputFormat() {
  }

  @Override
  public VertexWriter<LongWritable, DoubleWritable, FloatWritable>
    createVertexWriter(TaskAttemptContext context)
        throws IOException, InterruptedException {
    // TODO Auto-generated method stub
    return new GoraGVertexVertexWriter();
  }
  /**
   * Rexster vertex writer.
   */
  protected class GoraGVertexVertexWriter extends GoraVertexWriter {

    @Override
    protected Persistent getGoraVertex(
        Vertex<LongWritable, DoubleWritable, FloatWritable> vertex) {
      // TODO Auto-generated method stub
      return null;
    }
  
  }
}
