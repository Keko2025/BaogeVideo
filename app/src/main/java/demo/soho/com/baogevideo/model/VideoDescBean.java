package demo.soho.com.baogevideo.model;

import java.util.List;

/**
 * @author dell
 * @data 2018/1/20.
 */

public class VideoDescBean {

    /**
     * data : {"add_time":"1516421471","caches":"2","channel_id":"24","channel_intro":"从篮球转投健美，一路走来，外界质疑与夸赞不断，用无比对称、比例协调的肌肉体型征服观众与裁判，7届奥冠证明了西斯的实力（2011-2017奥赛无差别级冠军）","channel_name":"菲尔西斯","channel_pic":"118","channel_pic_url":"http://videos.baoge.tv/Uploads/Picture/2017-12-18/5a37cb474744a.jpg","channel_update_time":"1514301484","code":"cneCPv","collects":"4","comments":"2","file":"90","file_url":"http://videos.baoge.tv/Uploads/Download/2017-12-19/5a392e4f5cfc8.mp4","id":"32","intro":"菲尔.西斯的三角肌训练，来自YouTube频道FlexPlusTV","is_cnword":"1","is_collect":"0","is_commend":"0","lastplay_time":"1516463488","length":"6:30","likes":"0","pic":"172","pic_url":"http://videos.baoge.tv/Uploads/Picture/2017-12-20/5a39bdca66b13.jpg","play":"711","shares":"8","tag_ids":"343,380,381,382,517","tags":[{"id":"517","name":"阿诺德推举"},{"id":"380","name":"反飞鸟"},{"id":"381","name":"肩部"},{"id":"382","name":"三角肌"},{"id":"343","name":"西斯"}],"thumb_pic":"173","thumb_pic_url":"http://videos.baoge.tv/Uploads/Picture/2017-12-20/5a39bdca66b13_thumb.jpg","title":"菲尔西斯的三角肌训练","update_time":"1516421471"}
     * msg :
     * status : 1
     * url :
     */

    private DataBean data;
    private String msg;
    private int status;
    private String url;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class DataBean {
        /**
         * add_time : 1516421471
         * caches : 2
         * channel_id : 24
         * channel_intro : 从篮球转投健美，一路走来，外界质疑与夸赞不断，用无比对称、比例协调的肌肉体型征服观众与裁判，7届奥冠证明了西斯的实力（2011-2017奥赛无差别级冠军）
         * channel_name : 菲尔西斯
         * channel_pic : 118
         * channel_pic_url : http://videos.baoge.tv/Uploads/Picture/2017-12-18/5a37cb474744a.jpg
         * channel_update_time : 1514301484
         * code : cneCPv
         * collects : 4
         * comments : 2
         * file : 90
         * file_url : http://videos.baoge.tv/Uploads/Download/2017-12-19/5a392e4f5cfc8.mp4
         * id : 32
         * intro : 菲尔.西斯的三角肌训练，来自YouTube频道FlexPlusTV
         * is_cnword : 1
         * is_collect : 0
         * is_commend : 0
         * lastplay_time : 1516463488
         * length : 6:30
         * likes : 0
         * pic : 172
         * pic_url : http://videos.baoge.tv/Uploads/Picture/2017-12-20/5a39bdca66b13.jpg
         * play : 711
         * shares : 8
         * tag_ids : 343,380,381,382,517
         * tags : [{"id":"517","name":"阿诺德推举"},{"id":"380","name":"反飞鸟"},{"id":"381","name":"肩部"},{"id":"382","name":"三角肌"},{"id":"343","name":"西斯"}]
         * thumb_pic : 173
         * thumb_pic_url : http://videos.baoge.tv/Uploads/Picture/2017-12-20/5a39bdca66b13_thumb.jpg
         * title : 菲尔西斯的三角肌训练
         * update_time : 1516421471
         */

        private String add_time;
        private String caches;
        private String channel_id;
        private String channel_intro;
        private String channel_name;
        private String channel_pic;
        private String channel_pic_url;
        private String channel_update_time;
        private String code;
        private String collects;
        private String comments;
        private String file;
        private String file_url;
        private String id;
        private String intro;
        private String is_cnword;
        private String is_collect;
        private String is_commend;
        private String lastplay_time;
        private String length;
        private String likes;
        private String pic;
        private String pic_url;
        private String play;
        private String shares;
        private String tag_ids;
        private String thumb_pic;
        private String thumb_pic_url;
        private String title;
        private String update_time;
        private List<TagsBean> tags;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getCaches() {
            return caches;
        }

        public void setCaches(String caches) {
            this.caches = caches;
        }

        public String getChannel_id() {
            return channel_id;
        }

        public void setChannel_id(String channel_id) {
            this.channel_id = channel_id;
        }

        public String getChannel_intro() {
            return channel_intro;
        }

        public void setChannel_intro(String channel_intro) {
            this.channel_intro = channel_intro;
        }

        public String getChannel_name() {
            return channel_name;
        }

        public void setChannel_name(String channel_name) {
            this.channel_name = channel_name;
        }

        public String getChannel_pic() {
            return channel_pic;
        }

        public void setChannel_pic(String channel_pic) {
            this.channel_pic = channel_pic;
        }

        public String getChannel_pic_url() {
            return channel_pic_url;
        }

        public void setChannel_pic_url(String channel_pic_url) {
            this.channel_pic_url = channel_pic_url;
        }

        public String getChannel_update_time() {
            return channel_update_time;
        }

        public void setChannel_update_time(String channel_update_time) {
            this.channel_update_time = channel_update_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCollects() {
            return collects;
        }

        public void setCollects(String collects) {
            this.collects = collects;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getIs_cnword() {
            return is_cnword;
        }

        public void setIs_cnword(String is_cnword) {
            this.is_cnword = is_cnword;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }

        public String getIs_commend() {
            return is_commend;
        }

        public void setIs_commend(String is_commend) {
            this.is_commend = is_commend;
        }

        public String getLastplay_time() {
            return lastplay_time;
        }

        public void setLastplay_time(String lastplay_time) {
            this.lastplay_time = lastplay_time;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public String getShares() {
            return shares;
        }

        public void setShares(String shares) {
            this.shares = shares;
        }

        public String getTag_ids() {
            return tag_ids;
        }

        public void setTag_ids(String tag_ids) {
            this.tag_ids = tag_ids;
        }

        public String getThumb_pic() {
            return thumb_pic;
        }

        public void setThumb_pic(String thumb_pic) {
            this.thumb_pic = thumb_pic;
        }

        public String getThumb_pic_url() {
            return thumb_pic_url;
        }

        public void setThumb_pic_url(String thumb_pic_url) {
            this.thumb_pic_url = thumb_pic_url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * id : 517
             * name : 阿诺德推举
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
