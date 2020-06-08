package gst.trainingcourse.pagingimpl

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import gst.trainingcourse.pagingimpl.di.component.DaggerMainComponent

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerMainComponent.builder().application(this).build()
    }
}