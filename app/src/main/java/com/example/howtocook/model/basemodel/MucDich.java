package com.example.howtocook.model.basemodel;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MucDich implements Serializable {
    private String mucDichId;
    private boolean anSang;
    private boolean anTrua;
    private boolean anToi;
    private boolean anKieng;
    private boolean anVat;


    public MucDich() {
    }

    public String getMucDichId() {
        return mucDichId;
    }

    public void setMucDichId(String mucDichId) {
        this.mucDichId = mucDichId;
    }

    public boolean isAnSang() {
        return anSang;
    }

    public void setAnSang(boolean anSang) {
        this.anSang = anSang;
    }

    public boolean isAnTrua() {
        return anTrua;
    }

    public void setAnTrua(boolean anTrua) {
        this.anTrua = anTrua;
    }

    public boolean isAnToi() {
        return anToi;
    }

    public void setAnToi(boolean anToi) {
        this.anToi = anToi;
    }

    public boolean isAnKieng() {
        return anKieng;
    }

    public void setAnKieng(boolean anKieng) {
        this.anKieng = anKieng;
    }

    public boolean isAnVat() {
        return anVat;
    }

    public void setAnVat(boolean anVat) {
        this.anVat = anVat;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("mucDichId",mucDichId);
        result.put("anSang",anSang);
        result.put("anTrua",anTrua);
        result.put("anToi",anToi);
        result.put("anKieng",anKieng);
        result.put("anVat",anVat);

        return result;
    }
}
