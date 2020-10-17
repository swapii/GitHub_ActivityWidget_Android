package github.activity.ui.widget

import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class WidgetModule(
    private val getUserActivity: Provider<GetUserActivity>,
) {

    @Provides
    fun provideGetUserActivity(): GetUserActivity =
        getUserActivity.get()

}
