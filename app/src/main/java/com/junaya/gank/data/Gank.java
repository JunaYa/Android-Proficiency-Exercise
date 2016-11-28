package com.junaya.gank.data;

import com.junaya.gank.utils.DateUtils;

import java.util.List;

/**
 * Created by aya on 2016/11/25.
 */

public class Gank {

    public String _id;
    public String createdAt;
    public String desc;
    public List<String> images;  /** have images **/
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    public String ganhuo_id;      /** search **/
    public String readability;  /** search **/

    public String getPublishedAt(){
        return DateUtils.formatPublish(publishedAt);
    }

    // for ViewHolder type
    public int getType() {
        if (images != null && images.size()>0) {
            return 1;
        } else if (readability!=null ){
            return 2;
        }else {
            return 0;
        }
    }
}
