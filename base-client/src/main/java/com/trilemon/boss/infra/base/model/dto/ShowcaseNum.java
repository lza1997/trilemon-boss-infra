package com.trilemon.boss.infra.base.model.dto;

import java.io.Serializable;

/**
 * @author kevin
 */
public class ShowcaseNum implements Serializable {
    long allShowcases;
    long usedShowcases;
    long remainShowcases;

    public ShowcaseNum(long allShowcases, long usedShowcases, long remainShowcases) {
        this.allShowcases = allShowcases;
        this.usedShowcases = usedShowcases;
        this.remainShowcases = remainShowcases;
    }

    public long getAllShowcases() {
        return allShowcases;
    }

    public void setAllShowcases(long allShowcases) {
        this.allShowcases = allShowcases;
    }

    public long getUsedShowcases() {
        return usedShowcases;
    }

    public void setUsedShowcases(long usedShowcases) {
        this.usedShowcases = usedShowcases;
    }

    public long getRemainShowcases() {
        return remainShowcases;
    }

    public void setRemainShowcases(long remainShowcases) {
        this.remainShowcases = remainShowcases;
    }

    public void incrShowcases() {
        usedShowcases++;
        remainShowcases--;
    }

    public void decrShowcases() {
        usedShowcases--;
        remainShowcases++;
    }


    public boolean isShowcaseFull() {
        return usedShowcases >= allShowcases;
    }

    public String toString(){
        return "showcase remain ["+allShowcases+"/"+usedShowcases+"/"+remainShowcases+"](all/used/remain).";
    }
}
