package com.fzubb.common.mq;

import com.fzubb.common.ExcepTion.MessageUnhandleException;

public interface MessageHandler {
    void handle(byte[] message) throws MessageUnhandleException;
}
