package com.example.notesapp.Firebase;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.notesapp.Room.Database;
import com.example.notesapp.Room.Entity;
import com.example.notesapp.ViewModel.MyViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class MyWorker extends Worker {
    FirebaseDatabase database;
    FirebaseAuth auth;
    List<Entity> listLiveData;
    public MyWorker(Context context,WorkerParameters workerParams) {

        super(context, workerParams);

    }

    @NonNull

    @Override
    public Result doWork() {
        Log.i("123","workerisactive");

        return null;
    }
}
