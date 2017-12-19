package com.perusudroid.roomlocal.presenter.ipresenter;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Guru karthik on 06-12-2016.
 */

public interface IPresenter {

    void onCreatePresenter(Bundle bundle);

    void onStartPresenter();

    void onStopPresenter();

    void onPausePresenter();

    void onResumePresenter();

    void onDestroyPresenter();

    void onActivityResultPresenter(int requestCode, int resultCode, Intent data);
}
