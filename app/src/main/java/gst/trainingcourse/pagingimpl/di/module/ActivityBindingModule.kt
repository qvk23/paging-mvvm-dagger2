package gst.trainingcourse.pagingimpl.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import gst.trainingcourse.pagingimpl.MainActivity

@Module
abstract class ActivityBindingModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}