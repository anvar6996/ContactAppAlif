package uz.udevs.contactapp.di

import android.app.Application
import androidx.room.Room
import dagger.Binds
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
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): ContactsDatabase {
        return Room.databaseBuilder(
            app, ContactsDatabase::class.java, ContactsDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun getContactsRepository(
        contactsDatabase: ContactsDatabase,
    ): ContactsRepository {
        return ContactsRepositoryImpl(contactsDatabase.contactsDao)
    }

}