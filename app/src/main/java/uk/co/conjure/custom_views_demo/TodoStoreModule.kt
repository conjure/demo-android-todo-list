package uk.co.conjure.custom_views_demo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TodoStoreModule {
    @Singleton
    @Binds
    abstract fun bindTodoStore(impl: SharedPrefTodoStore): TodoStore
}