package com.example.finalproject.data.local.datastrore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.finalproject.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session_prefs")

class SessionDataStore(private val dataStore: DataStore<Preferences>) {

    val userSession: Flow<UserSession?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val idUser = preferences[SessionPreferences.KEY_ID_USER]
            val userName = preferences[SessionPreferences.KEY_USER_NAME]
            val userEmail = preferences[SessionPreferences.KEY_USER_EMAIL]

            if (!idUser.isNullOrBlank() && !userName.isNullOrBlank() && !userEmail.isNullOrBlank()) {
                UserSession(idUser = idUser, userName = userName, userEmail = userEmail)
            } else {
                null
            }
        }

    suspend fun saveSession(user: UserSession) {
        dataStore.edit { preferences ->
            preferences[SessionPreferences.KEY_ID_USER] = user.idUser
            preferences[SessionPreferences.KEY_USER_NAME] = user.userName
            preferences[SessionPreferences.KEY_USER_EMAIL] = user.userEmail
        }
    }

    suspend fun getUserId(): String? {
        return userSession.map { it?.idUser }.firstOrNull()
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}