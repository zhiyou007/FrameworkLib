package com.wzm.tools;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.wzm.framework.Tools.Logger;
import com.wzm.framework.Tools.Tools;
import com.wzm.framework.uihelper.ImgHelper;

/**
 * function(功能) :   第三方分享管理
 * Created by liuyang on 15/12/17.
 * company : http://www.graphmovie.com
 */
public class ShareManager {
    private static ShareManager instance;
//    final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
//            {
//                    SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
//                    SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.DOUBAN,
//                    SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS
//            };
//    final int[] normalicon = new int[]{R.mipmap.share_wechat, R.mipmap.share_pengyouquan,
//            R.mipmap.share_weibo, R.mipmap.share_qq,R.mipmap.share_qqzone, R.mipmap.share_douban,R.mipmap.share_mail,
//            R.mipmap.share_text};
//    final String[] normalstring = new String[]{"微信", "朋友圈", "新浪", "qq","qq空间", "豆瓣网", "邮件","短信"};
    private ShareManager() {

    }

    public static ShareManager getInstance() {
        if (instance == null) {
            instance = new ShareManager();
        }
        return instance;
    }

//    /**
//     * 返回分享词
//     */
//    public String getShareContent(MovieInfo mi) {
//        String sharecontent = Tag.SHARE_C.replaceAll("#电影名#", mi.name);
//
//        if (!TextUtils.isEmpty(mi.orkey)) {
//            sharecontent = sharecontent.replaceAll("#sub_title#", mi.subtitle);
//
//        } else {
//            sharecontent = sharecontent.replaceAll("#sub_title#", "");
//        }
//
//        if (!TextUtils.isEmpty(mi.orkey)) {
//            sharecontent = sharecontent.replaceAll("#电影链接#", Tag.RKEY_URL
//                    + mi.orkey);
//        } else {
//            sharecontent = sharecontent.replaceAll("观看：#电影链接#", "");
//        }
//        return sharecontent;
//    }
//
//
//    /**
//     * 电影分享
//     *
//     * @param activity
//     * @param mi
//     * @param umShareListener
//     */
//    public void initShare(Activity activity, MovieInfo mi, UMShareListener umShareListener) {
//
//        String share_content = mi.subtitle;
//
//        String share_url = Tag.SHARE_URL;
//        if (!TextUtils.isEmpty(mi.orkey)) {
//            share_url = Tag.RKEY_URL + mi.orkey;
//        }
//
//
//        initShare(activity, mi.id, share_content, mi.bpic, share_url, mi.name, umShareListener);
//    }
//
//    public void initShareAlbum(Activity activity, MovieInfo mi, UMShareListener umShareListener) {
//        String share_content = mi.subtitle;
//
//        String share_url = Tag.SHARE_URL;
//        if (!TextUtils.isEmpty(mi.orkey)) {
//            share_url = Tag.TOPIC_URL + mi.orkey;
//        }
//
//        initShare(activity, mi.id, share_content, mi.bpic, share_url, mi.name, umShareListener);
//    }

    /**
     * 自定义分享
     *
     * @param activity
     * @param share_content
     * @param pic
     * @param umShareListener
     */
    public void initShare(Activity activity,String share_content, String pic,UMShareListener umShareListener) {
        if(!pic.startsWith("http"))
        {
            try{
                pic = DES2.decrypt(pic, MD5Util.key);
            }catch (Exception e)
            {
                Tools.ToastMsg(activity,"处理图片失败");
                Logger.info("解密失败");
            }
        }
        UMImage image = new UMImage(activity, pic);//网络图片
        new ShareAction(activity).withText(share_content)
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();

    }
//
//    public void initShare(Activity activity, String id, String share_content, int pic, String
//            shareUrl, String title, UMShareListener umShareListener) {
//        ShareAction mShareAction = new ShareAction(activity).setDisplayList(displaylist);
//        mShareAction.withTitle(title);
//        mShareAction.withText(share_content);
//        mShareAction.withTargetUrl(shareUrl);
//        mShareAction.withMedia(new UMImage(activity, pic));
//        mShareAction.setListenerList(umShareListener);
//        mShareAction.open();
//    }


//    //自定义分享面板
//    private GridView mNormalView;
//    private TextView mShareTitle;
//    private ArrayList mList;
//    private ShareIconInfo info;
//    private CommonAdapter<ShareIconInfo> mAdapter;
//
//    public void initDiyView(final Activity activity, final String content, final String pic_url, final String url,
//                            final String title, String sharetitle, final UMShareListener listener) {
//        Dialog mShareDialog=null ;
//        if(mShareDialog==null){
//            mShareDialog= new Dialog(activity, R.style.dialog_coin);
//            mShareDialog.setContentView(R.layout.diyshareview);
//        }
//        Window dialogwindows = mShareDialog.getWindow();
//        dialogwindows.setGravity(Gravity.BOTTOM);
//        dialogwindows.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager
//                .LayoutParams.WRAP_CONTENT);
//        mShareTitle= (TextView) mShareDialog.findViewById(R.id.tv_sharetitle);
//        if(StringUtils.isNotEmpty(sharetitle)){
//            mShareTitle.setText(Html.fromHtml(sharetitle));
//        }else{
//            mShareTitle.setText("分享");
//        }
//        mNormalView = (GridView) mShareDialog.findViewById(R.id.gv_normalshare);
//        initNormalData(activity);
//        cancelDialog(mShareDialog,activity,content,pic_url,url,title,listener);
//        if(!mShareDialog.isShowing()){
//            mShareDialog.show();
//        }
//    }
//    //点击后弹框消失
//    public void cancelDialog(final Dialog mdialog, final Activity activity, final String content, final String pic_url, final String url,
//                             final String title, final UMShareListener listener){
//                mNormalView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        UMShareAPI.get(activity).doShare(activity, new ShareAction(activity)
//                                .withTitle(title)
//                                .withText(content)
//                                .setPlatform(displaylist[position])
//                                .withTargetUrl(url)
//                                .withMedia(new UMImage(activity, pic_url))
//                                .setListenerList(listener), listener);
//                        if(mdialog.isShowing()){
//                            mdialog.cancel();
//                        }
//                    }
//                });
//
//    }
//    public void initDiyShare(Activity activity, MovieInfo mi, String type, String sharetitle, UMShareListener listener) {
//        String content = mi.subtitle;
//        String url = Tag.SHARE_URL;
//        if (!TextUtils.isEmpty(mi.orkey)) {
//            if(type.equals("movie")){
//                url = Tag.RKEY_URL + mi.orkey;
//            }else {
//                url = Tag.TOPIC_URL + mi.orkey;
//            }
//        }
//        initDiyView(activity,content, mi.bpic, url, mi.name,sharetitle,listener);
//    }
//
//    //初始化所有分享项
//    private void initNormalData(Activity activity) {
//        mList = new ArrayList();
//        for (int i = 0; i < normalicon.length; i++) {
//            info = new ShareIconInfo();
//            info.icon = normalicon[i];
//            info.name = normalstring[i];
//            mList.add(info);
//        }
//        mAdapter = new CommonAdapter<ShareIconInfo>(activity, mList, R.layout.share_item) {
//            @Override
//            public void convert(ViewHolder helper, ShareIconInfo item, int pos) {
//                helper.setImageResource(R.id.iv_type, item.icon);
//                helper.setText(R.id.tv_typename, item.name);
//            }
//        };
//        mNormalView.setAdapter(mAdapter);
//    }
}
