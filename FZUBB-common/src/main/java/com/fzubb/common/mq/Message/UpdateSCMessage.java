package com.fzubb.common.mq.Message;

import com.fzubb.dao.model.dto.Course;
import lombok.Data;

import java.util.List;

@Data
public class UpdateSCMessage {
    String  id;
    List<Course> courses;
}
