package uz.udevs.contactapp.presentation.contact.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.udevs.contactapp.domain.ContactsRepository
import uz.udevs.contactapp.presentation.contact.ContactUiState
import uz.udevs.contactapp.presentation.contact.isValid
import uz.udevs.contactapp.presentation.contact.toContact
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    var contactUiState by mutableStateOf(ContactUiState())

    fun updateUiState(newContactUiState: ContactUiState) {
        contactUiState = newContactUiState.copy(actionEnable = newContactUiState.isValid())
    }

    fun onEvent(createContactIntent: CreateContactIntent) {
        when (createContactIntent) {
            is CreateContactIntent.CreateContact -> {
                saveContact()
            }
        }
    }

    private fun saveContact() {
        if (contactUiState.isValid()) {
            viewModelScope.launch {
                contactsRepository.insertContact(contactUiState.toContact())
            }
        }
    }
}
