package github.activity.feature.widget

import dagger.Component
import github.activity.feature.widget.provider.ActivityWidgetProvider

@Component(
    modules = [
        WidgetModule::class,
    ],
)
interface WidgetComponent {

    fun inject(x: ActivityWidgetProvider)

    interface Provider {

        fun getWidgetComponent(): WidgetComponent

    }

}
