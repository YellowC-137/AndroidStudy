/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.android.codelabs.paging.data

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * 데이터 모델
 */
data class Article(
    val id: Int,
    val title: String,
    val description: String,
    val created: LocalDateTime,
)

val Article.createdText: String get() = articleDateFormatter.format(created)

private val articleDateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")