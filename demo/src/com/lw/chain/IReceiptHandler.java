package com.lw.chain;

public interface IReceiptHandler {

    void handleReceipt(Receipt receipt, IReceiptHandleChain handleChain);
}
