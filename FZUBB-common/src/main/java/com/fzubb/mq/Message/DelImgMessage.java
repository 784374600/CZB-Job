package com.fzubb.mq.Message;

import lombok.Data;

@Data
public class DelImgMessage {
    String path;
    String name;
}
