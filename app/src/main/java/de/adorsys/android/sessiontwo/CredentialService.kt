package de.adorsys.android.sessiontwo

import android.content.Context
import android.content.ContextWrapper
import java.util.*

class CredentialService(val context: ContextWrapper) {

    private val preferencesKey = "de.adorsys.android.sessiontwo";
    private val usernamePreferencesKey = "username";
    private val passwordPreferencesKey = "password";

    fun isCorrect(credentials: Credentials): Boolean {
        val preferences = getSharedPreferences()

        val username = preferences.getString(usernamePreferencesKey, "")
        val password = preferences.getString(passwordPreferencesKey, "")

        return Objects.equals(credentials.username, username) && Objects.equals(credentials.password, password)
    }

    fun save(credentials: Credentials) {
        val preferences = getSharedPreferences()

        val editingPreferences = preferences.edit()
        editingPreferences.putString(usernamePreferencesKey, credentials.username)
        editingPreferences.putString(passwordPreferencesKey, credentials.password)

        editingPreferences.commit()
    }

    private fun getSharedPreferences() =
            context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)
}
