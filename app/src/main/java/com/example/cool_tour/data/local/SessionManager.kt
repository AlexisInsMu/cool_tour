package com.example.cool_tour.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "cool_tour_session")

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")

    val tokenFlow: Flow<String?> = context.dataStore.data.map { it[TOKEN_KEY] }

    suspend fun guardarSesion(token: String, nombre: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[USER_NAME_KEY] = nombre
        }
    }

    suspend fun obtenerToken(): String? = context.dataStore.data.first()[TOKEN_KEY]

    suspend fun cerrarSesion() {
        context.dataStore.edit { it.clear() }
    }
}