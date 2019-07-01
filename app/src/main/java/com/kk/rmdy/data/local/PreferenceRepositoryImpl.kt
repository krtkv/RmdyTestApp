package com.kk.rmdy.data.local

import android.content.SharedPreferences

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
class PreferenceRepositoryImpl(private val prefs: SharedPreferences) : PreferenceRepository {

    override fun saveTokens(authToken: String?, sessionToken: String?) {
        setAuthToken(authToken)
        setSessionToken(sessionToken)
    }

    private fun setAuthToken(value: String?) =
        prefs.edit().putString(PreferenceRepository.PrefKeys.AuthToken.key, value).commit()

    private fun setSessionToken(value: String?) =
        prefs.edit().putString(PreferenceRepository.PrefKeys.SessionToken.key, value).commit()

    override fun getAuthToken(): String =
        prefs.getString(PreferenceRepository.PrefKeys.AuthToken.key, "") ?: ""

    override fun getSessionToken(): String =
        prefs.getString(PreferenceRepository.PrefKeys.SessionToken.key, "") ?: ""
}