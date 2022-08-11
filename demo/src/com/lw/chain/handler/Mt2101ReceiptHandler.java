package com.lw.chain.handler;

import com.lw.chain.IReceiptHandleChain;
import com.lw.chain.IReceiptHandler;
import com.lw.chain.Receipt;

public class Mt2101ReceiptHandler implements IReceiptHandler {
    @Override
    public void handleReceipt(Receipt receipt, IReceiptHandleChain handleChain) {
        if ("MT2101".equals(receipt.getType())) {
            System.out.println("解析报文MT2101:" + receipt.getMessage());
        }
        //处理不了该回执就往下传递
        else {
            handleChain.handleReceipt(receipt);
        }
    }
}
