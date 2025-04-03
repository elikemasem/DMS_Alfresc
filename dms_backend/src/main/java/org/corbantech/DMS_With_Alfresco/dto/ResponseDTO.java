package org.corbantech.DMS_With_Alfresco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Integer statusCode;
    private String message;
    private LocalDateTime timeRequested;
    private String url;
    private Object data;
}
