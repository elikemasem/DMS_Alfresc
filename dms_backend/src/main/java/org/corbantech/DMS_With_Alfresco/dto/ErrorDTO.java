package org.corbantech.DMS_With_Alfresco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {
    private String errorKey;
    private int statusCode;
    private String briefSummary;
    private String stackTrace;
    private String descriptionURL;
    private String logId;

    // Getters and setters
}
