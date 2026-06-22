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
    // ── Keys usuario normal ──────────────────────────────
    private val TOKEN_KEY = stringPreferencesKey("auth_token")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")

    // ── Keys locatario ───────────────────────────────────
    private val LOCATARIO_TOKEN_KEY = stringPreferencesKey("locatario_token")
    private val LOCATARIO_ID_KEY = stringPreferencesKey("locatario_id")
    private val LOCATARIO_NOMBRE_KEY = stringPreferencesKey("locatario_nombre")

    // ── Usuario normal ───────────────────────────────────
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

    // ── Locatario ────────────────────────────────────────
    suspend fun guardarSesionLocatario(token: String, id: String, nombre: String) {
        context.dataStore.edit { prefs ->
            prefs[LOCATARIO_TOKEN_KEY] = token
            prefs[LOCATARIO_ID_KEY] = id
            prefs[LOCATARIO_NOMBRE_KEY] = nombre
        }
    }

    suspend fun obtenerTokenLocatario(): String? =
        context.dataStore.data.first()[LOCATARIO_TOKEN_KEY]

    suspend fun cerrarSesionLocatario() {
        context.dataStore.edit { prefs ->
            prefs.remove(LOCATARIO_TOKEN_KEY)
            prefs.remove(LOCATARIO_ID_KEY)
            prefs.remove(LOCATARIO_NOMBRE_KEY)
        }
    }

    suspend fun estaLogueadoComoLocatario(): Boolean =
        obtenerTokenLocatario() != null
}
