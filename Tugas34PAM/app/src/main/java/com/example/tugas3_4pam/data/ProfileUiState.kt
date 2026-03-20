package com.example.tugas3_4pam.data

data class ProfileUiState(
    val profile: Profile = Profile(),
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false,
    val editNameInput: String = "",
    val editBioInput: String = ""
)