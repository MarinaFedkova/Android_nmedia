package ru.netology.nmedia.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WorkManagerModule {
//    @Provides
//    @Singleton
//    fun provideWorkManager(
//        @ApplicationContext context: Context,
//        workerFactory: DependencyWorkerFactory,
//    ): WorkManager {
//        WorkManager.initialize(
//            context, Configuration.Builder()
//                .setWorkerFactory(workerFactory)
//                .build()
//        )
//        return WorkManager.getInstance(context)
//    }
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)

}