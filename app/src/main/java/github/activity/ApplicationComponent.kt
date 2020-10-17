package github.activity

import android.appwidget.AppWidgetManager
import android.content.Context
import com.github.GetUserActivity
import com.github.GitHubModule
import dagger.Component
import dagger.Module
import dagger.Provides
import github.activity.feature.work.WorkerFactory
import github.activity.ui.widget.ActivityWidgetProvider
import javax.inject.Provider

@Component(
    modules = [
        ApplicationComponent.ApplicationModule::class,
        GitHubModule::class,
    ],
)
interface ApplicationComponent {

    fun inject(x: App)
    fun inject(x: ActivityWidgetProvider)

    @Module
    class ApplicationModule(val context: Context) {

        @Provides
        fun context(): Context = context.applicationContext

        @Provides
        fun appWidgetManager(context: Context): AppWidgetManager =
            AppWidgetManager.getInstance(context)

        @Provides
        fun getAllGitHubUsersUsedInWidgets(
            context: Context,
            appWidgetManager: AppWidgetManager,
        ): GetAllGitHubUsersUsedInWidgets =
            GetAllGitHubUsersUsedInWidgets(
                context,
                appWidgetManager
            )

        @Provides
        fun provideWorkerFactory(
            getAllGitHubUsersUsedInWidgets: Provider<GetAllGitHubUsersUsedInWidgets>,
            getUserActivity: Provider<GetUserActivity>,
            storage: Provider<Storage>,
        ): WorkerFactory =
            WorkerFactory(
                { getAllGitHubUsersUsedInWidgets.get() },
                { getUserActivity.get() },
                { storage.get() },
            )

    }

}
