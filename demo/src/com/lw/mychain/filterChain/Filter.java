package com.lw.mychain.filterChain;

public abstract class Filter {
    public static int INFO = 1;
    public static int DEBUG = 2;
    public static int ERROR = 3;

    protected int level;

    protected Filter nextFilter;

    public void setNextFilter(Filter nextFilter){
        this.nextFilter = nextFilter;
    }

    public void handle(int level, String msg){
        if (this.level >= level){
            write(msg);
        }else {
            nextFilter.handle(level,msg);
        }
    }

    public abstract void write(String msg);
}
