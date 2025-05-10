package org.corbantech.DMS_With_Alfresco.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is for all nodes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeListDTO {
    private String createdAt;
    private boolean isFolder;
    private boolean isFile;
    private NodeUserDTO createdByUser;
    private String modifiedAt;
    private NodeUserDTO modifiedByUser;
    private String name;
    private String id;
    private String nodeType;
    private ContentDTO content;
    private String parentId;
}
