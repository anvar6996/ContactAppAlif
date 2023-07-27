package uz.udevs.contactapp.presentation.contact.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uz.udevs.contactapp.domain.ContactsRepository
import uz.udevs.contactapp.presentation.contact.ContactUiState
import uz.udevs.contactapp.presentation.contact.isValid
import uz.udevs.contactapp.presentation.contact.toContact
import uz.udevs.contactapp.presentation.contact.toContactUiState
import javax.inject.Inject


@HiltViewModel
class EditScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val contactsRepository: ContactsRepository
) : ViewModel() {

    var contactUiState by mutableStateOf(ContactUiState())
        private set

    private val contactId: Int = checkNotNull(savedStateHandle[EditScreenDestination.contactIdArg])

    init {
        viewModelScope.launch {
            contactUiState = contactsRepository.getContactStream(contactId).filterNotNull().first()
                .toContactUiState(actionEnable = true)
        }
    }

    fun onEvent(intent: EditContactIntent) {
        when (intent) {
            is EditContactIntent.EditContact -> {

                updateContact()
            }

            EditContactIntent.DeleteTask -> TODO()
        }

    }

    fun updateUiState(newContactUiState: ContactUiState) {
        contactUiState = newContactUiState.copy(actionEnable = newContactUiState.isValid())
    }

    private fun updateContact() {
        if (contactUiState.isValid()) {
            viewModelScope.launch {
                contactsRepository.updateContact(contactUiState.toContact())
            }
        }
    }
}


