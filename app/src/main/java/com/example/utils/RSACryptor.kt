package com.example.utils

import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.*
import java.security.cert.CertificateException
import java.security.spec.RSAKeyGenParameterSpec
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.security.auth.x500.X500Principal

/**
 * RSA 암호화 객체
 * ref: https://gooners0304.tistory.com/m/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-KeyStore-%EB%B9%84%EB%8C%80%EC%B9%AD-%EC%95%94%ED%98%B8%ED%99%94
 */
class RSACryptor {
    private val TAG = "RSACryptor"
    private val CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding"
    private val KEYSTORE_TYPE = "AndroidKeyStore"
    private val ALGORITHM = "RSA"
    private val CHARTER_SET = "UTF-8"
    private val KEY_SIZE = 2048

    private var keyEntry // 비대칭 암호화(공개키) 알고리즘 호출 상수
            : KeyStore.Entry? = null

    // Android KeyStore 시스템에서는 암호화 키를 컨테이너(시스템만이 접근 가능한 곳)에 저장해야하므로 이 키를 기기에서 추출해내기가 더 어려움
    fun init(context: Context) {
        try { // AndroidKeyStore 정확하게 기입
            val ks: KeyStore = KeyStore.getInstance(KEYSTORE_TYPE)
            ks.load(null)
            // KeyStore에 해당 패키지 네임이 등록되어있는가?
            if (!ks.containsAlias(context.packageName)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    initAndroidM(context.packageName)
                } else {
                    initAndroidK(context)
                }
            }
            keyEntry = ks.getEntry(
                context.packageName, null
            )
        } catch (e: KeyStoreException) {
            Log.e(TAG, "Initialize fail", e)
        } catch (e: IOException) {
            Log.e(TAG, "Initialize fail", e)
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Initialize fail", e)
        } catch (e: CertificateException) {
            Log.e(TAG, "Initialize fail", e)
        } catch (e: UnrecoverableEntryException) {
            Log.e(TAG, "Initialize fail", e)
        }
    }

    // API Level 23 이상(마쉬멜로우) 개인키 생성
    private fun initAndroidM(alias: String) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // AndroidKeyStore 정확하게 기입
                val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, KEYSTORE_TYPE
                )
                kpg.initialize(
                    KeyGenParameterSpec.Builder(
                        alias,
                        KeyProperties.PURPOSE_ENCRYPT or
                                KeyProperties.PURPOSE_DECRYPT
                    )
                        .setAlgorithmParameterSpec(
                            RSAKeyGenParameterSpec(
                                KEY_SIZE, RSAKeyGenParameterSpec.F4
                            )
                        )
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1
                        )
                        .setDigests(
                            KeyProperties.DIGEST_SHA512,
                            KeyProperties.DIGEST_SHA384,
                            KeyProperties.DIGEST_SHA256
                        )
                        .setUserAuthenticationRequired(false)
                        .build()
                )
                kpg.generateKeyPair()
                Log.d(TAG, "RSA Initialize")
            }
        } catch (e: GeneralSecurityException) {
            Log.e(
                TAG, "이 디바이스는 관련 알고리즘을 지원하지 않음.", e
            )
        }
    }

    // API Level 19 이상(킷캣) 개인키 생성
    private fun initAndroidK(context: Context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 유효성 기간
                val start: Calendar = Calendar.getInstance()
                val end: Calendar = Calendar.getInstance()
                end.add(Calendar.YEAR, 100)

                // AndroidKeyStore 정확하게 기입
                val kpg: KeyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, KEYSTORE_TYPE)
                kpg.initialize(
                    KeyPairGeneratorSpec.Builder(context)
                        .setKeySize(KEY_SIZE)
                        .setAlias(context.packageName)
                        .setSubject(
                            X500Principal("CN=myKey")
                        )
                        .setSerialNumber(BigInteger.ONE)
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()
                )
                kpg.generateKeyPair()
                Log.d(TAG, "RSA Initialize")
            }
        } catch (e: GeneralSecurityException) {
            Log.e(
                TAG, "이 디바이스는 관련 알고리즘을 지원하지 않음.", e
            )
        }
    }

    // 문자열 위주로 작업하기 때문에 반드시 String형이나
    // toString을 쓸 것!! 암호화(set)
    fun encrypt(plain: String): String {
        return try {
            keyEntry?.let { keyStoreEntry ->
                val bytes = plain.toByteArray(charset(CHARTER_SET))
                val cipher: Cipher = Cipher.getInstance(CIPHER_ALGORITHM)

                // Public Key로 암호화
                cipher.init(
                    Cipher.ENCRYPT_MODE,
                    (keyStoreEntry as KeyStore.PrivateKeyEntry).certificate.publicKey
                )
                val encryptedBytes: ByteArray = cipher.doFinal(bytes)
                Log.d(
                    TAG, "Encrypted Text : " + String(
                        Base64.encode(
                            encryptedBytes, Base64.DEFAULT
                        )
                    )
                )
                String(
                    Base64.encode(
                        encryptedBytes, Base64.DEFAULT
                    )
                )
            } ?: ""
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        } catch (e: BadPaddingException) {
            Log.e(TAG, "Encrypt fail", e)
            plain
        }
    }

    // 복호화(get) 데이터가 유출되더라도 복호화는 이 로직에서만 가능함
    fun decrypt(encryptedText: String): String {
        return try {
            keyEntry?.let { keyStoreEntry ->
                val cipher: Cipher = Cipher.getInstance(CIPHER_ALGORITHM)

                // Private Key로 복호화
                cipher.init(
                    Cipher.DECRYPT_MODE, (keyStoreEntry as KeyStore.PrivateKeyEntry?)!!.privateKey
                )
                val base64Bytes = encryptedText.toByteArray(charset(CHARTER_SET))
                val decryptedBytes: ByteArray = Base64.decode(base64Bytes, Base64.DEFAULT)
                Log.d(
                    TAG, "Decrypted Text : " + String(
                        cipher.doFinal(decryptedBytes)
                    )
                )
                String(
                    cipher.doFinal(decryptedBytes)
                )
            } ?: ""
        } catch (e: NoSuchAlgorithmException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        } catch (e: NoSuchPaddingException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        } catch (e: InvalidKeyException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        } catch (e: UnsupportedEncodingException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        } catch (e: BadPaddingException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        } catch (e: IllegalBlockSizeException) {
            Log.e(TAG, "Decrypt fail", e)
            encryptedText
        }
    }
}