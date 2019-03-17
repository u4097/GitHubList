package com.bsobat.github.dao;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.bsobat.github.dto.GitHubDto;

@Database(entities = {GitHubDto.class}, version = 2)
public abstract class GitHubDatabase extends RoomDatabase {
    public abstract GitHubDao gitHubDao();
}
