package github.activity

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.GitHubClient
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UpdateAllUsersDayActivitiesWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val getAllGitHubUsersUsedInWidgets: GetAllGitHubUsersUsedInWidgets,
    private val storage: Storage,
) : CoroutineWorker(
    context,
    workerParameters,
) {

    override suspend fun doWork(): Result =
        suspendCoroutine {
            val client = GitHubClient()
            getAllGitHubUsersUsedInWidgets()
                .map { username ->
                    username to client.getUserActivity(username)
                        .toList()
                        .blockingGet()
                }
                .forEach { (username, activity) ->
                    storage.updateUserActivity(username, activity)
                }
            it.resume(Result.success())
        }

}
