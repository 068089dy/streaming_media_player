package com.example.dy.myapplication.ui.ui.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.dy.myapplication.NetworkRequestImpl;
import com.example.dy.myapplication.R;
import com.example.dy.myapplication.UrlListener;
import com.example.dy.myapplication.util.share;
import com.example.dy.myapplication.util.window_method;
import com.upyun.upplayer.widget.UpVideoView;

import java.util.Iterator;

import br.tiagohm.markdownview.MarkdownView;
import br.tiagohm.markdownview.css.InternalStyleSheet;
import br.tiagohm.markdownview.css.styles.Github;

import static com.example.dy.myapplication.common.data.FAVORITE_KEY;
import static com.example.dy.myapplication.common.data.favoritelist;
import static com.example.dy.myapplication.ui.ui.Activity.MainActivity.mainActivity;
import static com.example.dy.myapplication.util.share.*;

public class IdcardActivity extends AppCompatActivity {

    //视频播放器
    private UpVideoView upVideoView;

    //主播介绍
    private MarkdownView tx_des;

    //获取直播源接口
    NetworkRequestImpl mNetworkRequest;

    //
    private RelativeLayout video_layout;

    //房间号
    long roomid;

    //markdown风格
    private InternalStyleSheet mStyle = new Github();

    //视频控制控件
    private ImageView im_play;
    private ImageView im_back;
    private ImageView im_resume;
    private ImageView im_full;
    private TextView tv_window;
    private ImageView im_favorite;

    //是否喜爱
    Boolean is_favorite = false;

    //视频控制控件界面
    private RelativeLayout onplay_layout;

    //主播介绍cardview
    private CardView id_card_view;

    //小窗口类
    private window_method window1;

    //直播源地址
    String live_url;
    private int visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_idcard);
        Intent intent = getIntent();
        roomid = Long.parseLong(intent.getStringExtra("roomid"));
        System.out.println("douyuroomid:"+roomid);
        mNetworkRequest = new NetworkRequestImpl(this);

        //判断来自哪个直播平台
        if(Long.toString(roomid).startsWith("1001")) {
            String douyuroomid = Long.toString(roomid).substring(4);
            System.out.println("douyuroomid:"+douyuroomid);
            mNetworkRequest.getStreamUrl(Integer.parseInt(douyuroomid), urlListener);
        }else if(Long.toString(roomid).startsWith("1002")){
            String pandaroomid = Long.toString(roomid).substring(4);
            mNetworkRequest.getPandaStream(Integer.parseInt(pandaroomid), pandaurlListener);
        }
        //初始化控件
        initview();
        //控件事件
        viewevent();
    }

    private void initview(){
        upVideoView = (UpVideoView) findViewById(R.id.idcard_video);
        upVideoView.setImage(R.drawable.douyu_loading);
        visible = View.VISIBLE;
        upVideoView.selectTrack(10);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        video_layout = (RelativeLayout) findViewById(R.id.video_layout);
        video_layout.setMinimumHeight(video_layout.getWidth()*2/3);
        tx_des = (MarkdownView) findViewById(R.id.tx_des);
        tx_des.addStyleSheet(mStyle);
        tx_des.loadMarkdown("# 主播简介\n"+
                "陈一发儿，重庆人，建筑行业CAD狗一名（貌似前两天停薪留职了），“知名”斗鱼主播，大家都亲切地称之为：发姐！有丧尸的粉丝称其为发妈妈（斗鱼TV发姐儿子遍天下）。发姐年纪未知，坊间传说85年生日。由于长相（身材）的关系，人称电竞贾玲。发姐羞涩的时候自带台湾口音，经常自称台湾人。直播房间：陈一发儿-斗鱼\n" +
                "微博：陈一发儿的微博网易云音乐（内有翻唱电台）：网易云音乐\n" +
                "发姐貌似还有一个废弃多年的知乎：陈一发儿发姐的自拍时候大概是这样的：<img src=\"https://pic3.zhimg.com/581081697e0546fdae309bad199ba206_b.jpg\" data-rawwidth=\"440\" data-rawheight=\"585\" class=\"origin_image zh-lightbox-thumb\" width=\"440\" data-original=\"https://pic3.zhimg.com/581081697e0546fdae309bad199ba206_r.jpg\">直播的时候大概是这样的（大雾）：<img src=\"https://pic1.zhimg.com/72c2a4b6705e077bd170a1089e38dbd4_b.jpg\" data-rawwidth=\"1082\" data-rawheight=\"1400\" class=\"origin_image zh-lightbox-thumb\" width=\"1082\" data-original=\"https://pic1.zhimg.com/72c2a4b6705e077bd170a1089e38dbd4_r.jpg\">发姐2014年9月在斗鱼开始直播，初出茅庐，风格羞涩，走的是唱小曲的路线，拿着一个蓝色的飞机杯一样的话筒。这是发姐早年间的珍贵录像：这就是发姐说的羞涩；斗鱼好声音，清新主播发姐。<img src=\"https://pic4.zhimg.com/v2-1812f4a5da5a09f6918163748ea45b5b_b.jpg\" data-rawwidth=\"352\" data-rawheight=\"356\" class=\"content_image\" width=\"352\">（胸前是为了防止走光的。。。电工胶带。。。。）之后发姐渐渐就。。。放开了。。。这是渐渐放开时期的代表作，翻唱A站著名歌曲刘继芬：斗鱼吹B哪家强？发姐教你唱爆刘继芬。身败名裂let it go：发姐身败名裂与百合之夜。我就是这个时候入坑的...之后就出现了各种特色的歌曲\n" +
                "重庆话的红色高跟鞋：发姐劲歌金曲 AWESOME MIX VOL. 1\n" +
                "上古网络歌曲，大学生自习室：发姐英雄池简直深不可测，套路太深\n" +
                "早期发姐的小集锦：发姐18岁生日特辑 1024品质平心而论，发姐唱歌不错，重低音美如画，前一阶段的：imbatv麦霸发姐两首歌修音版，可以证明。虽然发姐高音捉急，但反而成了良好的节目效果。渐渐放开之后。。。然后就放的非常的开。。。发姐收起了她的蓝色飞机杯，直播划水各种讲段子，感觉有些脱口秀的意思。这也是发姐最发光发热的时候，其直播内容花样翻新，笑点也很多，在斗鱼也渐渐有了比较高的地位（毕竟斗鱼四平）。以下是发姐除了唱歌外，直播时候的一些活动录像集锦：最大的日常！讲！黄！段！子！：\n" +
                "陈一发讲道理（开黄腔）。。 发姐：哲学讨论什么是处男画CAD，展示隆胸的坏处：[黑妈狂魔] 发姐11.01 陈工实力CAD再现江湖（恭喜鱼丸20T）<img src=\"https://pic4.zhimg.com/889ee19ffa75fb12f4f1f26f1794c19b_b.jpg\" data-rawwidth=\"1792\" data-rawheight=\"710\" class=\"origin_image zh-lightbox-thumb\" width=\"1792\" data-original=\"https://pic4.zhimg.com/889ee19ffa75fb12f4f1f26f1794c19b_r.jpg\">画CAD讨论一条内裤可以穿12次：发姐 能穿六次的三角裤可惜卖的是平角裤  <img src=\"https://pic2.zhimg.com/22b648f88f12d5408d5a1d7e347b7b0d_b.jpg\" data-rawwidth=\"1780\" data-rawheight=\"752\" class=\"origin_image zh-lightbox-thumb\" width=\"1780\" data-original=\"https://pic2.zhimg.com/22b648f88f12d5408d5a1d7e347b7b0d_r.jpg\">花式黑妈：\n" +
                "发姐日常黑妈集锦 [黑妈狂魔] 发姐11.01 陈工实力CAD再现江湖（恭喜鱼丸20T）  2为了满足观众的事业线要求，发姐直播画过沟：发姐无限火力段子模式自带6把无尽去别的直播间查房划水混时间：\n" +
                "5.13  发发查房反被黑 心疼我发 发姐查房被反制发姐渐渐（为了混时间）定位成了一个游戏主播，先后流浪于英雄联盟区和主机游戏区。发姐PC游戏主攻LOL、Dota2、山口山。后来买了PS4，播了美国末日、GTA5、血源（发姐血源第一猎人最终boss两次过！），她还尝试过播JUST DANCE，但是效果堪比太极：陈一发跳中老年广场舞——陈氏太极，尽情站桩。。。后来，发姐在CJ跳了一段小苹果，有进步！发姐鸡哥《小苹果》（发姐高清主视角 by 不愿意透露姓名的1群水友）2015年CJ前后，斗鱼兴起了户外直播，这是发姐用鸡哥的设备开的KTV直播，发妈妈还深情献唱了经典歌曲《抱刘继芬》：真正的麦霸王者选手——陈一发儿由于和游戏圈有了联系，发姐还结识了王校长王思聪。发姐在陪校长打LOL的时候，校长一句“重庆太JB远了”，发妈妈豪门梦碎。话说斗鱼开了酬勤之后，王思聪一晚上刷了上万元的鱼翅给发姐。最后，发姐在被劝退了CAD行业之后，全身心的投入到了娱乐行业。这是发姐拍的羞耻广告——“猪猪是怎么死的”。请诸位鉴赏：发姐演技爆发背后的故事实际上，这个答案里面诸多的视频录像，大家也应该感受到了发姐儿子们的热情。发姐的粉丝创造力和战斗力也很强大，几个粉丝群以二群的基佬最为丧病。除了巨量的GIF表情包之外，“斗奶狂魔陈一发”这个视频堪称粉丝虐恋巅峰之作：斗奶狂魔陈一发最终，我们为什么喜欢看陈一发儿的直播？\n" +
                "回答：闪闪发光的人格魅力！-------------\n" +
                "发姐直播快一年了，内容和梗都越来越多，很难整理出一个详尽的历史和视频集锦。本答案只是讲了我个人的所见，希望有兴趣的可以入坑。如果有需要补充的文字或者视频，请诸位留言。---------更新-----------\n" +
                "以上是15年7月的答案，发姐15年末去了上海，进入了新的环境，开始了新的生活。发姐还持续不断地产出着直播视频，但是因为我太懒，并不能整理上来了。。。更新一个15年的直播集锦，另外这个UP主也算是最近非常知名的逆子了：\n" +
                "发姐，2015年部分精彩集锦第一集 发姐，2015年部分精彩集锦第二集 发姐，2015年部分精彩集锦第三集（全集）------------更新--------------\n" +
                "讲真，15年末到现在（17年3月）发姐的精彩录像，萝叔叔毕竟还是敬业，内容详实丰富，如需入坑补档请看：萝菽菽的个人空间（ACFUN）萝菽菽的个人空间（Bilibili）另一个UP：加辣的个人空间（Bilibili）铁打的发姐流水的录像师傅，以前的录像师傅更替了很多，最近萝叔叔的投稿评论区被疯狂带节奏，不知道他还能坚持多久，毕竟A站药丸。另外，发姐歌唱事业迷之上升，《童话镇》网易热歌榜登顶，膨胀发能吹一辈子。歌曲链接：网易云音乐。-----------2017年3月更新--------A站药丸，很多发姐早期的视频都打不开了，暂时也没有特别好的补救措施，互联网内容爆炸但是又有很多内容没来得及保存就消失了，且看且珍惜。我现在只能给罗叔叔续一\n");

        //视频播放控制
        im_back = (ImageView) findViewById(R.id.im_back);
        im_play = (ImageView) findViewById(R.id.im_play);
        im_resume = (ImageView) findViewById(R.id.im_resume);
        im_full = (ImageView) findViewById(R.id.im_full);
        tv_window = (TextView) findViewById(R.id.tv_window);
        im_favorite = (ImageView) findViewById(R.id.im_favorite);


        onplay_layout = (RelativeLayout) findViewById(R.id.onplay_layout);
        id_card_view = (CardView) findViewById(R.id.id_card_view);

        //判断是否关注
        Iterator<String> it = favoritelist.iterator();
        while(it.hasNext()){
            if(it.next().equals(Long.toString(roomid))){
                is_favorite = true;
                im_favorite.setImageResource(R.drawable.favorite2);
            }
        }

    }

    private void viewevent(){

        upVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onplay_layout.setVisibility(View.VISIBLE);
                return true;
            }
        });
        onplay_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onplay_layout.setVisibility(View.GONE);
                return true;
            }
        });

        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        im_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upVideoView.resume();
            }
        });

        im_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upVideoView.isPlaying()){
                    im_play.setImageResource(R.drawable.play);
                    upVideoView.pause();
                }else{
                    im_play.setImageResource(R.drawable.pause);
                    upVideoView.start();
                }
            }
        });

        tv_window.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.showDesk();
                finish();
            }
        });
        /*
        im_full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(upVideoView.isFullState()){
                    id_card_view.setVisibility(View.VISIBLE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }else {
                    id_card_view.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
            }
        });
        */
        im_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_favorite){

                }else{
                    im_favorite.setImageResource(R.drawable.favorite2);
                    favoritelist.add(Long.toString(roomid));
                    putString(mainActivity,FAVORITE_KEY, share.getString(mainActivity,FAVORITE_KEY)+Long.toString(roomid)+"|");
                    is_favorite = true;
                }
            }
        });


    }

    private UrlListener urlListener = new UrlListener() {
        @Override
        public void onSuccess(String url) {
            live_url = url;
            window1 = new window_method(IdcardActivity.this,live_url,Long.toString(roomid));
            window1.createWindowManager();
            window1.createDesktopLayout();
            System.out.println("直播live:"+url);
            upVideoView.setVideoPath(url);
            upVideoView.start();
        }

        @Override
        public void onError() {
            Toast.makeText(IdcardActivity.this,"播放出错",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    private UrlListener pandaurlListener = new UrlListener() {
        @Override
        public void onSuccess(String url) {
            live_url = url;
            window1 = new window_method(IdcardActivity.this,live_url,Long.toString(roomid));
            window1.createWindowManager();
            window1.createDesktopLayout();
            System.out.println("直播live:"+url);
            upVideoView.setVideoPath(url);
            upVideoView.start();
        }

        @Override
        public void onError() {
            Toast.makeText(IdcardActivity.this,"播放出错",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
