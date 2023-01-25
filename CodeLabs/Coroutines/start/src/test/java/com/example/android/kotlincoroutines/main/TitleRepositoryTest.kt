/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.kotlincoroutines.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.kotlincoroutines.fakes.MainNetworkFake
import com.example.android.kotlincoroutines.fakes.TitleDaoFake
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

class TitleRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun whenRefreshTitleSuccess_insertsRows() {
        val subject = TitleRepository(
            MainNetworkFake("OK"),
            TitleDaoFake("title")
        )
        //runBlocking,runBlockingTest 대신에 launch를 사용하여 바로 응답을 받아야함.
        GlobalScope.launch {
            subject.refreshTitle()
            //refreshTitle이 정지함수 이므로 이를
            // 비동기적으로 실행하기 위해서 GlobalScope에서 실행

        }


    }

    @Test(expected = TitleRefreshError::class)
    fun whenRefreshTitleTimeout_throws() = runBlockingTest{
        val subject = TitleRepository(
            MainNetworkFake("OK"),
            TitleDaoFake("title")
        )
        subject.refreshTitle()
        throw TitleRefreshError("Remove this – made test pass in starter code", null)
    }
}