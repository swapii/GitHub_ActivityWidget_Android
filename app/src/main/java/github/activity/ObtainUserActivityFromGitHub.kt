package github.activity

import com.github.GetUserActivity
import com.github.OneDayActivityFromServer

class ObtainUserActivityFromGitHub(
    private val getUserActivity: GetUserActivity,
) {

    suspend operator fun invoke(
        username: String,
    ): List<OneDayActivityFromServer> =
        getUserActivity(username)

}
