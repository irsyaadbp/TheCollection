package com.irsyaad.dicodingsubmission.thecollection.model.service

import android.content.Intent
import android.widget.RemoteViewsService

import com.irsyaad.dicodingsubmission.thecollection.adapter.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext)
    }
}
