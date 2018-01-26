package demo.soho.com.baogevideo.model;

import java.util.List;

/**
 * @author dell
 * @data 2018/1/26.
 */

public class CollectListBean {

    /**
     * status : 1
     * msg :
     * url :
     * data : [{"video_id":"47","pic":"224","length":"08:17","title":"增大增厚胸肌的训练","is_cnword":"1","play":"128","video_add_time":"1516938652","video_update_time":"1516938652","type_name":"Jason Wittrock","username":"豹哥铁管馆长","userpic":"0","id":"724","add_time":"1516956677","pic_url":"http://videos.baoge.tv/Uploads/Picture/2018-01-01/5a4a428320aef.jpg","userpic_url":"","is_collect":"0"}]
     */

    private int status;
    private String msg;
    private String url;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * video_id : 47
         * pic : 224
         * length : 08:17
         * title : 增大增厚胸肌的训练
         * is_cnword : 1
         * play : 128
         * video_add_time : 1516938652
         * video_update_time : 1516938652
         * type_name : Jason Wittrock
         * username : 豹哥铁管馆长
         * userpic : 0
         * id : 724
         * add_time : 1516956677
         * pic_url : http://videos.baoge.tv/Uploads/Picture/2018-01-01/5a4a428320aef.jpg
         * userpic_url :
         * is_collect : 0
         */

        private String video_id;
        private String pic;
        private String length;
        private String title;
        private String is_cnword;
        private String play;
        private String video_add_time;
        private String video_update_time;
        private String type_name;
        private String username;
        private String userpic;
        private String id;
        private String add_time;
        private String pic_url;
        private String userpic_url;
        private String is_collect;

        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIs_cnword() {
            return is_cnword;
        }

        public void setIs_cnword(String is_cnword) {
            this.is_cnword = is_cnword;
        }

        public String getPlay() {
            return play;
        }

        public void setPlay(String play) {
            this.play = play;
        }

        public String getVideo_add_time() {
            return video_add_time;
        }

        public void setVideo_add_time(String video_add_time) {
            this.video_add_time = video_add_time;
        }

        public String getVideo_update_time() {
            return video_update_time;
        }

        public void setVideo_update_time(String video_update_time) {
            this.video_update_time = video_update_time;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getUserpic_url() {
            return userpic_url;
        }

        public void setUserpic_url(String userpic_url) {
            this.userpic_url = userpic_url;
        }

        public String getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(String is_collect) {
            this.is_collect = is_collect;
        }
    }
}
