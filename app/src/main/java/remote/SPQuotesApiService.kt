package remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import model.Character
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


private const val BASE_URL = "https://southparkquotes.onrender.com/"

private val client: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .build()
        chain.proceed(newRequest)
    }.build()

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface SouthParkApiServiceQNumber { // Api Call für die Anzahl der quotes.

    @GET("v1/quotes/{number}")
    suspend fun getQuotesNumbers(@Path("number") format: String): List<Character>

    @GET("v1/quotes/search/{searchTerm}")
    suspend fun getCharacterAndQuotes(@Query("searchTerm") characterName: String) : List<Character>

    object UserApi {
        val retrofitService: SouthParkApiServiceQNumber by lazy {
            retrofit.create(SouthParkApiServiceQNumber::class.java)
        }
    }
}
