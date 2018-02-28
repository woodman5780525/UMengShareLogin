package com.umeng.soexample.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.soexample.Defaultcontent;
import com.umeng.soexample.R;
import com.umeng.soexample.utils.ShareUtils;


import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnShareToQQ;
    private Button btnShareToWeiXin;
    private Button btnShareToWeiBo;
    private Button btnShareToQQZone;
    private Button btnShareToCircleFriend;
    private Button btnLoginFromQQ;
    private Button btnLoginFromWeiXin;
    private Button btnLoginFromWeiBo;
    private Button btnShowPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListen();

    }

    private void setListen() {
        //分享到QQ
        btnShareToQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(MainActivity.this,
                        Defaultcontent.url,
                        Defaultcontent.title ,
                        Defaultcontent.text,
                        Defaultcontent.imageurl,
                        R.mipmap.ic_launcher, SHARE_MEDIA.QQ );
            }
        });
        //分享到微信
        btnShareToWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(MainActivity.this,
                        Defaultcontent.url,
                        Defaultcontent.title ,
                        Defaultcontent.text,
                        Defaultcontent.imageurl,
                        R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN );
            }
        });
        //分享到微博
        btnShareToWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(MainActivity.this,
                        Defaultcontent.url,
                        Defaultcontent.title ,
                        Defaultcontent.text,
                        Defaultcontent.imageurl,
                        R.mipmap.ic_launcher, SHARE_MEDIA.SINA );
            }
        });
        //分享到QQ空间
        btnShareToQQZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(MainActivity.this,
                        Defaultcontent.url,
                        Defaultcontent.title ,
                        Defaultcontent.text,
                        Defaultcontent.imageurl,
                        R.mipmap.ic_launcher, SHARE_MEDIA.QZONE );
            }
        });
        //分享到朋友圈
        btnShareToCircleFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.shareWeb(MainActivity.this,
                        Defaultcontent.url,
                        Defaultcontent.title ,
                        Defaultcontent.text,
                        Defaultcontent.imageurl,
                        R.mipmap.ic_launcher, SHARE_MEDIA.WEIXIN_CIRCLE );
            }
        });
        //登入QQ
        btnLoginFromQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(SHARE_MEDIA.QQ);
            }
        });
        //登入微信
        btnLoginFromWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(SHARE_MEDIA.WEIXIN);
            }
        });
        //登入微博
        btnLoginFromWeiBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(SHARE_MEDIA.SINA);
            }
        });

        btnShowPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ShareAction(MainActivity.this)
                        //这里只单独分享文本，需要分享其他类型的数据
                        //直接调用with...方法即可
                        .withText("hello")
                         /*设置要分享的平台*/
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener)
                        .open();
            }
        });
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"成功了",
                    Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this,"失败"+
                    t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };



    //授权
    private void login(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");
                //下面这些可以查看官网文档，不同平台有些是获取不到的
                String uid = map.get("uid");
                String openid = map.get("openid");
                String unionid = map.get("unionid");
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                StringBuilder builder = new StringBuilder();
                builder.append(uid)
                        .append(openid)
                        .append(unionid)
                        .append(access_token)
                        .append(refresh_token)
                        .append(expires_in)
                        .append(name)
                        .append(gender)
                        .append(iconurl);
                Toast.makeText(MainActivity.this,
                        builder.toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }
            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    private void initView() {
        btnShareToQQ = (Button) findViewById(R.id.share_to_qq);
        btnShareToWeiXin = (Button) findViewById(R.id.share_to_weixin);
        btnShareToWeiBo = (Button) findViewById(R.id.share_to_weibo);
        btnShareToQQZone = (Button) findViewById(R.id.share_to_qq_zone);
        btnShareToCircleFriend = (Button) findViewById(R.id.share_to_circle_friend);
        btnLoginFromQQ = (Button) findViewById(R.id.login_from_qq);
        btnLoginFromWeiXin = (Button) findViewById(R.id.login_from_weixin);
        btnLoginFromWeiBo = (Button) findViewById(R.id.login_from_weibo);
        btnShowPanel = (Button) findViewById(R.id.btn_show_panel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }



}
