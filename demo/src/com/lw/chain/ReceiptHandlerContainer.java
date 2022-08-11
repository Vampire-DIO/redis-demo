package com.lw.chain;

import com.lw.chain.handler.Mt2101ReceiptHandler;
import com.lw.chain.handler.Mt8104ReceiptHandler;

import java.util.ArrayList;
import java.util.List;

public class ReceiptHandlerContainer {
    private ReceiptHandlerContainer(){}

    public static List<IReceiptHandler> getReceiptHandlerList(){
        List<IReceiptHandler> receiptHandlerList = new ArrayList<>();
        receiptHandlerList.add(new Mt2101ReceiptHandler());
        receiptHandlerList.add(new Mt8104ReceiptHandler());
        return receiptHandlerList;
    }
}
