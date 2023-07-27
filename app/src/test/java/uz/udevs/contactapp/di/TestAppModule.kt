package uz.udevs.contactapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.udevs.contactapp.data.base.ContactsDatabase
import uz.udevs.contactapp.domain.ContactsRepository
import uz.udevs.contactapp.domain.ContactsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {


    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ContactsDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            ContactsDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideEasyToDoRepository(db: ContactsDatabase): ContactsRepository {
        return ContactsRepositoryImpl(db.contactsDao)
    }

}