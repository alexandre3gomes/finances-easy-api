package com.finances.utils

import org.springframework.security.crypto.codec.Hex
import java.nio.charset.Charset
import java.security.MessageDigest
import java.util.*

object EncryptUtils {

    @JvmStatic
	fun hashPassword(password: String): Optional<String> {
        return try {
            val dig = MessageDigest.getInstance("MD5")
            dig.reset()
            dig.update(password.toByteArray(Charset.forName("UTF8")))
            val arrBytes = dig.digest()
            Optional.of(String(Hex.encode(arrBytes)))
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println(hashPassword("admin").get())
    }
}