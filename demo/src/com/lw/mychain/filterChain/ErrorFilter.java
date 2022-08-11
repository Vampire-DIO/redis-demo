package com.lw.mychain.filterChain;

public class ErrorFilter extends Filter{
    public ErrorFilter(int level) {
        this.level = level;
    }

    @Override
    public void write(String msg) {
        System.out.println("ERROR filter: " + msg  );
    }
}
