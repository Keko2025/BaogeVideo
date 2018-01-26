package demo.soho.com.baogevideo.model;

/**
 * @author dell
 * @data 2018/1/26.
 */

public class UserBean {

    /**
     * status : 1
     * msg : 登录成功
     * url :
     * data : {"user_id":"1998","openid":"","qq_openid":"","weibo_uid":"","username":"豹哥铁管馆长","mobile":"17602142624","device_token":"AjVu6NusGxijUqcuYTFfnoYZQpctF8LPfPXz670nELSz","sex":"1","birthday":"1991-01-01","intro":"","height":"0","weight":"0","bfr":"0","pic":"0","score":"0","level_id":"1","level_time":"1970-01-01 08:00:00","grade":"2","is_sure":"0","dumb_time":"1970-01-01 08:00:00","is_push_comments":"1","is_push_likes":"1","read_message_ids":"","del_message_ids":"","login":"1","reg_ip":"1960977902","reg_time":"2018-01-25 16:37:46","last_login_ip":"116.226.37.238","last_login_time":"2018-01-25 16:37:46","lastclick_ats_time":"1516870618","lastclick_likes_time":"1516870618","status":"1","token":"402a32cb28e761b5994d9b3d80916681","pic_url":"","level_name":"LV.0","medal":"毫无训练痕迹","level_pic_url":"","next_level_score":"100","age":27}
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
         * user_id : 1998
         * openid :
         * qq_openid :
         * weibo_uid :
         * username : 豹哥铁管馆长
         * mobile : 17602142624
         * device_token : AjVu6NusGxijUqcuYTFfnoYZQpctF8LPfPXz670nELSz
         * sex : 1
         * birthday : 1991-01-01
         * intro :
         * height : 0
         * weight : 0
         * bfr : 0
         * pic : 0
         * score : 0
         * level_id : 1
         * level_time : 1970-01-01 08:00:00
         * grade : 2
         * is_sure : 0
         * dumb_time : 1970-01-01 08:00:00
         * is_push_comments : 1
         * is_push_likes : 1
         * read_message_ids :
         * del_message_ids :
         * login : 1
         * reg_ip : 1960977902
         * reg_time : 2018-01-25 16:37:46
         * last_login_ip : 116.226.37.238
         * last_login_time : 2018-01-25 16:37:46
         * lastclick_ats_time : 1516870618
         * lastclick_likes_time : 1516870618
         * status : 1
         * token : 402a32cb28e761b5994d9b3d80916681
         * pic_url :
         * level_name : LV.0
         * medal : 毫无训练痕迹
         * level_pic_url :
         * next_level_score : 100
         * age : 27
         */

        private String user_id;
        private String openid;
        private String qq_openid;
        private String weibo_uid;
        private String username;
        private String mobile;
        private String device_token;
        private String sex;
        private String birthday;
        private String intro;
        private String height;
        private String weight;
        private String bfr;
        private String pic;
        private String score;
        private String level_id;
        private String level_time;
        private String grade;
        private String is_sure;
        private String dumb_time;
        private String is_push_comments;
        private String is_push_likes;
        private String read_message_ids;
        private String del_message_ids;
        private String login;
        private String reg_ip;
        private String reg_time;
        private String last_login_ip;
        private String last_login_time;
        private String lastclick_ats_time;
        private String lastclick_likes_time;
        private String status;
        private String token;
        private String pic_url;
        private String level_name;
        private String medal;
        private String level_pic_url;
        private String next_level_score;
        private int age;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getQq_openid() {
            return qq_openid;
        }

        public void setQq_openid(String qq_openid) {
            this.qq_openid = qq_openid;
        }

        public String getWeibo_uid() {
            return weibo_uid;
        }

        public void setWeibo_uid(String weibo_uid) {
            this.weibo_uid = weibo_uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBfr() {
            return bfr;
        }

        public void setBfr(String bfr) {
            this.bfr = bfr;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getLevel_id() {
            return level_id;
        }

        public void setLevel_id(String level_id) {
            this.level_id = level_id;
        }

        public String getLevel_time() {
            return level_time;
        }

        public void setLevel_time(String level_time) {
            this.level_time = level_time;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getIs_sure() {
            return is_sure;
        }

        public void setIs_sure(String is_sure) {
            this.is_sure = is_sure;
        }

        public String getDumb_time() {
            return dumb_time;
        }

        public void setDumb_time(String dumb_time) {
            this.dumb_time = dumb_time;
        }

        public String getIs_push_comments() {
            return is_push_comments;
        }

        public void setIs_push_comments(String is_push_comments) {
            this.is_push_comments = is_push_comments;
        }

        public String getIs_push_likes() {
            return is_push_likes;
        }

        public void setIs_push_likes(String is_push_likes) {
            this.is_push_likes = is_push_likes;
        }

        public String getRead_message_ids() {
            return read_message_ids;
        }

        public void setRead_message_ids(String read_message_ids) {
            this.read_message_ids = read_message_ids;
        }

        public String getDel_message_ids() {
            return del_message_ids;
        }

        public void setDel_message_ids(String del_message_ids) {
            this.del_message_ids = del_message_ids;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getReg_ip() {
            return reg_ip;
        }

        public void setReg_ip(String reg_ip) {
            this.reg_ip = reg_ip;
        }

        public String getReg_time() {
            return reg_time;
        }

        public void setReg_time(String reg_time) {
            this.reg_time = reg_time;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public String getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(String last_login_time) {
            this.last_login_time = last_login_time;
        }

        public String getLastclick_ats_time() {
            return lastclick_ats_time;
        }

        public void setLastclick_ats_time(String lastclick_ats_time) {
            this.lastclick_ats_time = lastclick_ats_time;
        }

        public String getLastclick_likes_time() {
            return lastclick_likes_time;
        }

        public void setLastclick_likes_time(String lastclick_likes_time) {
            this.lastclick_likes_time = lastclick_likes_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getMedal() {
            return medal;
        }

        public void setMedal(String medal) {
            this.medal = medal;
        }

        public String getLevel_pic_url() {
            return level_pic_url;
        }

        public void setLevel_pic_url(String level_pic_url) {
            this.level_pic_url = level_pic_url;
        }

        public String getNext_level_score() {
            return next_level_score;
        }

        public void setNext_level_score(String next_level_score) {
            this.next_level_score = next_level_score;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
