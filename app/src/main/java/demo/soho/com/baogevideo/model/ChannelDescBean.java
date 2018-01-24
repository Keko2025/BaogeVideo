package demo.soho.com.baogevideo.model;

/**
 * @author dell
 * @data 2018/1/24.
 * desc:频道详情
 */

public class ChannelDescBean {

    /**
     * status : 1
     * msg :
     * url :
     * data : {"id":"34","name":"杰西小姐姐","intro":"杰西·格拉夫（Jessie Graff），特技女星，因参加美国\u201c忍者勇士跑道\u201d比赛出名。","pic":"1618","cover":"1619","sort":"0","count":"1","is_commend":"0","add_time":"1516465535","update_time":"1516465601","status":"1","pic_url":"http://videos.baoge.tv/Uploads/Picture/2018-01-21/5a636d6f54c3f.jpg","cover_url":"http://videos.baoge.tv/Uploads/Picture/2018-01-21/5a636d74c65f5.jpg","collects":"1","shares":"12"}
     */

    private int status;
    private String msg;
    private String url;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 34
         * name : 杰西小姐姐
         * intro : 杰西·格拉夫（Jessie Graff），特技女星，因参加美国“忍者勇士跑道”比赛出名。
         * pic : 1618
         * cover : 1619
         * sort : 0
         * count : 1
         * is_commend : 0
         * add_time : 1516465535
         * update_time : 1516465601
         * status : 1
         * pic_url : http://videos.baoge.tv/Uploads/Picture/2018-01-21/5a636d6f54c3f.jpg
         * cover_url : http://videos.baoge.tv/Uploads/Picture/2018-01-21/5a636d74c65f5.jpg
         * collects : 1
         * shares : 12
         */

        private String id;
        private String name;
        private String intro;
        private String pic;
        private String cover;
        private String sort;
        private String count;
        private String is_commend;
        private String add_time;
        private String update_time;
        private String status;
        private String pic_url;
        private String cover_url;
        private String collects;
        private String shares;

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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getIs_commend() {
            return is_commend;
        }

        public void setIs_commend(String is_commend) {
            this.is_commend = is_commend;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getCollects() {
            return collects;
        }

        public void setCollects(String collects) {
            this.collects = collects;
        }

        public String getShares() {
            return shares;
        }

        public void setShares(String shares) {
            this.shares = shares;
        }
    }
}
