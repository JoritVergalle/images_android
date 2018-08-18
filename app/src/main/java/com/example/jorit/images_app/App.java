package com.example.jorit.images_app;

import android.app.Application;
import android.content.Context;

import com.example.jorit.images_app.domain.MyObjectBox;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import io.objectbox.BoxStore;

public class App extends Application {

    private BoxStore boxStore;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //LeakCanary.enableDisplayLeakActivity(this);
        // do this once, for example in your Application class
        boxStore = MyObjectBox.builder().androidContext(App.this).build();
// do this in your activities/fragments to get hold of a Box
        //notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);

        //boxStore = MyObjectBox.builder().androidContext(this).build();
// do this in your activities/fragments to get hold of a Box
        //notesBox = ((App) getApplication()).getBoxStore().boxFor(Note.class);
        //Box<Tag> box = boxStore.boxFor(Playlist.class)
    }

    public BoxStore getBoxStore() {
        return boxStore;
    }
}
