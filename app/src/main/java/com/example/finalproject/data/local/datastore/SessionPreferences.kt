package com.example.finalproject.data.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object SessionPreferences {
    val KEY_ID_USER = stringPreferencesKey("key_id_user")
    val KEY_USER_NAME = stringPreferencesKey("key_user_name")
    val KEY_USER_EMAIL = stringPreferencesKey("key_user_email")
    val KEY_TOKEN = stringPreferencesKey("key_token")
}