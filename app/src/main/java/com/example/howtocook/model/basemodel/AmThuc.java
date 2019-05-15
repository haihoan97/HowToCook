package com.example.howtocook.model.basemodel;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class AmThuc {

    private int amThucId;
    private boolean VietNam;
    private boolean Y;
    private boolean Nhat;
    private boolean Trung;
    private boolean Thai;
    private boolean Han;
    private boolean Uc;
    private boolean Au;
    private boolean My;
    private boolean Phi;
    private boolean Phap;

    public AmThuc(int anThucId) {
        this.amThucId = anThucId;
    }

    public AmThuc(int anThucId, boolean vietNam, boolean y, boolean nhat, boolean trung, boolean thai, boolean han, boolean uc, boolean au, boolean my, boolean phi, boolean phap) {
        this.amThucId = anThucId;
        VietNam = vietNam;
        Y = y;
        Nhat = nhat;
        Trung = trung;
        Thai = thai;
        Han = han;
        Uc = uc;
        Au = au;
        My = my;
        Phi = phi;
        Phap = phap;
    }

    public int getAmThucId() {
        return amThucId;
    }

    public void setAmThucId(int amThucId) {
        this.amThucId = amThucId;
    }

    public boolean isVietNam() {
        return VietNam;
    }

    public void setVietNam(boolean vietNam) {
        VietNam = vietNam;
    }

    public boolean isY() {
        return Y;
    }

    public void setY(boolean y) {
        Y = y;
    }

    public boolean isNhat() {
        return Nhat;
    }

    public void setNhat(boolean nhat) {
        Nhat = nhat;
    }

    public boolean isTrung() {
        return Trung;
    }

    public void setTrung(boolean trung) {
        Trung = trung;
    }

    public boolean isThai() {
        return Thai;
    }

    public void setThai(boolean thai) {
        Thai = thai;
    }

    public boolean isHan() {
        return Han;
    }

    public void setHan(boolean han) {
        Han = han;
    }

    public boolean isUc() {
        return Uc;
    }

    public void setUc(boolean uc) {
        Uc = uc;
    }

    public boolean isAu() {
        return Au;
    }

    public void setAu(boolean au) {
        Au = au;
    }

    public boolean isMy() {
        return My;
    }

    public void setMy(boolean my) {
        My = my;
    }

    public boolean isPhi() {
        return Phi;
    }

    public void setPhi(boolean phi) {
        Phi = phi;
    }

    public boolean isPhap() {
        return Phap;
    }

    public void setPhap(boolean phap) {
        Phap = phap;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("amThucId",amThucId);
        result.put("VietNam",VietNam);
        result.put("Y",Y);
        result.put("Nhat",Nhat);
        result.put("Trung",Trung);
        result.put("Thai",Thai);
        result.put("Han",Han);
        result.put("Uc", Uc);
        result.put("Au",Au);
        result.put("My",My);
        result.put("Phap",Phap);
        result.put("Phi",Phi );

        return result;
    }
}
