package uz.udevs.contactapp.data.dao

import androidx.room.*
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.Flow
import uz.udevs.contactapp.data.entity.Contact

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * from contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contact>

    @Query("SELECT * from contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>
}