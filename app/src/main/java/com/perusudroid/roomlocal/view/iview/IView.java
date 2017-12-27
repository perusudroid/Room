package com.perusudroid.roomlocal.view.iview;

import com.google.gson.Gson;
import com.perusudroid.roomlocal.presenter.ipresenter.IPresenter;
import com.perusudroid.roomlocal.utils.CodeSnippets;

/**
 * Created by perusu on 20/11/17.
 */

public interface IView {

    void showToast(String msg);

    void bindPresenter(IPresenter iPresenter);

    CodeSnippets getCodeSnippet();

    void showNetworkMessage();

    Gson getGson();

}
