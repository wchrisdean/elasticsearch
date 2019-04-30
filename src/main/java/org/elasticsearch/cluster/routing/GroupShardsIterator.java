/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.cluster.routing;

import java.util.Collection;
import java.util.Iterator;

/**
 * This class implements a compilation of {@link ShardIterator}s. Each {@link ShardIterator}
 * iterated by this {@link Iterable} represents a group of shards.
 *  
*/
public class GroupShardsIterator implements Iterable<ShardIterator> {

    private final Collection<ShardIterator> iterators;

    public GroupShardsIterator(Collection<ShardIterator> iterators) {
        this.iterators = iterators;
    }

    /**
     * Returns the total number of shards within all groups 
     * @return total number of shards
     */
    public int totalSize() {
        int size = 0;
        for (ShardIterator shard : iterators) {
            size += shard.size();
        }
        return size;
    }

    /**
     * Returns the total number of shards plus the number of empty groups
     * @return number of shards and empty groups 
     */
    public int totalSizeWith1ForEmpty() {
        int size = 0;
        for (ShardIterator shard : iterators) {
            int sizeActive = shard.size();
            if (sizeActive == 0) {
                size += 1;
            } else {
                size += sizeActive;
            }
        }
        return size;
    }

    /**
     * Return the number of groups
     * @return number of groups
     */
    public int size() {
        return iterators.size();
    }

    /**
     * Return all group iterators
     * @return
     */
    public Collection<ShardIterator> iterators() {
        return iterators;
    }

    @Override
    public Iterator<ShardIterator> iterator() {
        return iterators.iterator();
    }
}