package com.yiwo.friendscometogether.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.friendscometogether.R;
import com.yiwo.friendscometogether.base.BaseFragment;
import com.yiwo.friendscometogether.imagepreview.StatusBarUtils;
import com.yiwo.friendscometogether.model.UserModel;
import com.yiwo.friendscometogether.network.NetConfig;
import com.yiwo.friendscometogether.pages.LoginActivity;
import com.yiwo.friendscometogether.pages.LookHistoryActivity;
import com.yiwo.friendscometogether.pages.MyCommentActivity;
import com.yiwo.friendscometogether.pages.MyFriendActivity;
import com.yiwo.friendscometogether.pages.MyInformationActivity;
import com.yiwo.friendscometogether.pages.MyOrderActivity;
import com.yiwo.friendscometogether.pages.MyPicturesActivity;
import com.yiwo.friendscometogether.sp.SpImp;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/3.
 */

public class MyFragment1 extends BaseFragment {

    View rootView;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;

    private SpImp spImp;
    private String uid = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my1, null);
        StatusBarUtils.setStatusBarTransparent(getActivity());
        ScreenAdapterTools.getInstance().loadView(rootView);

        ButterKnife.bind(this, rootView);
        spImp = new SpImp(getContext());

        return rootView;
    }

    @Override
    public void onNetChange(int netMobile) {
        // TODO Auto-generated method stub
        //在这个判断，根据需要做处理
        if (netMobile == 1) {
            Log.e("2222", "inspectNet:连接wifi");
            onStart();
        } else if (netMobile == 0) {
            Log.e("2222", "inspectNet:连接移动数据");
            onStart();
        } else if (netMobile == -1) {
            Log.e("2222", "inspectNet:当前没有网络");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = spImp.getUID();
        Log.e("222", uid);
        if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
//            tvNotLogin.setVisibility(View.GONE);
//            rlContent.setVisibility(View.VISIBLE);
            ViseHttp.POST(NetConfig.userInformation)
                    .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userInformation))
                    .addParam("uid", uid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                Log.e("222", data);
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getInt("code") == 200) {
                                    Gson gson = new Gson();
                                    UserModel userModel = gson.fromJson(data, UserModel.class);
                                    if (TextUtils.isEmpty(userModel.getObj().getHeadeimg())) {
                                        Picasso.with(getContext()).load(R.mipmap.my_head).into(ivAvatar);
                                    } else {
                                        Picasso.with(getContext()).load(userModel.getObj().getHeadeimg()).into(ivAvatar);
                                    }
                                    tvNickname.setText("昵称: " + userModel.getObj().getUsername());
//                                    if (userModel.getObj().getSex().equals("0")) {
//                                        Picasso.with(getContext()).load(R.mipmap.nan).into(ivSex);
//                                    } else {
//                                        Picasso.with(getContext()).load(R.mipmap.nv).into(ivSex);
//                                    }
                                    //等级部分
//                                    rlSignTeam.setVisibility(View.VISIBLE);
//                                    tvLevel.setText("LV" + userModel.getObj().getUsergrade());
//                                    if (userModel.getObj().getSign().equals("0")) {
//                                        Glide.with(getContext()).load(R.mipmap.sign_gray).into(ivIsSign);
//                                    } else {
//                                        Glide.with(getContext()).load(R.mipmap.sign_yellow).into(ivIsSign);
//                                    }
//                                    //是否为vip
//                                    if(userModel.getObj().getVip().equals("1")){
//                                        rlCreateActivity.setVisibility(View.VISIBLE);
//                                        rlInitiating.setVisibility(View.VISIBLE);
//                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
//            tvNotLogin.setVisibility(View.VISIBLE);
//            rlContent.setVisibility(View.GONE);
            Picasso.with(getContext()).load("null").into(ivAvatar);
        }
    }

    @OnClick({R.id.ll_order_all, R.id.ll_to_pay, R.id.ll_to_trip, R.id.ll_to_comment, R.id.ll_return_money, R.id.rl_my_picture, R.id.rl_my_friend,
    R.id.rl_my_comment, R.id.rl_history, R.id.rl_person_set})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.ll_order_all:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 0);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_to_pay:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 1);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_to_trip:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 2);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_to_comment:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 3);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.ll_return_money:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyOrderActivity.class);
                    intent.putExtra("position", 4);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_my_picture:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyPicturesActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_my_friend:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyFriendActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_my_comment:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyCommentActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_history:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), LookHistoryActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_person_set:
                if (!TextUtils.isEmpty(uid) && !uid.equals("0")) {
                    intent.setClass(getContext(), MyInformationActivity.class);
                    startActivity(intent);
                } else {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}