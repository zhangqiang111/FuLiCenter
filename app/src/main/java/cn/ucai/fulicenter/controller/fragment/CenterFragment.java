package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuliCenterApplication;
import cn.ucai.fulicenter.controller.activity.MainActivity;
import cn.ucai.fulicenter.model.bean.MessageBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ImageLoader;
import cn.ucai.fulicenter.model.utils.MFGT;

/**
 * A simple {@link Fragment} subclass.
 */
public class CenterFragment extends Fragment {


    @BindView(R.id.iv_user_avatar)
    ImageView ivUserAvatar;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_collect_count)
    TextView mTvCollectCount;
    IModelUser model;
    public CenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_center, container, false);
        ButterKnife.bind(this, view);
        initData();
        getCollectCount();
        return view;
    }

    private void initData() {
        User user = FuliCenterApplication.getUser();
        if (user != null) {
            loadUserInfo(user);
        } else {
            MFGT.gotoLogin((MainActivity) getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getCollectCount();
    }

    private void loadUserInfo(User user) {
        ImageLoader.downloadImg(getActivity(), ivUserAvatar, user.getAvatarPath());
        tvUserName.setText(user.getMuserName());
        loadCollectCount("0");
    }
    private void loadCollectCount(String count) {
        mTvCollectCount.setText(count);
    }

    private void getCollectCount() {
        model = new ModelUser();
        model.collectCount(getContext(), FuliCenterApplication.getUser().getMuserName(), new OnCompleteListener<MessageBean>() {
            @Override
            public void onSuccess(MessageBean result) {
                if (result!=null&&result.isSuccess()){
                    loadCollectCount(result.getMsg());
                }else {
                    loadCollectCount("0");
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @OnClick({R.id.tv_center_settings, R.id.center_user_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_settings:
                MFGT.gotoSetting((MainActivity) getActivity());
                break;
            case R.id.center_user_info:
                MFGT.gotoSetting((MainActivity) getActivity());
                break;
        }
    }

}
