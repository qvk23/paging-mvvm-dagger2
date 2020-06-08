package gst.trainingcourse.pagingimpl.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import gst.trainingcourse.pagingimpl.App
import gst.trainingcourse.pagingimpl.di.module.ActivityBindingModule
import gst.trainingcourse.pagingimpl.di.module.NetworkModule
import gst.trainingcourse.pagingimpl.di.module.RepositoryModule
import gst.trainingcourse.pagingimpl.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    NetworkModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
interface MainComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): MainComponent
    }
}
