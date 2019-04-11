package com.yangsz.news.Bean;

import java.util.List;

public class NewsBean {
    //新闻头条
    private List<Bean> T1348647909107;
    public List<Bean> getTop(){
        return T1348647909107;
    }
    public void setT1348647909107(List<Bean> T1348647909107){
        this.T1348647909107=T1348647909107;
    }

    //体育新闻
    private List<Bean> T1348649145984;
    public List<Bean> getNba(){
        return T1348649145984;
    }
    public void setT1348649145984(List<Bean> T1348649145984){
        this.T1348649145984=T1348649145984;
    }
    //娱乐新闻
    private List<Bean> T1350383429665;
    public List<Bean> getJoke(){
        return T1350383429665;
    }
    public void setT1350383429665(List<Bean> T1350383429665){
        this.T1350383429665=T1350383429665;
    }


    //新闻类的set 和get方法集合
    public static class Bean{

        private String template;
        private String skipID;
        private String lmodify;
        private String postid;
        private String source;
        private String title;
        private String mtime;
        private int hasImg;
        private String topic_background;
        private String digest;
        private String photosetID;
        private String boardid;
        private String alias;
        private int hasAD;
        private String imgsrc;
        public String ptime;
        private String daynum;
        private int hasHead;
        private int imgType;
        private int order;
        private int votecount;
        private boolean hasCover;
        private String docid;
        public String tname;
        private int priority;
        private String ename;
        private int replyCount;
        private int imgsum;
        private boolean hasIcon;
        private String skipType;
        private String cid;
        private String url_3w;
        private String url;
        private String ltitle;
        private String subtitle;
        private String specialtip;
        private String specialID;
        private String speciallogo;
        private String specialadlogo;
        private List<?> editor;
        private List<AdsBean> ads;
        private List<SpecialextraBean> specialextra;
        private List<ImgextraBean> imgextra;


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMtime() {
            return mtime;
        }


        public String getDigest() {
            return digest;
        }


        public String getImgsrc() {
            return imgsrc;
        }


        public String getUrl() {
            return url;
        }


        public static class AdsBean {
            /**
             * subtitle :
             * skipType : photoset
             * skipID : 00AP0001|2293640
             * tag : photoset
             * title : 6岁娃重病无钱治 母亲:谁救好谁抱走
             * imgsrc : bigimg
             * url : 00AP0001|2293640
             */

            private String subtitle;
            private String skipType;
            private String skipID;
            private String tag;
            private String title;
            private String imgsrc;
            private String url;

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getSkipType() {
                return skipType;
            }

            public void setSkipType(String skipType) {
                this.skipType = skipType;
            }

            public String getSkipID() {
                return skipID;
            }

            public void setSkipID(String skipID) {
                this.skipID = skipID;
            }

            public String getTag() {
                return tag;
            }

            public void setTag(String tag) {
                this.tag = tag;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getUrl() {
                return url;
            }

        }





        public static class SpecialextraBean {
            /**
             * votecount : 0
             * docid : DI68OD9G0001875O
             * url_3w : http://news.163.com/18/0519/15/DI68OD9G0001875O.html
             * source : 新华国际
             * postid : DI68OD9G0001875O
             * priority : 71
             * title : 哈里王子婚礼在即 大批王室粉丝赶到温莎镇"候场"
             * mtime : 2018-05-19 15:26:17
             * url : http://3g.163.com/news/18/0519/15/DI68OD9G0001875O.html
             * replyCount : 0
             * ltitle : 哈里王子婚礼在即 大批王室粉丝赶到温莎镇"候场"
             * subtitle :
             * digest : 【哈里王子婚礼在即温莎镇人真多】英国哈里王子的婚礼定于当地时间19日中午（北京时间19日傍晚）在温莎城堡内举行。民调结果显示，共有66%受访英国人对此不太感兴趣
             * boardid : news_guoji2_bbs
             * imgsrc : http://cms-bucket.nosdn.127.net/dd4e93b64c224268b7dfe6005bb1530620180519152526.png
             * ptime : 2018-05-19 15:21:06
             * daynum : 17670
             */

            private int votecount;
            private String docid;
            private String url_3w;
            private String source;
            private String postid;
            private int priority;
            private String title;
            private String mtime;
            private String url;
            private int replyCount;
            private String ltitle;
            private String subtitle;
            private String digest;
            private String boardid;
            private String imgsrc;
            private String ptime;
            private String daynum;

            public int getVotecount() {
                return votecount;
            }

            public void setVotecount(int votecount) {
                this.votecount = votecount;
            }

            public String getDocid() {
                return docid;
            }

            public void setDocid(String docid) {
                this.docid = docid;
            }

            public String getUrl_3w() {
                return url_3w;
            }

            public void setUrl_3w(String url_3w) {
                this.url_3w = url_3w;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getPostid() {
                return postid;
            }

            public void setPostid(String postid) {
                this.postid = postid;
            }

            public int getPriority() {
                return priority;
            }

            public void setPriority(int priority) {
                this.priority = priority;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMtime() {
                return mtime;
            }

            public void setMtime(String mtime) {
                this.mtime = mtime;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getReplyCount() {
                return replyCount;
            }

            public void setReplyCount(int replyCount) {
                this.replyCount = replyCount;
            }

            public String getLtitle() {
                return ltitle;
            }

            public void setLtitle(String ltitle) {
                this.ltitle = ltitle;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getBoardid() {
                return boardid;
            }

            public void setBoardid(String boardid) {
                this.boardid = boardid;
            }

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }

            public String getPtime() {
                return ptime;
            }

            public void setPtime(String ptime) {
                this.ptime = ptime;
            }

            public String getDaynum() {
                return daynum;
            }

            public void setDaynum(String daynum) {
                this.daynum = daynum;
            }
        }

        public static class ImgextraBean {
            /**
             * imgsrc : http://cms-bucket.nosdn.127.net/2600b9fc21e545b3a62442007cd2dbd720180519152548.png
             */

            private String imgsrc;

            public String getImgsrc() {
                return imgsrc;
            }

            public void setImgsrc(String imgsrc) {
                this.imgsrc = imgsrc;
            }
        }
    }

}
