import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uz.udevs.contactapp.data.base.ContactsDatabase
import uz.udevs.contactapp.data.dao.ContactsDao
import uz.udevs.contactapp.data.entity.Contact
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ContactDaoTest {
    private lateinit var contactDao: ContactsDao
    private lateinit var contactsDatabase: ContactsDatabase
    private var contact1 = Contact(1, "Anvar Juraqulov")
    private var contact2 = Contact(2, "Rustam Akbaraliyev", 8745632054, "juraqulovanvar@mail.ru")

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        contactsDatabase = Room.inMemoryDatabaseBuilder(context, ContactsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = contactsDatabase.contactsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        contactsDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsContactIntoDB() = runBlocking {
        addOneContactToDb()
        val allContacts = contactDao.getAllContacts().first()
        assertEquals(allContacts[0], contact1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllContacts_returnsAllContactsFromDB() = runBlocking {
        addTwoContactsToDb()
        val allContacts = contactDao.getAllContacts().first()
        assertEquals(allContacts[0], contact1)
        assertEquals(allContacts[1], contact2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateContacts_updatesContactsInDB() = runBlocking {
        addTwoContactsToDb()
        contactDao.update(Contact(1, "Anvar Juraqulov", 1452036895))
        contactDao.update(Contact(2, "Rustam Akbaraliyev", 8745632054, "juanperezz@email.com"))
        val allContacts = contactDao.getAllContacts().first()
        assertEquals(allContacts[0], Contact(1, "Anvar Juraqulov", 1452036895))
        assertEquals(allContacts[1], Contact(2, "Rustam Akbaraliyev", 8745632054, "juanperezz@email.com"))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteContacts_deletesAllContactsFromDB() = runBlocking {
        contactDao.delete(contact1)
        contactDao.delete(contact2)
        val allContacts = contactDao.getAllContacts().first()
        Assert.assertTrue(allContacts.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetContact_returnsContactFromDB() = runBlocking {
        addOneContactToDb()
        val contact = contactDao.getContact(1)
        assertEquals(contact.first(), contact1)
    }

    private suspend fun addOneContactToDb() {
        contactDao.insert(contact1)
    }

    private suspend fun addTwoContactsToDb() {
        contactDao.insert(contact1)
        contactDao.insert(contact2)
    }
}