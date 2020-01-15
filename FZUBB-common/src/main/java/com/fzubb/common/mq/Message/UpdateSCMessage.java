package com.fzubb.common.mq.Message;

import lombok.Data;

import java.util.List;

@Data
public class UpdateSCMessage {
    String  id;
    List<Course> courses;
}
