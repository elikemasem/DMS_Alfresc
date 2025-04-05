package org.corbantech.DMS_With_Alfresco.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDTO {
    private String mimeType;
    private String mimeTypeName;
    private long sizeInBytes;
    private String encoding;
}
