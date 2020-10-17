package github.activity

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import github.activity.feature.widget.provider.ActivityWidgetProvider
import github.activity.feature.widget.provider.WidgetOptions

class GetAllGitHubUsersUsedInWidgets(
    private val context: Context,
    private val widgetManager: AppWidgetManager,
) {

    operator fun invoke(): Set<String> =
        widgetManager.getAllWidgetsOptions(componentName)
            .map { it.username }
            .toSet()

    private val componentName: ComponentName
        get() = ComponentName(context, ActivityWidgetProvider::class.java)

    private fun AppWidgetManager.getAllWidgetsOptions(
        component: ComponentName,
    ): Sequence<WidgetOptions> =
        getAppWidgetIds(component)
            .asSequence()
            .map { getAppWidgetOptions(it) }
            .map { WidgetOptions(it) }

}
