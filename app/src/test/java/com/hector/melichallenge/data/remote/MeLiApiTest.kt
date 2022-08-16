package com.hector.melichallenge.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.hector.melichallenge.data.remote.dto.ProductDetailDto
import com.hector.melichallenge.data.remote.dto.SearchResultDto
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class MeLiApiTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val server = MockWebServer()
    private lateinit var api: MeLiApi
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun setUp() {
        server.start()

        val baseUrl = server.url("/").toString()

        val okHttpClient = OkHttpClient.Builder()
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(MeLiApi::class.java)
    }

    @Test
    fun `search product returns 200 OK`() {
        // Given
        val mockedResponse = MockResponseFileReader("searchProductResponse.json").content
        val expectedResponse = gson.fromJson(mockedResponse, SearchResultDto::class.java)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        // When
        val response = runBlocking {
            api.searchProduct(
                "test-query",
                0
            )
        }


        // Then
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `get product detail returns 200 OK`() {
        // Given
        val mockedResponse = MockResponseFileReader("getProductResponse.json").content
        val listType: Type = object : TypeToken<List<ProductDetailDto>>() {}.type
        val expectedResponse = gson.fromJson<List<ProductDetailDto>>(mockedResponse, listType)

        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockedResponse)
        )

        // When
        val response = runBlocking {
            api.getProductDetail(
                "test-item-id"
            )
        }


        // Then
        assertEquals(expectedResponse, response)
    }

    @Test
    fun `search product returns 400 Bad Request`() {
        // Given
        server.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        // When
        try {
            runBlocking {
                api.searchProduct(
                    "test-query",
                    0
                )
            }
        }
        catch (httpException: HttpException) {

            // Then
            assertEquals(400, httpException.code())

        }
    }

    @Test
    fun `get product detail returns 400 Bad Request`() {
        // Given
        server.enqueue(
            MockResponse()
                .setResponseCode(400)
        )

        // When
        try {
            runBlocking {
                api.getProductDetail(
                    "test-item-id"
                )
            }
        }
        catch (httpException: HttpException) {

            // Then
            assertEquals(400, httpException.code())

        }
    }
}