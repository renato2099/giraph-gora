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
package org.apache.giraph.examples;

import java.io.IOException;

import org.apache.giraph.conf.LongConfOption;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
import org.apache.giraph.Algorithm;

/**
 * Demonstrates community detection algorithm implementation.
 */
@Algorithm(
    name = "Community detection",
    description = "Finds communities within a group of nodes"
)

public class CommunityDetectionComputation extends BasicComputation
    <LongWritable, Text, FloatWritable, Text> {
  /** The shortest paths id */
  public static final LongConfOption SOURCE_ID =
      new LongConfOption("CommunityDetectionComputation.sourceId", 1,
          "The starting point vertex id");
  /** Class logger */
  private static final Logger LOG =
      Logger.getLogger(CommunityDetectionComputation.class);

  @Override
  public void compute(Vertex<LongWritable, Text, FloatWritable> vertex,
    Iterable<Text> messages) throws IOException {
    //if (LOG.isDebugEnabled()) {
    LOG.info("Vertex " + vertex.getId() + " got value = " +
      vertex.getValue() + " vertex value = " + vertex.getValue());
    //}
    vertex.voteToHalt();
  }
}
