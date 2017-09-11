package com.chaotong.yujia.main.entity.collect;

/**
 * Created by Administrator on 2016/8/18 0018.
 */
public class CollectChangguan {
    String stadiumsid;
    String stadiumsname;
    String add;
    String area;
    String pic;
    String distance;
    String tel;
    String mobile;
    String detail;

    public String getStadiumsid() {
        return stadiumsid;
    }

    public void setStadiumsid(String stadiumsid) {
        this.stadiumsid = stadiumsid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public CollectChangguan(String stadiumsid,String stadiumsname, String add, String area, String pic,
                            String distance, String tel, String mobile) {
        this.stadiumsid=stadiumsid;
        this.stadiumsname = stadiumsname;
        this.add = add;
        this.area = area;
        this.pic = pic;
        this.distance = distance;
        this.tel = tel;
        this.mobile = mobile;

    }

    public CollectChangguan() {
    }

    public String getStadiumsname() {
        return stadiumsname;
    }

    public void setStadiumsname(String stadiumsname) {
        this.stadiumsname = stadiumsname;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
