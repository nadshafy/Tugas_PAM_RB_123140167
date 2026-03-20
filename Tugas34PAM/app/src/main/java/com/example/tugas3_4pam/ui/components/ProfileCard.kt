package com.example.tugas3_4pam.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas3_4pam.data.Profile

@Composable
fun ProfileCard(profile: Profile) {
    Card(
        modifier = Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoItem(icon = Icons.Default.Email, text = profile.email)
            InfoItem(icon = Icons.Default.Phone, text = profile.phone)
            InfoItem(icon = Icons.Default.LocationOn, text = profile.location)
        }
    }
}