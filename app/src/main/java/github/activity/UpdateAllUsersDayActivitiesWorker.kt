package github.activity

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.GetUserActivity
import kotlinx.coroutines.coroutineScope

class UpdateAllUsersDayActivitiesWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val getAllGitHubUsersUsedInWidgets: GetAllGitHubUsersUsedInWidgets,
    private val getUserActivity: GetUserActivity,
    private val storage: Storage,
) : CoroutineWorker(
    context,
    workerParameters,
) {

    override suspend fun doWork(): Result =
        coroutineScope {
            getAllGitHubUsersUsedInWidgets()
                .map { username ->
                    username to getUserActivity(username)
                }
                .forEach { (username, activity) ->
                    storage.updateUserActivity(username, activity)
                }
            Result.success()
        }

}
