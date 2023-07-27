package uz.udevs.contactapp.domain

import kotlinx.coroutines.flow.Flow
import uz.udevs.contactapp.data.dao.ContactsDao
import uz.udevs.contactapp.data.entity.Contact
import javax.inject.Inject

class ContactsRepositoryImpl @Inject constructor(private val contactsDao: ContactsDao) :
    ContactsRepository {

    override fun getAllContactsStream(): Flow<List<Contact>> = contactsDao.getAllContacts()

    override fun getContactStream(id: Int): Flow<Contact?> = contactsDao.getContact(id)

    override suspend fun insertContact(contact: Contact) = contactsDao.insert(contact)

    override suspend fun deleteContact(contact: Contact) = contactsDao.delete(contact)

    override suspend fun updateContact(contact: Contact) = contactsDao.update(contact)
}