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

package org.apache.giraph.aggregators.matrix;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map.Entry;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

import org.apache.hadoop.io.Writable;

/**
 * The int vector holds the values of a particular row.
 */
public class IntVector implements Writable {
  /**
   * The entries of the vector are (key, value) pairs of the form (row, value)
   */
  private Int2IntOpenHashMap entries = null;

  /**
   * Create a new vector with default size.
   */
  public IntVector() {
    initialize(Int2IntOpenHashMap.DEFAULT_INITIAL_SIZE);
  }

  /**
   * Create a new vector with given size.
   *
   * @param size the size of the vector
   */
  public IntVector(int size) {
    initialize(size);
  }

  /**
   * Initialize the values of the vector. The default value is 0.0
   *
   * @param size the size of the vector
   */
  private void initialize(int size) {
    entries = new Int2IntOpenHashMap(size);
    entries.defaultReturnValue(0);
  }

  /**
   * Get a particular entry of the vector.
   *
   * @param i the entry
   * @return the value of the entry.
   */
  int get(int i) {
    return entries.get(i);
  }

  /**
   * Set the given value to the entry specified.
   *
   * @param i the entry
   * @param value the value to set to the entry
   */
  void set(int i, int value) {
    entries.put(i, value);
  }

  /**
   * Clear the contents of the vector.
   */
  void clear() {
    entries.clear();
  }

  /**
   * Add the vector specified. This is a vector addition that does an
   * element-by-element addition.
   *
   * @param other the vector to add.
   */
  void add(IntVector other) {
    for (Entry<Integer, Integer> entry : other.entries.entrySet()) {
      entries.addTo(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeInt(entries.size());
    for (Entry<Integer, Integer> entry : entries.entrySet()) {
      out.writeInt(entry.getKey());
      out.writeInt(entry.getValue());
    }
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    int size = in.readInt();
    initialize(size);
    for (int i = 0; i < size; ++i) {
      int row = in.readInt();
      int value = in.readInt();
      entries.put(row, value);
    }
  }
}
