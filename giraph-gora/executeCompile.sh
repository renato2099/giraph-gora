#! /bin/bash
mvn clean compile package -DskipTests
cp conf/gora-cassandra-mapping.xml target/.
cp conf/gora-hbase-mapping.xml target/.
cp conf/gora.properties target/.
cp conf/zoo.cfg target/.

#/Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/conf/zoo.cfg 

###################################
echo "[INFO] Initializing classpath"
###################################
export HADOOP_CLASSPATH=/Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-examples/target/giraph-examples-1.1.0-SNAPSHOT-for-hadoop-0.20.203.0-jar-with-dependencies.jar:/Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/target/giraph-gora-1.1.0-SNAPSHOT-jar-with-dependencies.jar

###################################
echo "[INFO] Cleaning up"
###################################
/Users/renatomarroquin/Documents/Apache/Hadoop/hadoop-0.20.203.0/bin/hadoop fs -rmr shortestPathsOutputGraph

###################################
echo "[INFO] Executing Hadoop"
###################################
/Users/renatomarroquin/Documents/Apache/Hadoop/hadoop-0.20.203.0/bin/hadoop jar /Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-examples/target/giraph-examples-1.1.0-SNAPSHOT-for-hadoop-0.20.203.0-jar-with-dependencies.jar org.apache.giraph.GiraphRunner  -files /Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/target/gora-cassandra-mapping.xml,/Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/target/gora.properties,/Users/renatomarroquin/Documents/Apache/HBase/hbase-0.90.4/conf/hbase-site.xml,/Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/target/gora-hbase-mapping.xml  -Dgiraph.metrics.enable=true -Dio.serializations=org.apache.hadoop.io.serializer.WritableSerialization,org.apache.hadoop.io.serializer.JavaSerialization -libjars /Users/renatomarroquin/Documents/workspace/workspaceGiraph/giraph2/giraph-gora/target/giraph-gora-1.1.0-SNAPSHOT-jar-with-dependencies.jar org.apache.giraph.examples.SimpleShortestPathsComputation -vif org.apache.giraph.io.gora.GoraGVertexVertexInputFormat -of org.apache.giraph.io.formats.IdWithValueTextOutputFormat -op shortestPathsOutputGraph -w 1


###################################
#echo "[INFO] Cleaning up"
###################################
#./Users/renatomarroquin/Documents/Apache/Hadoop/hadoop-0.20.203.0/bin/hadoop fs -rmr shortestPathsOutputGraph
