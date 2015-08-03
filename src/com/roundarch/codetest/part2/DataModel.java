package com.roundarch.codetest.part2;

import java.io.Serializable;

public class DataModel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -702176057888480130L;

    private String text1;
    private String text2;
    private double text3;

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public double getText3() {
        return text3;
    }

    public void setText3(double text3) {
        this.text3 = text3;
    }

}
