/**
 * Licensed to Big Data Genomics (BDG) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The BDG licenses this file
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
package org.bdgenomics.adam.rdd

import org.bdgenomics.adam.models.{ SequenceDictionary, ReferenceRegion }
import org.apache.spark.rdd.RDD
import org.apache.spark.SparkContext._
import scala.Predef._
import org.apache.spark.SparkContext
import scala.reflect.ClassTag

/**
 * A trait describing a join in the genomic coordinate space between two RDDs
 * where the values are keyed by a ReferenceRegion.
 *
 * @tparam T The type of the left RDD.
 * @tparam U The type of the right RDD.
 * @tparam RT The type of data yielded by the left RDD at the output of the
 *   join. This may not match T if the join is an outer join, etc.
 * @tparam RU The type of data yielded by the right RDD at the output of the
 *   join.
 */
trait RegionJoin[T, U, RT, RU] {

  /**
   * Performs a region join between two RDDs.
   *
   * @param baseRDD The 'left' side of the join
   * @param joinedRDD The 'right' side of the join
   * @param tManifest implicit type of baseRDD
   * @param uManifest implicit type of joinedRDD
   * @tparam T type of baseRDD
   * @tparam U type of joinedRDD
   * @return An RDD of pairs (x, y), where x is from baseRDD, y is from joinedRDD, and the region
   *         corresponding to x overlaps the region corresponding to y.
   */
  def partitionAndJoin(
    baseRDD: RDD[(ReferenceRegion, T)],
    joinedRDD: RDD[(ReferenceRegion, U)])(implicit tManifest: ClassTag[T],
                                          uManifest: ClassTag[U]): RDD[(RT, RU)]
}
