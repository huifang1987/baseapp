package cn.wostore.baseapp.api.response;

import java.util.List;

/**
 * Created by Fanghui at 2018-10-24
 */
public class GetVideoListResponse {

    /**
     * data : {"count":2,"list":[{"id":"3","image":"9ab4df8b1a35489e9201298fc6237692","link":"resources/video/d1f53eb92eca4907a3c215d2aa62cfb1.mp4","title":"12313"},{"id":"2","image":"d6ab5864917a4331a9844115578b5452","link":"resources/video/cc41fddbaac646e7b52dda7c15fb321c.mp4","title":"123"}]}
     * message : 查询成功
     * success : true
     */

    private DataBean data;
    private String message;
    private String success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * count : 2
         * list : [{"id":"3","image":"9ab4df8b1a35489e9201298fc6237692","link":"resources/video/d1f53eb92eca4907a3c215d2aa62cfb1.mp4","title":"12313"},{"id":"2","image":"d6ab5864917a4331a9844115578b5452","link":"resources/video/cc41fddbaac646e7b52dda7c15fb321c.mp4","title":"123"}]
         */

        private String count;
        private List<VideoBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<VideoBean> getList() {
            return list;
        }

        public void setList(List<VideoBean> list) {
            this.list = list;
        }

        public static class VideoBean {
            /**
             * id : 3
             * image : 9ab4df8b1a35489e9201298fc6237692
             * link : resources/video/d1f53eb92eca4907a3c215d2aa62cfb1.mp4
             * title : 12313
             */

            private String id;
            private String image;
            private String link;
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
