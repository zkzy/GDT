package com.example.gdt;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;

import java.util.List;

public class GDTManager {
    private static final String TAG = "GDTManager";
    public static void loadBannerView(Activity context, ViewGroup container, String appID, String posId) {
        if (container != null) {
            BannerView bv = new BannerView(context, ADSize.BANNER, appID, posId);
            container.removeAllViews();
            container.addView(bv);
            bv.loadAD();
        }
    }

    public static void loadNativeExpressAD(Activity context, ViewGroup container, String appID, String posId) {
        if (container != null) {
            refreshAd(context, container, appID,
                    posId);
        }
    }

    private static void refreshAd(Activity context, final ViewGroup container, String appID, String posId) {
        /**
         *  如果选择支持视频的模版样式，请使用{@link PositionId#NATIVE_EXPRESS_SUPPORT_VIDEO_POS_ID}
         */
        NativeExpressAD nativeExpressAD = new NativeExpressAD(context, getMyADSize(), appID, posId, new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                Log.e(TAG, "onADLoaded: ");
                if (list != null && list.size() > 0) {
                    container.removeAllViews();
                    NativeExpressADView view=list.get(0);
                    container.addView(view);
                    view.render();
                }
            }
            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onRenderFail() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onRenderSuccess() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADExposure() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADClicked() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADClosed() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADLeftApplication() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADOpenOverlay() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {
                Log.d(TAG, "onADCloseOverlay() called with: nativeExpressADView = [" + nativeExpressADView + "]");
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.d(TAG, "onNoAD() called with: adError = [" + adError + "]");
                Log.d(TAG, "onNoAD() called with: adError = [" + adError.getErrorMsg() + "]");
            }
        }); // 这里的Context必须为Activity
        nativeExpressAD.setVideoOption(new VideoOption.Builder()
                .setAutoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS) // 设置什么网络环境下可以自动播放视频
                .setAutoPlayMuted(true) // 设置自动播放视频时，是否静音
                .build()); // setVideoOption是可选的，开发者可根据需要选择是否配置
        nativeExpressAD.setMaxVideoDuration(10);
        /**
         * 如果广告位支持视频广告，强烈建议在调用loadData请求广告前调用setVideoPlayPolicy，有助于提高视频广告的eCPM值 <br/>
         * 如果广告位仅支持图文广告，则无需调用
         */

        /**
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         *
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         *
         * 如自动播放策略为AutoPlayPolicy.WIFI，但此时用户网络为4G环境，在用户看来就是手工播放的
         */
        nativeExpressAD.setVideoPlayPolicy(getVideoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS, context));  // 本次拉回的视频广告，在用户看来是否为自动播放的
        nativeExpressAD.loadAD(1);

        Log.e(TAG, "refreshAd: " );
   ;
    }

    public static int getVideoPlayPolicy(int autoPlayPolicy, Context context) {
        if (autoPlayPolicy == VideoOption.AutoPlayPolicy.ALWAYS) {
            return VideoOption.VideoPlayPolicy.AUTO;
        } else if (autoPlayPolicy == VideoOption.AutoPlayPolicy.WIFI) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetworkInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            return wifiNetworkInfo != null && wifiNetworkInfo.isConnected() ? VideoOption.VideoPlayPolicy.AUTO
                    : VideoOption.VideoPlayPolicy.MANUAL;
        } else if (autoPlayPolicy == VideoOption.AutoPlayPolicy.NEVER) {
            return VideoOption.VideoPlayPolicy.MANUAL;
        }
        return VideoOption.VideoPlayPolicy.UNKNOWN;
    }

    private static com.qq.e.ads.nativ.ADSize getMyADSize() {
        return new com.qq.e.ads.nativ.ADSize(600, 400);
    }
}
