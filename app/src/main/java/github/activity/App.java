package github.activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.work.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Provider;

import github.activity.feature.work.WorkerFactory;

public class App extends Application implements Configuration.Provider {

    private static final Logger L = LoggerFactory.getLogger(App.class);

    private ApplicationComponent component;

    @Inject
    protected Provider<WorkerFactory> workerFactoryProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        L.trace("App create");

        component = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationComponent.ApplicationModule(this))
            .build();
        component.inject(this);

    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
            .setWorkerFactory(workerFactoryProvider.get())
            .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

}
