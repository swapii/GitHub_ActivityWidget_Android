package github.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import com.github.GitHubModule;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

import github.activity.feature.widget.DaggerWidgetComponent;
import github.activity.feature.widget.WidgetComponent;
import github.activity.feature.widget.WidgetModule;
import github.activity.feature.work.WorkerFactory;
import okhttp3.OkHttpClient;

public class App extends Application implements Configuration.Provider, WidgetComponent.Provider {

    private static final Logger L = LoggerFactory.getLogger(App.class);

    @Inject
    protected Provider<WorkerFactory> workerFactoryProvider;

    @Inject
    protected WidgetModule widgetModule;

    private WidgetComponent widgetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        L.trace("App create");

        ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationComponent.ApplicationModule(this))
            .gitHubModule(new GitHubModule(new OkHttpClient()))
            .build();

        appComponent.inject(this);

        widgetComponent = DaggerWidgetComponent.builder()
            .widgetModule(widgetModule)
            .build();

    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
            .setWorkerFactory(workerFactoryProvider.get())
            .build();
    }

    @NotNull
    @Override
    public WidgetComponent getWidgetComponent() {
        return widgetComponent;
    }

}
