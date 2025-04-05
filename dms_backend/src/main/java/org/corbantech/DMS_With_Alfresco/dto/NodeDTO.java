package org.corbantech.DMS_With_Alfresco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NodeDTO {
    private List<String> aspectNames;
    private String createdAt;
    private boolean isFolder;
    private boolean isFile;
    private NodeUserDTO createdByUser;
    private String modifiedAt;
    private NodeUserDTO modifiedByUser;
    private String name;
    private String id;
    private String nodeType;
    private PropertiesDTO properties;
    private String parentId;
}
