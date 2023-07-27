package uz.udevs.contactapp.presentation.contact.edit

import uz.udevs.contactapp.presentation.contact.ContactUiState

sealed class EditContactIntent {
    data class EditContact(val contactUiState: ContactUiState) : EditContactIntent()
    object DeleteTask : EditContactIntent()
}