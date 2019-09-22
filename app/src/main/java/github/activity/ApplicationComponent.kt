package github.activity

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import github.activity.ui.widget.ActivityWidgetProvider

@Component(modules = [ApplicationComponent.ApplicationModule::class])
interface ApplicationComponent {

    fun inject(x: UpdateService)
    fun inject(x: ActivityWidgetProvider)

    @Module
    class ApplicationModule(val context: Context) {

        @Provides
        fun context(): Context = context.applicationContext

    }

}
