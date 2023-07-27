package uz.udevs.contactapp.domain

import kotlinx.coroutines.flow.Flow
import uz.udevs.contactapp.data.entity.Contact

interface ContactsRepository {
    fun getAllContactsStream(): Flow<List<Contact>>

    fun getContactStream(id: Int): Flow<Contact?>

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun updateContact(contact: Contact)
}