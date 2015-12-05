package com.janseon.cardmenuview.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

/**
 * @author 健兴
 * @version 1.0
 * @Description
 * @date 2014-8-17
 * @Copyright: Copyright (c) 2014 Shenzhen Inser Technology Co., Ltd. Inc. All
 * rights reserved.
 */
public class MainActivity extends BaseActivity implements OnItemClickListener  {
    private ImageView img_bg;
    private View layout_user;
    private MenusLayout mMenusLayout;
    // private ActivityMainView mCreativeView;
    private CreativeView mCreativeView;
    // private CreativeTouchGallery mCreativeView;
    private OpusView mOpusView;
    private DramaView mDramaView;

    private BaseCardLayout[] mCardLayouts;

    private RoundedImageView img_portrait;
    private TextView txt_name;
    private TextView txt_info;
    private ImageView img_go;

    private Menu msgMenu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        img_bg = new ImageView(this);
        img_bg.setScaleType(ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        setContentView(img_bg, bgParams);
        FrameLayout.LayoutParams mainParams = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addContentView(View.inflate(this, R.layout.activity_main, null), mainParams);
        findViews();
        setViewsContent();
        setViewsListener();
    }

    @Override
    protected void findViews() {
        layout_user = findViewById(R.id.layout_user);
        mMenusLayout = (MenusLayout) findViewById(R.id.menus);
        mCreativeView = (CreativeView) findViewById(R.id.view_creative);
        mOpusView = (OpusView) findViewById(R.id.view_opus);
        mDramaView= (DramaView) findViewById(R.id.view_drama);

        mCardLayouts = new BaseCardLayout[3];
        mCardLayouts[0] = mCreativeView;
        mCardLayouts[1] = mOpusView;
        mCardLayouts[2] = mDramaView;

        img_portrait = (RoundedImageView) findViewById(R.id.img_portrait);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_info = (TextView) findViewById(R.id.txt_info);
        img_go = (ImageView) findViewById(R.id.img_go);
    }

    @Override
    protected void setViewsContent() {
        mCreativeView.setHideRestOthersEnable(true);
        mOpusView.setHideRestOthersEnable(true);
        mDramaView.setHideRestOthersEnable(true);

        mCreativeView.setFirstResetEnable(true);
        mOpusView.setFirstResetEnable(true);
        mDramaView.setFirstResetEnable(true);

//		Drawable drawable = getResources().getDrawable(R.drawable.img_head);
//		if (drawable instanceof BitmapDrawable) {
//			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//			Bitmap bgBitmap = BlurUtil.fastblur(_this(), bitmap);
//			img_bg.setImageBitmap(bgBitmap);
//		}

        ArrayList<Menu> list = new ArrayList<Menu>();
        list.add(new Menu(R.drawable.icon_main_drama, "剧本投标"));
        list.add(new Menu(R.drawable.icon_main_crowd, "影片众筹"));
        list.add(new Menu(R.drawable.icon_main_sponsorship, "赞助"));
        list.add(new Menu(R.drawable.icon_main_com_video, "视频定制"));
        msgMenu = new Menu(R.drawable.icon_main_message, "消息");
        list.add(msgMenu);
        list.add(new Menu(R.drawable.icon_main_more, "更多"));
        MenusAdapter menusAdapter = new MenusAdapter(this, list);
        mMenusLayout.setAdapter(menusAdapter);

        // int start = 500 + MenusLayout.duration + MenusLayout.delay_offset *
        // menusAdapter.getCount();
        // int start = 300;
        // mCreativeView.resetScroll(start + 100);
        // mOpusView.resetScroll(start);

        AnimUtil.postAnimation(layout_user, 200, 500);
        AnimUtil.postAnimationBottom(mCreativeView, 200, 1100);
        AnimUtil.postAnimationBottom(mOpusView, 150, 1000);
        AnimUtil.postAnimationBottom(mDramaView, 100, 900);

//        AsyncBiz.init(new AsyncCallBack(InitData.class) {
//            @Override
//            public void onSuccessResponse(Response t) {
//                InitDataConfig.setInitData((InitData) t.obj);
//                mCreativeView.toSetCreativeTypes();
//                mOpusView.toSetOpusTypes();
//                if (!UserConfig.isLogined()) {
//                    return;
//                }
//                setViewsUserInfo();
//                final String token = UserConfig.getUserInfo().token;
//                AsyncBiz.login(new AsyncCallBack(UserInfo.class) {
//                    @Override
//                    public void onSuccessResponse(Response t) {
//                        UserInfo userInfo = (UserInfo) t.obj;
//                        userInfo.setToken(token);
//                        setViewsUserInfo();
//                        AsyncBiz.messages(new AsyncCallBack(MsgStatus.class, true) {
//                            @SuppressWarnings("unchecked")
//                            @Override
//                            public void onSuccessResponse(Response t) {
//                                msgStatusList = (ArrayList<MsgStatus>) t.obj;
//                                // MsgStatusConfig.save(msgStatusList, t.data);
//                                setMsgCount();
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }

    @Override
    protected void setViewsListener() {
//        layout_user.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (UserConfig.isLogined()) {
//                    IntentUtil.gotoActivity(_this(), UserCenterActivity.class);
//                    overridePendingTransition(R.anim.top_in, R.anim.none);
//                } else {
//                    IntentUtil.gotoActivityForResult(_this(), LoginActivity.class, new ActivityResult() {
//                        @Override
//                        public void onActivityResult(Intent data) {
//                            setViewsUserInfo();
//                        }
//                    });
//                }
//            }
//        });
        mMenusLayout.setOnItemClickListener(this);
//        addReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (msgStatusList != null) {
//                    int type = intent.getIntExtra(IntentUtil.key(0), 0);
//                    int id = intent.getIntExtra(IntentUtil.key(1), 0);
//                    for (MsgStatus msgStatus : msgStatusList) {
//                        if (msgStatus.type == type) {
//                            if (id == 0) {
//                                msgStatus.unread = 0;
//                            } else {
//                                msgStatus.unread--;
//                            }
//                            break;
//                        }
//                    }
//                    setMsgCount();
//                }
//            }
//        }, BroadcastFilters.ACTION_MESSAGE_READ);
    }

    private void setViewsUserInfo() {
//        UserInfo userInfo = UserConfig.getUserInfo();
//        BitmapBiz.setPortrait(img_portrait, userInfo.header_image);
//        txt_name.setText(userInfo.nickname);
        txt_info.setVisibility(View.GONE);
        img_go.setVisibility(View.VISIBLE);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        switch (position) {
//            case 0:
//                IntentUtil.gotoActivity(this, DramaTendersActivity.class);
//                break;
//            case 1:
//                IntentUtil.gotoActivity(this, CrowdsActivity.class);
//                break;
//            case 2:
//                IntentUtil.gotoActivity(this, SponsorshipsActivity.class);
//                break;
//            case 3:
//                IntentUtil.gotoActivity(this, MyComVideosActivity.class);
//                break;
//            case 4:
//                if (msgStatusList != null) {
//                    IntentUtil.gotoActivity(this, MessagesActivity.class, msgStatusList);
//                }
//                break;
//            case 5:
//                IntentUtil.gotoActivityForResult(_this(), SettingActivity.class, new ActivityResult() {
//                    @Override
//                    public void onActivityResult(Intent data) {
//                        img_portrait.setImageResource(R.drawable.img_portrait_def);
//                        txt_name.setText("点击登录");
//                        txt_info.setVisibility(View.VISIBLE);
//                        img_go.setVisibility(View.GONE);
//                    }
//                });
//                break;
//        }
    }

    @Override
    public void onBackPressed() {
        boolean resetScrolled = false;
        for (BaseCardLayout cardLayout : mCardLayouts) {
            if (!cardLayout.checkReset()) {
                resetScrolled = true;
                break;
            }
        }
        if (resetScrolled) {
            for (BaseCardLayout cardLayout : mCardLayouts) {
                cardLayout.startResetScroll();
            }
            return;
        }
        super.onBackPressed();

//        DialogUtil.showAlert(_this(), "是否要退出应用程序？", "退出", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                DialogUtil.dismissDialog();
//                MainActivity.super.onBackPressed();
//                AppContext.exitDelayed();
//            }
//        });
    }

    private class MenusAdapter extends BaseObjectAdapter<Menu> {

        public MenusAdapter(Context context, ArrayList<Menu> list) {
            super(context, list);
        }

        @Override
        public View getView(int position, View convertView, final ViewGroup parent) {
            Menu menu = getItem(position);
            TextView txt = new TextView(mContext);
            txt.setTextColor(Color.WHITE);
            txt.setTextSize(16);
            txt.setGravity(Gravity.CENTER_HORIZONTAL);
            DrawableUtil.setTextDrawableTop(txt, DrawableUtil.dp2Px(mContext, 10), menu.resId);
            txt.setText(menu.text);
            final int size = LayoutParams.WRAP_CONTENT;
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size, Gravity.CENTER);
            parent.addView(txt, params);
            menu.setCountListener(new CountListener() {
                @Override
                public void onCount(int count) {
                    TextView txt_count = (TextView) parent.getTag();
                    if (count > 0) {
                        if (txt_count == null) {
                            txt_count = new TextView(mContext);
                            int pad = DrawableUtil.dp2Px(mContext, 8);
                            txt_count.setPadding(pad, 0, pad, 0);
                            //txt_count.setBackgroundResource(R.drawable.bg_round_red_msg);
                            txt_count.setTextColor(Color.WHITE);
                            txt_count.setTextSize(12);
                            int height = DrawableUtil.dp2Px(mContext, 17);
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, height, Gravity.RIGHT);
                            params.topMargin = pad;
                            params.rightMargin = pad;
                            parent.addView(txt_count, params);
                            parent.setTag(txt_count);
                        }
                        txt_count.setText("" + count);
                    } else {
                        if (txt_count != null) {
                            parent.removeView(txt_count);
                            parent.setTag(null);
                        }
                    }
                }
            });
            return parent;
        }
    }

    private class Menu {
        int resId;
        String text;

        Menu(int resId, String text) {
            this.resId = resId;
            this.text = text;
        }

        CountListener mCountListener;

        void setCountListener(CountListener l) {
            mCountListener = l;
        }

        void setCount(int count) {
            if (mCountListener != null) {
                mCountListener.onCount(count);
            }
        }
    }

    interface CountListener {
        void onCount(int count);
    }

}
