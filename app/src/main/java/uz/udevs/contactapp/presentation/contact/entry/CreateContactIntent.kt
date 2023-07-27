package uz.udevs.contactapp.presentation.contact.entry

sealed class CreateContactIntent {
    object CreateContact : CreateContactIntent()
}