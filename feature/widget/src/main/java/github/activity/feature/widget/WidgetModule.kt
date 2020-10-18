package github.activity.feature.widget

import dagger.Module
import dagger.Provides
import github.activity.feature.widget.interactor.GetUserActivity
import javax.inject.Provider

@Module
class WidgetModule(
    private val getUserActivity: Provider<GetUserActivity>,
) {

    @Provides
    fun provideGetUserActivity(): GetUserActivity =
        getUserActivity.get()

}
