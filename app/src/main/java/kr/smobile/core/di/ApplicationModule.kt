package kr.smobile.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import kr.smobile.core.api.LiveDataCallAdapterFactory
import kr.smobile.core.api.WeatherRestApiService
import kr.smobile.core.db.BaseCodeDb
import kr.smobile.core.db.DBConfigure
import kr.smobile.core.db.WeatherDao
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module( includes = [ViewModelModule::class])
class ApplicationModule {

    @Singleton
    @Provides
    fun provideApplicationContext(application: Application): Context = application

    @Named("openweather")
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/${WeatherRestApiService.version}/")
            //.client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory( LiveDataCallAdapterFactory() )
            .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) : BaseCodeDb {
        return Room.databaseBuilder(app, BaseCodeDb::class.java, "BaseCode.db")
            //.addCallback(DBConfigure.rdc)
            //.addMigrations(DBConfigure.MIGRATION_1_2)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherDao( db: BaseCodeDb ) : WeatherDao = db.weatherDao()


}