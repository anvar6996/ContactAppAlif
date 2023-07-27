package uz.udevs.contactapp.presentation.contact.details

import uz.udevs.contactapp.presentation.contact.ContactUiState

sealed class DetailsContactIntent {
    object DeleteTask : DetailsContactIntent()
}