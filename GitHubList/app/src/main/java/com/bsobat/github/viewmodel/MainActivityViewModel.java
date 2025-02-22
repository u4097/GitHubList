package com.bsobat.github.viewmodel;


import androidx.arch.core.util.Function;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import com.bsobat.github.dto.GitHubResponse;
import com.bsobat.github.dto.Resource;
import com.bsobat.github.repo.GitHubRepository;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {
    private GitHubRepository repository;

    final private MutableLiveData<Request> request = new MutableLiveData();
    final private LiveData<Resource<GitHubResponse>> result = Transformations.switchMap(request, new Function<Request, LiveData<Resource<GitHubResponse>>>() {
        @Override
        public LiveData<Resource<GitHubResponse>> apply(Request input) {
            LiveData<Resource<GitHubResponse>> resourceLiveData = repository.browseRepo(input.page, input.limit);
            return resourceLiveData;
        }
    });
    public static MainActivityViewModel create(FragmentActivity activity) {
        MainActivityViewModel viewModel = ViewModelProviders.of(activity).get(MainActivityViewModel.class);
        return viewModel;
    }

    public void load(int page, int limit) {
        request.setValue(new Request(page, limit));
    }

    @Inject
    public void setRepository(GitHubRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<GitHubResponse>> getResult() {
        return result;
    }

    public void clearCache() {
        repository.clearCache();
    }

    public static class Request {
        final private int page, limit;
        public Request(int page, int limit) {
            this.page = page;
            this.limit = limit;
        }
        public int getLimit() {
            return limit;
        }
    }

}
