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

import org.apache.giraph.BspCase;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.Text;
import org.junit.Test;

import java.io.IOException;

/*
    Test class for Accumulo vertex input/output formats.
 */
public class TestGoraVertexFormat extends BspCase{

    /**
     * Create the test case
     */
    public TestGoraVertexFormat() {
        super(TestGoraVertexFormat.class.getName());
    }

    /*
     Write a simple parent-child directed graph to Accumulo.
     Run a job which reads the values
     into subclasses that extend AccumuloVertex I/O formats.
     Check the output after the job.
     */
    @Test
    public void testGoraInputOutput() throws Exception {
    }

    /*
    Test compute method that sends each edge a notification of its parents.
    The test set only has a 1-1 parent-to-child ratio for this unit test.
     */
    public static class EdgeNotification
            extends BasicComputation<Text, Text, Text, Text> {
      @Override
      public void compute(Vertex<Text, Text, Text> vertex,
          Iterable<Text> messages) throws IOException {
          for (Text message : messages) {
            vertex.getValue().set(message);
          }
          if(getSuperstep() == 0) {
            sendMessageToAllEdges(vertex, vertex.getId());
          }
        vertex.voteToHalt();
      }
    }
}
