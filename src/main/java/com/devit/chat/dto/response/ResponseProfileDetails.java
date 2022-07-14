package com.devit.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ResponseProfileDetails {
    private Date timestamp;
    private Object profile;
    private Object resume;
    private int httpStatus;
    private String path;
}
