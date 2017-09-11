package com.chaotong.yujia.main.entity.HuiYuanBean;

/**
 * Created by Administrator on 2016/8/8 0008.
 */
public class HuiYuan_Info {
    String ncname;
    String sex;
    String age;
    String tel;
    String pic;
    String cardnum;
    String type;
    String wdyy;
    String wcks;
    String jf;
    String yhj;
    String hgdd;
    String dpj;
    String zxing_adr;
    String zxing_ios;

    public String getZxing_adr() {
        return zxing_adr;
    }

    public void setZxing_adr(String zxing_adr) {
        this.zxing_adr = zxing_adr;
    }

    public String getZxing_ios() {
        return zxing_ios;
    }

    public void setZxing_ios(String zxing_ios) {
        this.zxing_ios = zxing_ios;
    }

    public String getDpj() {
        return dpj;
    }

    public void setDpj(String dpj) {
        this.dpj = dpj;
    }

    public HuiYuan_Info(String ncname, String sex, String age, String tel,
                        String pic, String cardnum, String type, String wdyy,
                        String wcks, String jf, String yhj, String hgdd, String dpj) {
        this.ncname = ncname;
        this.sex = sex;
        this.age = age;
        this.tel = tel;
        this.pic = pic;
        this.cardnum = cardnum;
        this.type = type;
        this.wdyy = wdyy;
        this.wcks = wcks;
        this.jf = jf;
        this.yhj = yhj;
        this.hgdd = hgdd;
        this.dpj=dpj;
    }

    public String getWdyy() {
        return wdyy;
    }

    public void setWdyy(String wdyy) {
        this.wdyy = wdyy;
    }

    public String getHgdd() {
        return hgdd;
    }

    public void setHgdd(String hgdd) {
        this.hgdd = hgdd;
    }

    public String getNcname() {
        return ncname;
    }

    public void setNcname(String ncname) {
        this.ncname = ncname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getWcks() {
        return wcks;
    }

    public void setWcks(String wcks) {
        this.wcks = wcks;
    }

    public String getJf() {
        return jf;
    }

    public void setJf(String jf) {
        this.jf = jf;
    }

    public String getYhj() {
        return yhj;
    }

    public void setYhj(String yhj) {
        this.yhj = yhj;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


}
