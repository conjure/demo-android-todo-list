package uk.co.conjure.custom_views_demo

import android.app.Application

class App : Application() {
    val viewModelFactory by lazy { ViewModelFactory() }
}