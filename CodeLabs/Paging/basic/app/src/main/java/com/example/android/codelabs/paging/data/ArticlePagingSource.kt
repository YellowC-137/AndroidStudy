package com.example.android.codelabs.paging.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.time.LocalDateTime

//paging data 에게 데이터 제공
//페이징 키의 유형,로드될 데이터의 유형 설정
class ArticlePagingSource:PagingSource<Int,Article>() {
    private val firstArticleCreatedTime = LocalDateTime.now()

    private fun ensureValidkey(key:Int) = max(STARTING_KEY,key)
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    } //처음 키를 가져옴, startkey

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val start = params.key?: STARTING_KEY
        val range = start.until(start+params.loadSize)

        return LoadResult.Page(
            data = range.map{
                number -> Article(
                id = number,
                title = "Article $number",
                description = "Description $number",
                created = firstArticleCreatedTime.minusDays(number.toLong())
                )
            },
            prevKey = when()
        )
    }//



}