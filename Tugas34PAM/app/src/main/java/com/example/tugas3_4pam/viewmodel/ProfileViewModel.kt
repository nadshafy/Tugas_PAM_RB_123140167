package com.example.tugas3_4pam.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugas3_4pam.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
    fun toggleDarkMode(isDark: Boolean) {
        _uiState.update { it.copy(isDarkMode = isDark) }
    }

    fun toggleEditMode() {
        _uiState.update { state ->
            state.copy(
                isEditing = !state.isEditing,
                editNameInput = if (!state.isEditing) state.profile.name else state.editNameInput,
                editBioInput = if (!state.isEditing) state.profile.bio else state.editBioInput
            )
        }
    }

    fun updateNameInput(newName: String) {
        _uiState.update { it.copy(editNameInput = newName) }
    }

    fun updateBioInput(newBio: String) {
        _uiState.update { it.copy(editBioInput = newBio) }
    }


    fun saveProfile() {
        _uiState.update { state ->
            state.copy(
                profile = state.profile.copy(
                    name = state.editNameInput,
                    bio = state.editBioInput
                ),
                isEditing = false
            )
        }
    }
}