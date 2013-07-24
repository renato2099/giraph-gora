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

import org.apache.giraph.io.VertexOutputFormat;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
/**
 *
 *  Class which wraps the GoraOutputFormat. It's designed
 *  as an extension point to VertexOutputFormat subclasses who wish
 *  to write vertices back to an Accumulo table.
 *
 *  Works with
 *  {@link GoraVertexInputFormat}
 *
 *
 * @param <I> vertex id type
 * @param <V>  vertex value type
 * @param <E>  edge type
 */
public abstract class GoraVertexOutputFormat<
        I extends WritableComparable,
        V extends Writable,
        E extends Writable>
        extends VertexOutputFormat<I, V, E> {


  /**
   * Output table parameter
   */
  public static final String OUTPUT_TABLE = "OUTPUT_TABLE";

  /**
   *
   * checkOutputSpecs
   *
   * @param context information about the job
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public void checkOutputSpecs(JobContext context)
    throws IOException, InterruptedException {
  }

  /**
   * getOutputCommitter
   *
   * @param context the task context
   * @return OutputCommitter
   * @throws IOException
   * @throws InterruptedException
   */
  @Override
  public OutputCommitter getOutputCommitter(TaskAttemptContext context)
    throws IOException, InterruptedException {
    return null;
  }
}
