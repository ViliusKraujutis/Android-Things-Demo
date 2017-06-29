package lt.andro.androidthingsdemo1.api

import lt.andro.androidthingsdemo1.api.entity.Forecast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Single

/**
 * @author Vilius Kraujutis
 * @since 2017-06-28
 */
interface OpenWeatherMapApi {

    @GET("/data/2.5/forecast")
    fun getForecast(@Query("id") city: Int = 593116, @Query("appid") appId: String = APP_ID): Single<Forecast>

    companion object RestApi {
        val api: OpenWeatherMapApi by lazy { create() }

        val APP_ID = "090b6488d88b9b3febb5bd2022c8bbb4"
        private fun create(): OpenWeatherMapApi {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create<OpenWeatherMapApi>(OpenWeatherMapApi::class.java)
        }
    }
}