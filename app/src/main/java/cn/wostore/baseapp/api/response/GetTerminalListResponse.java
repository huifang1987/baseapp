package cn.wostore.baseapp.api.response;


import java.util.List;

/**
 * Created by Fanghui at 2018-10-24
 */
public class GetTerminalListResponse {


    /**
     * data : {"count":3,"list":[{"address":"江苏省-南京市","channel":"4","companyName":"眼湖科技","id":"3","image":"61654b11465244f3a426782ea5e2c266","lat":"31.947204","lon":"118.702525","status":"2","terminalModel":"003号","terminalName":"3号车辆","terminalNum":"1003"},{"address":"江苏省-南京市","channel":"3","companyName":"眼湖科技","id":"2","image":"269b9314fdaf46cdb869e1fc639829b6","lat":"32.020588","lon":"118.815135","status":"1","terminalModel":"002号","terminalName":"2号车辆","terminalNum":"1002"},{"address":"江苏省-南京市","channel":"2","companyName":"眼湖科技","id":"1","image":"b731e135873242688e1f1928173f56fb","lat":"32.022043","lon":"118.921565","status":"1","terminalModel":"001号","terminalName":"1号车辆","terminalNum":"1001"}]}
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
         * count : 3
         * list : [{"address":"江苏省-南京市","channel":"4","companyName":"眼湖科技","id":"3","image":"61654b11465244f3a426782ea5e2c266","lat":"31.947204","lon":"118.702525","status":"2","terminalModel":"003号","terminalName":"3号车辆","terminalNum":"1003"},{"address":"江苏省-南京市","channel":"3","companyName":"眼湖科技","id":"2","image":"269b9314fdaf46cdb869e1fc639829b6","lat":"32.020588","lon":"118.815135","status":"1","terminalModel":"002号","terminalName":"2号车辆","terminalNum":"1002"},{"address":"江苏省-南京市","channel":"2","companyName":"眼湖科技","id":"1","image":"b731e135873242688e1f1928173f56fb","lat":"32.022043","lon":"118.921565","status":"1","terminalModel":"001号","terminalName":"1号车辆","terminalNum":"1001"}]
         */

        private String count;
        private List<TerminalBean> list;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public List<TerminalBean> getList() {
            return list;
        }

        public void setList(List<TerminalBean> list) {
            this.list = list;
        }

        public static class TerminalBean {
            /**
             * address : 江苏省-南京市
             * channel : 4
             * companyName : 眼湖科技
             * id : 3
             * image : 61654b11465244f3a426782ea5e2c266
             * lat : 31.947204
             * lon : 118.702525
             * status : 2
             * terminalModel : 003号
             * terminalName : 3号车辆
             * terminalNum : 1003
             */

            private String address;
            private String channel;
            private String companyName;
            private String id;
            private String image;
            private String lat;
            private String lon;
            private String status;
            private String terminalModel;
            private String terminalName;
            private String terminalNum;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }

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

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTerminalModel() {
                return terminalModel;
            }

            public void setTerminalModel(String terminalModel) {
                this.terminalModel = terminalModel;
            }

            public String getTerminalName() {
                return terminalName;
            }

            public void setTerminalName(String terminalName) {
                this.terminalName = terminalName;
            }

            public String getTerminalNum() {
                return terminalNum;
            }

            public void setTerminalNum(String terminalNum) {
                this.terminalNum = terminalNum;
            }
        }
    }
}
