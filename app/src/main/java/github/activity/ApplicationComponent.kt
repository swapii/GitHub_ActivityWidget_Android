package github.activity

import android.appwidget.AppWidgetManager
import android.content.Context
import com.github.GetUserActivity
import com.github.GitHubModule
import dagger.Component
import dagger.Module
import dagger.Provides
import github.activity.dao.DaoMaster
import github.activity.dao.DaoMaster.DevOpenHelper
import github.activity.dao.DaoSession
import github.activity.feature.widget.WidgetModule
import github.activity.feature.work.WorkerFactory
import javax.inject.Provider

@Component(
    modules = [
        ApplicationComponent.ApplicationModule::class,
        GitHubModule::class,
    ],
)
interface ApplicationComponent {

    fun inject(x: App)

    @Module
    class ApplicationModule(
        private val context: Context,
    ) {

        @Provides
        fun provideWidgetModule(
            getUserActivity: Provider<github.activity.feature.widget.interactor.GetUserActivity>,
        ): WidgetModule =
            WidgetModule(getUserActivity)

        @Provides
        fun provideGetUserActivity(
            impl: GetUserActivityFromLocalSource,
        ): github.activity.feature.widget.interactor.GetUserActivity =
            impl

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

        @Provides
        fun provideDaoMaster(): DaoMaster {
            val helper = DevOpenHelper(context, "main", null)
            val db = helper.writableDatabase
            return DaoMaster(db)
        }

        @Provides
        fun provideDaoSession(
            daoMaster: DaoMaster,
        ): DaoSession =
            daoMaster.newSession()

        @Provides
        fun provideGetUserActivityFromLocalSource(
            daoSession: DaoSession,
        ): GetUserActivityFromLocalSource =
            GetUserActivityFromLocalSource(
                daoSession,
            )

        @Provides
        fun provideStorage(
            daoSessionProvider: Provider<DaoSession>,
        ): Storage =
            Storage(daoSessionProvider)

    }

}
