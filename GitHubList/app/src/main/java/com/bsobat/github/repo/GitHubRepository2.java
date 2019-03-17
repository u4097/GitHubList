package com.bsobat.github.repo;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.bsobat.github.api.GitHubApi;
import com.bsobat.github.dao.GitHubDao;
import com.bsobat.github.dto.ApiResponse;
import com.bsobat.github.dto.GitHubDto;
import com.bsobat.github.dto.Resource;
import com.bsobat.github.utils.NetworkBoundResource;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;


/**
 * This the Single source of truth!
 * This one will use the NetworkBoundResource
 */
public class GitHubRepository2 {
    final private GitHubApi api;
    final private GitHubDao dao;
    final private Executor executor;

    @Inject
    public GitHubRepository2(GitHubApi api, GitHubDao dao, Executor executor) {
        this.api = api;
        this.dao = dao;
        this.executor = executor;
    }

    public LiveData<Resource<List<GitHubDto>>> browseRepo(final int page, final int limit) {
        final int offset = (page - 1) * limit;
        LiveData<Resource<List<GitHubDto>>> liveData = new NetworkBoundResource<List<GitHubDto>, List<GitHubDto>>() {
            @Override
            protected void saveCallResult(@NonNull List<GitHubDto> items) {
                GitHubDto[] arr = new GitHubDto[items.size()];
                items.toArray(arr);
                dao.insertAll(arr);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<GitHubDto> data) {
                return true;//let's always refresh to be up to date. data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<GitHubDto>> loadFromDb() {
                return dao.get(offset, limit);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<GitHubDto>>> createCall() {
                LiveData<ApiResponse<List<GitHubDto>>> response = api.browseRepoLiveData(page, limit);
                return response;
            }
        }.getAsLiveData();

        return liveData;
    }

    public void clearCache() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }
}
