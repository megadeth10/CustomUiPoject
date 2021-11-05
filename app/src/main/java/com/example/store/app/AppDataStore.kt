package com.deleo.c2cmarketplace.store

import android.app.Application
import android.content.Context
import com.example.utils.RSACryptor
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers


/**
 * 앱 사용에 필요한 data store
 */
/**
 * 심플한 암호화 방법을 적용해야 한다.
 */
class AppDataStore(val application: Application) {
    private val fileName = "dataPreference"

    init {

    }

    /**
     * user location key
     */
    private val KEY_A = "AAAAAAAAAA"
    var location: UserPosition? = null
        private set

    /**
     * 장바구니 Unchecked 확인
     */
    val AA1 = "AA1"

    private var rSACryptor = RSACryptor()

    init {
        rSACryptor.init(application.applicationContext)
    }

    private fun getPreference() =
        application.applicationContext.getSharedPreferences(fileName, Context.MODE_PRIVATE)

    fun init() {
        this.initLocation()
    }

    /**
     * 사용자 위치값 초기화
     */
    private fun initLocation() {
        if (this.location == null) {
            this.loadDecryptData(KEY_A, "") {
                if (it.isNotEmpty()) {
                    val jsonText = rSACryptor.decrypt(it)
                    this.location = Gson().fromJson(jsonText, UserPosition::class.java)
                } else {
                    this.location = UserPosition()
                }
            }
        }
    }


    /**
     * 사용자 위치값 저장
     */
    fun updateLocation(latitude: Double, longitude: Double, displayName: String) {
        if (this.location == null) {
            this.location = UserPosition(b = latitude, a = longitude, n = displayName)
        } else {
            this.location!!.a = longitude
            this.location!!.b = latitude
            this.location!!.n = displayName
        }
        val text = Gson().toJson(this.location)
        this.updateEncryptData(KEY_A, text)
    }

    /**
     * local store에 암호화 문자열 저장
     */
    private fun updateEncryptData(keyName: String, dataString: String) {
        val encryptText = this.rSACryptor.encrypt(dataString)
        this.updateData(keyName, encryptText)
    }

    /**
     * local store에 암호화 문자열 load
     */
    private fun loadDecryptData(keyName: String, defaultValue: String, callback: (String) -> Unit) {
        this.loadString(keyName, defaultValue, callback)
    }

    /**
     * local store 에 데이터 저장
     */
    fun updateData(
        keyName: String,
        data: Any,
        completeCallback : () -> Unit = {}
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val preferences = getPreference().edit()
                when (data) {
                    is String -> preferences.putString(keyName, data)
                    is Int -> preferences.putInt(keyName, data)
                    is Long -> preferences.putLong(keyName, data)
                    is Float -> preferences.putFloat(keyName, data)
                    is Boolean -> preferences.putBoolean(keyName, data)
                }
                preferences.commit()
                completeCallback()
            }
    }

    /**
     * local store 에 문자열 load
     */
    fun loadString(
        keyName: String,
        defaultValue: String,
        callback: (String) -> Unit
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val encryptText = getPreference().getString(keyName, "")
                callback(encryptText ?: defaultValue)
            }
    }

    /**
     * local store 에 문자열 load
     */
    fun loadLong(
        keyName: String,
        defaultValue: Long,
        callback: (Long) -> Unit
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val encryptText = getPreference()
                    .getLong(keyName, defaultValue)
                callback(encryptText ?: defaultValue)
            }
    }

    /**
     * local store 에 문자열 load
     */
    fun loadInt(
        keyName: String,
        defaultValue: Int,
        callback: (Int) -> Unit
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val encryptText = getPreference().getInt(keyName, defaultValue)
                callback(encryptText ?: defaultValue)
            }
    }

    /**
     * local store 에 문자열 load
     */
    fun loadFloat(
        keyName: String,
        defaultValue: Float,
        callback: (Float) -> Unit
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val encryptText = getPreference()
                    .getFloat(keyName, defaultValue)
                callback(encryptText ?: defaultValue)
            }
    }

    /**
     * local store 에 문자열 load
     */
    fun loadBoolean(
        keyName: String,
        defaultValue: Boolean,
        callback: (Boolean) -> Unit
    ) {
        Single.just(true)
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe { t: Boolean ->
                val encryptText = getPreference()
                    .getBoolean(keyName, defaultValue)
                callback(encryptText ?: defaultValue)
            }
    }

    /**
     * 사용자 지도 위치값 객체
     * @param a longitude 경도
     * @param b latitude 위도
     * @param n 설정된 지역 이름
     */
    class UserPosition(
        var a: Double? = null,
        var b: Double? = null,
        var n: String = ""
    )
}