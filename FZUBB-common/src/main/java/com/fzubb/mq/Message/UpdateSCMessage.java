package com.fzubb.mq.Message;

import com.fzubb.model.dto.Course;
import lombok.Data;

import java.util.List;

@Data
public class UpdateSCMessage {
    String  id;
    List<Course> courses;
}
