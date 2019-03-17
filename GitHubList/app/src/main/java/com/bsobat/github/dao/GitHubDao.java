package com.bsobat.github.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.bsobat.github.dto.GitHubDto;

import java.util.List;

@Dao
public interface GitHubDao {

    @Query("SELECT * FROM GitHubDto LIMIT :limit OFFSET :offset")
    LiveData<List<GitHubDto>> get(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(GitHubDto... gitHubDtos);

    @Delete
    void delete(GitHubDto gitHubDto);

    @Query("SELECT * FROM GitHubDto LIMIT :limit OFFSET :offset")
    List<GitHubDto> hasData(int offset, int limit);

    @Query("DELETE FROM GitHubDto")
    void deleteAll();
}
