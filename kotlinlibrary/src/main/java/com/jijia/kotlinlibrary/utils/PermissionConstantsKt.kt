package com.jijia.kotlinlibrary.utils

import android.Manifest.permission
import android.os.Build
import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.util.*

object PermissionConstantsKt {

    val CALENDAR = "CALENDAR"
    val CAMERA = "CAMERA"
    val CONTACTS = "CONTACTS"
    val LOCATION = "LOCATION"
    val MICROPHONE = "MICROPHONE"
    val PHONE = "PHONE"
    val SENSORS = "SENSORS"
    val SMS = "SMS"
    val STORAGE = "STORAGE"

    private val GROUP_CALENDAR = arrayOf(
        permission.READ_CALENDAR, permission.WRITE_CALENDAR
    )
    private val GROUP_CAMERA = arrayOf(
        permission.CAMERA
    )
    private val GROUP_CONTACTS = arrayOf(
        permission.READ_CONTACTS, permission.WRITE_CONTACTS, permission.GET_ACCOUNTS
    )
    private val GROUP_LOCATION = arrayOf(
        permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION
    )
    private val GROUP_MICROPHONE = arrayOf(
        permission.RECORD_AUDIO
    )
    private val GROUP_PHONE = arrayOf(
        permission.READ_PHONE_STATE, permission.READ_PHONE_NUMBERS, permission.CALL_PHONE,
        permission.READ_CALL_LOG, permission.WRITE_CALL_LOG
    )

    private val GROUP_SENSORS = arrayOf(
        permission.BODY_SENSORS
    )
    private val GROUP_SMS = arrayOf(
        permission.SEND_SMS, permission.RECEIVE_SMS, permission.READ_SMS,
        permission.RECEIVE_WAP_PUSH, permission.RECEIVE_MMS
    )
    private val GROUP_STORAGE = arrayOf(
        permission.READ_EXTERNAL_STORAGE, permission.WRITE_EXTERNAL_STORAGE
    )


    fun getPermissions(permission: String): Array<String> {
        if (permission == null) return arrayOf()
        when (permission) {
            CALENDAR -> return GROUP_CALENDAR
            CAMERA -> return GROUP_CAMERA
            CONTACTS -> return GROUP_CONTACTS
            LOCATION -> return GROUP_LOCATION
            MICROPHONE -> return GROUP_MICROPHONE
            PHONE -> return GROUP_PHONE
            SENSORS -> return GROUP_SENSORS
            SMS -> return GROUP_SMS
            STORAGE -> return GROUP_STORAGE
        }
        return arrayOf(permission)
    }

}