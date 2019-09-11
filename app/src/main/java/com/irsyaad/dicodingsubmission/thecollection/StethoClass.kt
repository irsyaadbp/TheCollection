package com.irsyaad.dicodingsubmission.thecollection

import android.app.Application

import com.facebook.stetho.Stetho

class StethoClass : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}