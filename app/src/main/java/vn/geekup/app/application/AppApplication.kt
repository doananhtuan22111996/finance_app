package vn.geekup.app.application

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import vn.geekup.app.BuildConfig
import vn.geekup.app.di.AppModules
import vn.geekup.app.di.DomainModules
import vn.geekup.data.DataModule

class AppApplication : Application() {
    companion object {
        private val instanceLock = Any()
        var instance: AppApplication? = null
            set(value) {
                synchronized(instanceLock) {
                    field = value
                }
            }
    }

    private val defaultLifecycleObserver = object : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            //TODO your code here
        }

        override fun onStop(owner: LifecycleOwner) {
            super.onStop(owner)
            //TODO your code here
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        setupTimber()
        startKoin()
        ProcessLifecycleOwner.get().lifecycle.addObserver(defaultLifecycleObserver)
    }

    private fun setupTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun startKoin() {
        startKoin {
            // use Koin logger
            if (BuildConfig.DEBUG) {
                androidLogger()
            }
            androidContext(this@AppApplication)
            // declare modules
            modules(
                AppModules.applicationModules,
                AppModules.viewModelModules,
                DataModule.localModules,
                DataModule.remoteModules,
                DataModule.repositoryModules,
                DomainModules.useCaseModules,
            )
        }
    }
}