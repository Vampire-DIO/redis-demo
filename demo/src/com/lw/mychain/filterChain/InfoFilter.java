package com.lw.mychain.filterChain;

public class InfoFilter extends Filter{
    public InfoFilter(int level){
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("INFO Filter: " + msg);
    }
}
