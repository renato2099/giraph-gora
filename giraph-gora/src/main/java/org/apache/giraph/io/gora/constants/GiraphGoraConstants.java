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
package org.apache.giraph.io.gora.constants;

import org.apache.giraph.conf.StrConfOption;

/**
 * Constants used all over Giraph for configuration specific for Gora
 */
// CHECKSTYLE: stop InterfaceIsTypeCheck
public interface GiraphGoraConstants {
  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_DATASTORE_CLASS =
    new StrConfOption("giraph.gora.datastore.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");

  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_KEY_CLASS =
    new StrConfOption("giraph.gora.datastore.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");

  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_PERSISTENT_CLASS =
    new StrConfOption("giraph.gora.datastore.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");

  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_START_KEY =
    new StrConfOption("giraph.gora.datastore.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");

  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_END_CLASS =
    new StrConfOption("giraph.gora.datastore.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");

  /** Gora data store class which provides data access. */
  StrConfOption GIRAPH_GORA_KEYS_FACTORY_CLASS =
    new StrConfOption("giraph.gora.keys.factory.class", null,
                      "Gora DataStore class to access to data from. " +
                      "- required");
}
// CHECKSTYLE: resume InterfaceIsTypeCheck
