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

package org.apache.paimon.flink.sink;

import org.apache.paimon.table.FileStoreTable;

import org.apache.flink.streaming.api.operators.OneInputStreamOperator;
import org.apache.flink.table.data.RowData;

import javax.annotation.Nullable;

import java.util.Map;

/** {@link FlinkSink} for writing records into paimon. */
public class FileStoreSink extends FlinkWriteSink<RowData> {

    private static final long serialVersionUID = 1L;

    @Nullable private final LogSinkFunction logSinkFunction;

    public FileStoreSink(
            FileStoreTable table,
            @Nullable Map<String, String> overwritePartition,
            @Nullable LogSinkFunction logSinkFunction) {
        super(table, overwritePartition);
        this.logSinkFunction = logSinkFunction;
    }

    @Override
    protected OneInputStreamOperator<RowData, Committable> createWriteOperator(
            StoreSinkWrite.Provider writeProvider, String commitUser) {
        return new RowDataStoreWriteOperator(table, logSinkFunction, writeProvider, commitUser);
    }
}
