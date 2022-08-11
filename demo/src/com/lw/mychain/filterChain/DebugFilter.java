package com.lw.mychain.filterChain;

public class DebugFilter extends Filter{
    public DebugFilter(int level) {
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("DEBUG Filter : " + msg);
    }
}
