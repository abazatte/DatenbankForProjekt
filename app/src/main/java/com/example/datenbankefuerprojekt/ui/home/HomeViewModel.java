package com.example.datenbankefuerprojekt.ui.home;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.datenbankefuerprojekt.db.main.database.Fragment;
import com.example.datenbankefuerprojekt.db.main.database.Uebung;
import com.example.datenbankefuerprojekt.db.main.database.UebungRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private static String TAG = "HomeViewModel:";

    private UebungRepository repository;
    private LiveData<List<Uebung>> allUebung;
    //private LiveData<Uebung> returnUebung;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.repository = new UebungRepository(application);
        this.allUebung = repository.getAlleUebungen();
    }

    public void insert(Uebung uebung) {
        repository.insertUebung(uebung);
    }

    public void update(Uebung uebung) {
        repository.updateUebung(uebung);
    }
    public void delete(Uebung uebung) {
        repository.deleteUebung(uebung);
    }
    public void deleteAllUebung() {
        repository.deleteAllUebungen();
    }

    public LiveData<List<Uebung>> getAllUebung() {return allUebung;}

    public void insertFragment(Fragment fragment){
        repository.insertFragment(fragment);
    }
    public void updateFragment(Fragment fragment){
        repository.updateFragment(fragment);
    }
    public void deleteFragment(Fragment fragment){
        repository.deleteFragment(fragment);
    }

    public LiveData<List<Fragment>> getAllFragmentsOfUebung(int uebungID){
        return repository.getAlleFragmenteOfUebung(uebungID);
    }

    /*
    public void initReturnUebung(int uebungId){
        Log.i(TAG, "initReturnUebung: uebungID" + uebungId);
        this.returnUebung = repository.getUebungById(uebungId);
    }

    public LiveData<Uebung> getReturnUebung(){
        return returnUebung;
    }*/


    public LiveData<Uebung> getUebungById(int uebungID){
        return repository.getUebungById(uebungID);
    }


}

/*
CODE GRAVEYARD:

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }



    public Uebung getUebungById(int uebungID) throws UebungNotPresentInDatabaseException {
        List<Uebung> uebungList = allUebung.getValue();
        Uebung result = null;
        for(Uebung u: uebungList){
            if(u.getId() == uebungID){
                result = u;
            }
        }
        if(result == null){
            throw new UebungNotPresentInDatabaseException("getUebungByID failed");
        }
        return result;
    }
 */