package com.example.mycv.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.observers.DisposableObserver;

@Dao
public interface CvDao {
    @Query("SELECT * FROM MYRESPONSE")
    Flowable<List<MyResponse>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MyResponse myResponse);

    @Update
    void update(MyResponse myResponse);

    @Delete
    void delete(MyResponse myResponse);

}
