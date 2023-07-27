package uz.udevs.contactapp.data.base

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.udevs.contactapp.data.dao.ContactsDao
import uz.udevs.contactapp.data.entity.Contact

@Database(
    entities = [Contact::class],
    version = 1, exportSchema = false
)
abstract class ContactsDatabase : RoomDatabase() {

    abstract val contactsDao: ContactsDao

    companion object {
        const val DATABASE_NAME = "contact_app_db"
    }

}