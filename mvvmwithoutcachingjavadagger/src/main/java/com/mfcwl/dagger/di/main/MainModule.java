package com.mfcwl.dagger.di.main;

import com.mfcwl.dagger.network.main.MainApi;
import com.mfcwl.dagger.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static PostsRecyclerAdapter provideAdapter() {
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi provideMainAPi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
