package kr.ac.kumoh.s20180073.myapplication.ui.week;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WeekViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WeekViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}