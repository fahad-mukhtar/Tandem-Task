package com.example.community

import com.example.community.data.networkLayer.CommunityApiService

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CommunityApiTest {
    private lateinit var service:CommunityApiService
    private lateinit var server:MockWebServer
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(CommunityApiService::class.java)
    }

    private fun enqueueMockResponse(name:String){
        val input = javaClass.classLoader!!.getResourceAsStream(name)
        val source = input.source().buffer()
        val response = MockResponse()
        response.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(response)
    }

    @Test
    fun getCommunity_sentRequest_received(){
        runBlocking {
            enqueueMockResponse("communityresponse.json")
            val response = service.getCommunity(1)
            val request = server.takeRequest()
            assertThat(response).isNotNull()
            assertThat(request.path).isEqualTo("community_{page}.json")

        }
    }

    @Test
    fun getCommunity_pageSize(){
        runBlocking {
            enqueueMockResponse("communityresponse.json")
            val responseBody = service.getCommunity(1)
            val communityList = responseBody!!.response
            assertThat(communityList.size).isEqualTo(20)

        }
    }


    @Test
    fun checkContent(){
        runBlocking {
            enqueueMockResponse("communityresponse.json")
            val responseBody = service.getCommunity(1)
            val communityList = responseBody!!.response
            val community = communityList[0]
            assertThat(community.firstName).isEqualTo("Tobi")
            assertThat(community.id).isEqualTo(1)
            assertThat(community.pictureUrl).isEqualTo("https://tandem2019.web.app/img/pic1.png")


        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}