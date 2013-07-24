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
package org.apache.giraph.partition;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * Used to keep track of statistics of every {@link Partition}. Contains no
 * actual partition data, only the statistics.
 */
public class PartitionStats implements Writable {
  /** Id of partition to keep stats for */
  private int partitionId = -1;
  /** Vertices in this partition */
  private long vertexCount = 0;
  /** Maximum Vertices in this partition */
  private long vertexMaxCount = 0;
  /** Minimum Vertices in this partition */
  private long vertexMinCount = 0;
  /** Average Vertices in this partition */
  private long vertexAvgCount = 0;
  /** Finished vertices in this partition */
  private long finishedVertexCount = 0;
  /** Edges in this partition */
  private long edgeCount = 0;
  /** Maximum Edges in this partition */
  private long edgeMaxCount = 0;
  /** Minimum Edges in this partition */
  private long edgeMinCount = 0;
  /** Messages sent from this partition */
  private long messagesSentCount = 0;

  /**
   * Default constructor for reflection.
   */
  public PartitionStats() { }

  /**
   * Constructor with the initial stats.
   *
   * @param partitionId Partition count.
   * @param vertexCount Vertex count.
   * @param finishedVertexCount Finished vertex count.
   * @param edgeCount Edge count.
   * @param messagesSentCount Number of messages sent
   */
  public PartitionStats(int partitionId,
      long vertexCount,
      long finishedVertexCount,
      long edgeCount,
      long messagesSentCount) {
    this.partitionId = partitionId;
    this.vertexCount = vertexCount;
    this.finishedVertexCount = finishedVertexCount;
    this.edgeCount = edgeCount;
    this.messagesSentCount = messagesSentCount;
    // we can initialize this as zeros because this are calculated
    this.edgeMaxCount = 0;
    this.edgeMinCount = 0;
    this.vertexMaxCount = 0;
    this.vertexMinCount = 0;
    this.vertexAvgCount = 0;
  }

  /**
   * Set the partition id.
   *
   * @param partitionId New partition id.
   */
  public void setPartitionId(int partitionId) {
    this.partitionId = partitionId;
  }

  /**
   * Get partition id.
   *
   * @return Partition id.
   */
  public int getPartitionId() {
    return partitionId;
  }

  /**
   * Increment the vertex count by one.
   */
  public void incrVertexCount() {
    ++vertexCount;
  }

  /**
   * Get the vertex count.
   *
   * @return Vertex count.
   */
  public long getVertexCount() {
    return vertexCount;
  }

  /**
   * Get the vertex maximum count.
   *
   * @return Vertex count.
   */
  public long getVertexMaxCount() {
    return vertexMaxCount;
  }

  /**
   * Sets the vertex maximum count.
   * @param vertexMaxCount New vertex maximum count.
   */
  public void setVertexMaxCount(long vertexMaxCount) {
    if (this.vertexMaxCount < vertexMaxCount) {
      this.vertexMaxCount = vertexMaxCount;
    }
  }

  /**
   * Get the vertex minimum count.
   *
   * @return Vertex count.
   */
  public long getVertexMinCount() {
    return vertexMinCount;
  }

  /**
   * Sets the vertex minimum count.
   * @param vertexMinCount New vertex minimum count.
   */
  public void setVertexMinCount(long vertexMinCount) {
    if (this.vertexMinCount > vertexMinCount) {
      this.vertexMinCount = vertexMinCount;
    }
  }

  /**
   * Get the vertex average count.
   *
   * @return Vertex count.
   */
  public long getVertexAvgCount() {
    return vertexAvgCount;
  }

  /**
   * Increment the finished vertex count by one.
   */
  public void incrFinishedVertexCount() {
    ++finishedVertexCount;
  }

  /**
   * Get the finished vertex count.
   *
   * @return Finished vertex count.
   */
  public long getFinishedVertexCount() {
    return finishedVertexCount;
  }

  /**
   * Add edges to the edge count.
   *
   * @param edgeCount Number of edges to add.
   */
  public void addEdgeCount(long edgeCount) {
    this.edgeCount += edgeCount;
  }

  /**
   * Get the edge count.
   *
   * @return Edge count.
   */
  public long getEdgeCount() {
    return edgeCount;
  }

  /**
   * Get the edge maximum count.
   *
   * @return Edge count.
   */
  public long getEdgeMaxCount() {
    return edgeMaxCount;
  }

  /**
   * Sets the vertex maximum count.
   * @param edgeMaxCount New edge maximum count.
   */
  public void setEdgeMaxCount(long edgeMaxCount) {
    if (this.edgeMaxCount < edgeMaxCount) {
      this.edgeMaxCount = edgeMaxCount;
    }
  }

  /**
   * Get the edge count.
   *
   * @return Edge count.
   */
  public long getEdgeMinCount() {
    return edgeMinCount;
  }

  /**
   * Sets the vertex maximum count.
   * @param edgeMinCount New edge minimum count.
   */
  public void setEdgeMinCount(long edgeMinCount) {
    if (this.edgeMinCount > edgeMinCount) {
      this.edgeMinCount = edgeMinCount;
    }
  }

  /**
   * Add messages to the messages sent count.
   *
   * @param messagesSentCount Number of messages to add.
   */
  public void addMessagesSentCount(long messagesSentCount) {
    this.messagesSentCount += messagesSentCount;
  }

  /**
   * Get the messages sent count.
   *
   * @return Messages sent count.
   */
  public long getMessagesSentCount() {
    return messagesSentCount;
  }

  @Override
  public void readFields(DataInput input) throws IOException {
    partitionId = input.readInt();
    vertexCount = input.readLong();
    finishedVertexCount = input.readLong();
    edgeCount = input.readLong();
    messagesSentCount = input.readLong();
  }

  @Override
  public void write(DataOutput output) throws IOException {
    output.writeInt(partitionId);
    output.writeLong(vertexCount);
    output.writeLong(finishedVertexCount);
    output.writeLong(edgeCount);
    output.writeLong(messagesSentCount);
  }

  @Override
  public String toString() {
    return "(id=" + partitionId + ",vtx=" + vertexCount + ",finVtx=" +
        finishedVertexCount + ",edges=" + edgeCount + ",msgsSent=" +
        messagesSentCount + ")";
  }
}
