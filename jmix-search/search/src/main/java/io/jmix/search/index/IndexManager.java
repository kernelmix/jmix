/*
 * Copyright 2021 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.search.index;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Collection;
import java.util.Map;

/**
 * Provides functionality for index management.
 */
public interface IndexManager {

    /**
     * Creates index if not exists using provided {@link IndexConfiguration}.
     *
     * @param indexConfiguration index configuration
     * @return true if index was successfully created, false otherwise
     */
    boolean createIndex(IndexConfiguration indexConfiguration);

    /**
     * Drops index by name.
     *
     * @param indexName index name
     * @return true if index was successfully dropped, false otherwise
     */
    boolean dropIndex(String indexName);

    /**
     * Drops and creates all search indexes.
     *
     * @return Map with operation result per every index configuration
     */
    Map<IndexConfiguration, Boolean> recreateIndexes();

    /**
     * Drops and creates search indexes using provided collection of {@link IndexConfiguration}.
     *
     * @param indexConfigurations index configurations
     * @return Map with operation result per every index configuration
     */
    Map<IndexConfiguration, Boolean> recreateIndexes(Collection<IndexConfiguration> indexConfigurations);

    /**
     * Drops and creates search index using provided {@link IndexConfiguration}.
     *
     * @param indexConfiguration index configuration
     * @return true if index was successfully recreated, false otherwise
     */
    boolean recreateIndex(IndexConfiguration indexConfiguration);

    /**
     * Checks if index exists.
     *
     * @param indexName index name
     * @return true if index exists, false otherwise
     */
    boolean isIndexExist(String indexName);

    /**
     * Validates current state of schema of all search indexes defined in application.
     *
     * @return {@link IndexValidationStatus} per each {@link IndexConfiguration}
     */
    Map<IndexConfiguration, IndexValidationStatus> validateIndexes();

    /**
     * Validates current state of index schema related to provided collection of {@link IndexConfiguration}.
     *
     * @param indexConfigurations actual configurations
     * @return {@link IndexValidationStatus} per each {@link IndexConfiguration}
     */
    Map<IndexConfiguration, IndexValidationStatus> validateIndexes(Collection<IndexConfiguration> indexConfigurations);

    /**
     * Validates current state of index schema.
     *
     * @param indexConfiguration actual configuration
     * @return {@link IndexValidationStatus}
     */
    IndexValidationStatus validateIndex(IndexConfiguration indexConfiguration);

    /**
     * Requests info about index from server.
     *
     * @param indexName index name
     * @return response as Json
     */
    ObjectNode getIndexMetadata(String indexName);

    /**
     * Synchronizes schemas of all search indexes defined in application.
     * <p>See {@link IndexManager#synchronizeIndexSchemas(Collection)}
     * <p>See {@link IndexManager#synchronizeIndexSchema(IndexConfiguration)}
     *
     * @return {@link IndexSynchronizationStatus} per each {@link IndexConfiguration}
     */
    Map<IndexConfiguration, IndexSynchronizationStatus> synchronizeIndexSchemas();

    /**
     * Synchronizes schemas of search indexes for provided collection of {@link IndexConfiguration}.
     * <p>
     * See {@link IndexManager#synchronizeIndexSchema(IndexConfiguration)}
     *
     * @param indexConfigurations actual index configurations
     * @return {@link IndexSynchronizationStatus} per each {@link IndexConfiguration}
     */
    Map<IndexConfiguration, IndexSynchronizationStatus> synchronizeIndexSchemas(Collection<IndexConfiguration> indexConfigurations);

    /**
     * Synchronizes schema of search index for provided {@link IndexConfiguration}.
     * <p>
     * It tries to update schema to the actual state according to {@link IndexSchemaManagementStrategy}
     * defined by 'jmix.search.indexSchemaManagementStrategy' application property.
     *
     * @param indexConfiguration actual index configuration
     * @return {@link IndexSynchronizationStatus}
     */
    IndexSynchronizationStatus synchronizeIndexSchema(IndexConfiguration indexConfiguration);
}
