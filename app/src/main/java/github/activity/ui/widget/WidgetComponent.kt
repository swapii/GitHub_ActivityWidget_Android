package github.activity.ui.widget

import dagger.Component

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
