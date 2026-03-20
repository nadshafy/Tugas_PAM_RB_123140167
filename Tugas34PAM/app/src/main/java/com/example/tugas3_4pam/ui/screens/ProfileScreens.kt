package com.example.tugas3_4pam.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas3_4pam.ui.components.*
import com.example.tugas3_4pam.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    // Collect state dari ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Toggle Dark Mode di pojok kanan atas
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = uiState.isDarkMode,
                onCheckedChange = { viewModel.toggleDarkMode(it) } //
            )
        }

        ProfileHeader(name = uiState.profile.name, bio = uiState.profile.bio)

        if (uiState.isEditing) {
            EditProfileForm(
                nameInput = uiState.editNameInput,
                onNameChange = { viewModel.updateNameInput(it) },
                bioInput = uiState.editBioInput,
                onBioChange = { viewModel.updateBioInput(it) },
                onSave = { viewModel.saveProfile() },
                onCancel = { viewModel.toggleEditMode() }
            )
        } else {
            ProfileCard(profile = uiState.profile)

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(onClick = { viewModel.toggleEditMode() }) {
                    Text("Edit Profile")
                }
            }
        }
    }
}