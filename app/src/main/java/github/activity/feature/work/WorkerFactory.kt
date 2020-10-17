package github.activity.feature.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import github.activity.GetAllGitHubUsersUsedInWidgets
import github.activity.Storage
import github.activity.UpdateAllUsersDayActivitiesWorker

class WorkerFactory(
    private val getAllGitHubUsersUsedInWidgetsProvider: (() -> GetAllGitHubUsersUsedInWidgets),
    private val storageProvider: (() -> Storage),
) : androidx.work.WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        when (workerClassName) {

            UpdateAllUsersDayActivitiesWorker::class.qualifiedName ->
                UpdateAllUsersDayActivitiesWorker(
                    appContext,
                    workerParameters,
                    getAllGitHubUsersUsedInWidgetsProvider(),
                    storageProvider()
                )

            else -> throw IllegalArgumentException("Unsupported worker class [$workerClassName]")
        }

}
