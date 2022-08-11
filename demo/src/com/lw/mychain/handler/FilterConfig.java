package com.lw.mychain.handler;

import com.lw.mychain.filterChain.DebugFilter;
import com.lw.mychain.filterChain.ErrorFilter;
import com.lw.mychain.filterChain.Filter;
import com.lw.mychain.filterChain.InfoFilter;

public class FilterConfig {
    public static Filter getFilter(){
        InfoFilter infoFilter = new InfoFilter(1);
        DebugFilter debugFilter = new DebugFilter(2);
        ErrorFilter errorFilter = new ErrorFilter(3);
        infoFilter.setNextFilter(debugFilter);
        debugFilter.setNextFilter(errorFilter);

        return infoFilter;
    }

    public static void main(String[] args) {

        Filter filter = getFilter();
        filter.handle(Filter.INFO,"11");
        filter.handle(Filter.DEBUG,"22");
        filter.handle(Filter.ERROR,"33");

    }
}
