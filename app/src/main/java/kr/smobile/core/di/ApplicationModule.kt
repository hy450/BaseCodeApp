package kr.smobile.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kr.smobile.BaseApplication
import kr.smobile.core.api.WeatherRestApiService
import kr.smobile.core.db.BaseCodeDb
import kr.smobile.core.db.WeatherDao
import kr.smobile.sample.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    fun provideContext(application: BaseApplication): Context {
        return application.applicationContext
    }

//    @Singleton
//    @Provides
//    fun provideApplicationContext(application: BaseApplication): Context = application

    @Named("openweather")
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/${WeatherRestApiService.version}/")
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication) : BaseCodeDb {
        return Room.databaseBuilder(app, BaseCodeDb::class.java, "BaseCode.db")
            //.addCallback(DBConfigure.rdc)
            //.addMigrations(DBConfigure.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao( db: BaseCodeDb ) : WeatherDao = db.weatherDao()


    private fun createClient(): OkHttpClient {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }


}