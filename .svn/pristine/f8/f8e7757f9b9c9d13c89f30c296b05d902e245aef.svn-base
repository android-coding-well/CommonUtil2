package com.gosuncn.cu.module.dagger2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gosuncn.core.base.BaseFragment;
import com.gosuncn.cu.R;
import com.gosuncn.cu.app.MyApplication;

import javax.inject.Inject;

public class BlankFragment extends BaseFragment implements BlankContract.View{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    @Inject
    BlankPresenter blankPresenter;

    public BlankFragment() {
    }


    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //MyApplication.getInstance().getActivityComponent(getActivity()).inject(this);
        DaggerBlankComponent.builder().appComponent(MyApplication.getInstance().getAppComponent()).blankModule(new BlankModule(this)).build().inject(this);
       // blankPresenter.attachView(this);
        blankPresenter.test();
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }



    /**
     * 设置presenter，在使用依赖注入后此方法可以直接忽略
     *
     * @param presenter
     */
    @Override
    public void setPresenter(BlankContract.Presenter presenter) {

    }

    /**
     * 显示提示内容
     *
     * @param hint
     */
    @Override
    public void showHint(String hint) {
            showShortToast(hint);
    }
}
