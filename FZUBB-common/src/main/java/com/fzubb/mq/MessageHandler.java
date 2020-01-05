package com.fzubb.mq;

import com.fzubb.ExcepTion.MessageUnhandleException;

public interface MessageHandler {
    void handle(byte[] message) throws MessageUnhandleException;
}
