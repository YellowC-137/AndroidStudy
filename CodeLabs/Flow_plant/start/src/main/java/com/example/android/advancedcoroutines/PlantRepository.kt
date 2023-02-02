/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.advancedcoroutines

import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.example.android.advancedcoroutines.util.CacheOnSuccess
import com.example.android.advancedcoroutines.utils.ComparablePair
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * Repository module for handling data operations.
 *
 * This PlantRepository exposes two UI-observable database queries [plants] and
 * [getPlantsWithGrowZone].
 *
 * To update the plants cache, call [tryUpdateRecentPlantsForGrowZoneCache] or
 * [tryUpdateRecentPlantsCache].
 */
class PlantRepository private constructor(
    private val plantDao: PlantDao,
    private val plantService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {


    fun getPlantsWithFrowZoneFlow(growZoneNumber: GrowZone): Flow<List<Plant>> {
        return plantDao.getPlantswithGrowZoneNumberFlow(growZoneNumber.number)
    }


    @AnyThread
    suspend fun List<Plant>.applyMainSafeSort(customSortOrder: List<String>) =
    //디스 패쳐간 전환을 위함,  IO 디스패처는 네트워크나 디스크에서 읽기와 같은 IO 작업에 최적화되어 있고,
        // 기본 디스패처는 CPU 집약적인 작업에 최적
        withContext(defaultDispatcher) {
            this@applyMainSafeSort.applySort(customSortOrder)
        }

    // 맞춤 정렬 순서를 위한 메모리내 캐시 사용
    private var plantsListSortOrderCache =
        //sunflower 유틸리티 클래스, 캐싱 추상화
        CacheOnSuccess(onErrorFallback = {
            listOf<String>()
        }) {
            plantService.customPlantSortOrder()
        }

    private val customSortFlow = plantsListSortOrderCache::getOrAwait.asFlow()

    //customSortFlow가 활성화되면 최근 값의 flow와 합하여 다시 정렬시킨다.
    //sortOrder나 plants의 변경이 생기면 다시 호출된다.
    val plantsFlow: Flow<List<Plant>>
        get() = plantDao.getPlantsFlow()
            .combine(customSortFlow) { plants, order ->
                plants.applySort(order)
            }.flowOn(defaultDispatcher).conflate()
    //디스패쳐에서 새 코루틴을 실행해 flowOn의 호출 전에 flow를 실행후 수집,
    //버퍼를 도입해 새 코루틴의 결과를 이후 호출로 전송
    //버퍼의 값을 flowOn 이후의 Flow에 보낸다.(ViewModel로)


    //목록을 다시 정렬하여 plants를 목록 앞부분에 배치한다.
    private fun List<Plant>.applySort(customSortOrder: List<String>): List<Plant> {
        return sortedBy { plant ->
            val positonForItem = customSortOrder.indexOf(plant.plantId).let { order ->
                if (order > -1) order
                else Int.MAX_VALUE
            }
            ComparablePair(positonForItem, plant.name)
        }
    }

    val plants: LiveData<List<Plant>> = liveData<List<Plant>> {
        val plantsLiveData = plantDao.getPlants()
        val customSortOrder = plantsListSortOrderCache.getOrAwait()
        emitSource(plantsLiveData.map { plantList ->
            plantList.applySort(customSortOrder)
        })
    }

    fun getPlantsWithGrowZoneFlow(growZone: GrowZone): Flow<List<Plant>> {
        return plantDao.getPlantswithGrowZoneNumberFlow(growZone.number).map {
            val sortOrderFromNetwork = plantsListSortOrderCache.getOrAwait()
            val nextval = it.applyMainSafeSort(sortOrderFromNetwork)
            nextval
        }
    }

    fun getPlantsWithGrowZone(growZone: GrowZone) =
        plantDao.getPlantsWithGrowZoneNumber(growZone.number)
            .switchMap { plantList ->
                liveData {
                    val customSortOrder = plantsListSortOrderCache.getOrAwait()
                    emit(plantList.applyMainSafeSort(customSortOrder))
                }
            }

    /**
     * Returns true if we should make a network request.
     */
    private fun shouldUpdatePlantsCache(): Boolean {
        // suspending function, so you can e.g. check the status of the database here
        return true
    }

    /**
     * Update the plants cache.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsCache() {
        if (shouldUpdatePlantsCache()) fetchRecentPlants()
    }

    /**
     * Update the plants cache for a specific grow zone.
     *
     * This function may decide to avoid making a network requests on every call based on a
     * cache-invalidation policy.
     */
    suspend fun tryUpdateRecentPlantsForGrowZoneCache(growZoneNumber: GrowZone) {
        if (shouldUpdatePlantsCache()) fetchPlantsForGrowZone(growZoneNumber)
    }

    /**
     * Fetch a new list of plants from the network, and append them to [plantDao]
     */
    private suspend fun fetchRecentPlants() {
        val plants = plantService.allPlants()
        plantDao.insertAll(plants)
    }

    /**
     * Fetch a list of plants for a grow zone from the network, and append them to [plantDao]
     */
    private suspend fun fetchPlantsForGrowZone(growZone: GrowZone) {
        val plants = plantService.plantsByGrowZone(growZone)
        plantDao.insertAll(plants)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PlantRepository? = null

        fun getInstance(plantDao: PlantDao, plantService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: PlantRepository(plantDao, plantService).also { instance = it }
            }
    }
}
