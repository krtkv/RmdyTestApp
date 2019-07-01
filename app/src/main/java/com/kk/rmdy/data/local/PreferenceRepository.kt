package com.kk.rmdy.data.local

/**
 * Created by Kirill Kartukov on 27.06.2019.
 */
interface PreferenceRepository {

    fun saveTokens(authToken: String?, sessionToken: String?)

    fun getAuthToken(): String

    fun getSessionToken(): String

    enum class PrefKeys(val key: String) {
        RmdyPrefs("RmdyPrefs"),
        AuthToken("AuthToken"),
        SessionToken("SessionToken")
    }

}