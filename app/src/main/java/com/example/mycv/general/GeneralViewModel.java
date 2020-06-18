package com.example.mycv.general;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mycv.db.App;
import com.example.mycv.db.AppDatabase;
import com.example.mycv.db.CvDao;
import com.example.mycv.db.CvService;
import com.example.mycv.db.MyResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.moshi.Json;

import org.reactivestreams.Subscriber;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeneralViewModel extends AndroidViewModel{
    public MutableLiveData<MyResponse> result = new MutableLiveData<>();
    public MutableLiveData<Boolean> error = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    AppDatabase mAppDatabase;



    public GeneralViewModel(@NonNull Application application) {
        super(application);
        ((App)getApplication()).getComponent().inject(this);
    }


    private CvService getTestService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(CvService.class);
    }

    public void getInfo() {
        compositeDisposable.add(getTestService().getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                        new DisposableObserver<MyResponse>() {
                            @Override
                            public void onNext(MyResponse value) {
                                mAppDatabase.cvDao().insert(value);
                                getResponseFromBase();
                                error.postValue(false);
                                onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                if(e instanceof UnknownHostException){
                                   error.postValue(true);
                                }
                            }

                            @Override
                            public void onComplete() {

                            }
                        }
                ));


    }




    public void getResponseFromBase() {
        compositeDisposable.add(
                mAppDatabase.cvDao().getAll().
                        toObservable().
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<MyResponse>>() {
                            @Override
                            public void accept(List<MyResponse> responses) throws Exception {
                                long currentTime = new Date().getTime();
                               if(responses.size()==0){
                                   getInfo();
                               }else{
                                   if(currentTime - responses.get(0).getDate()>60000){
                                       getInfo();
                                   }else{
                                       result.postValue(responses.get(0));
                                   }

                               }

                            }
                        }));
    }


    public void clear(){
        compositeDisposable.clear();
    }
}
