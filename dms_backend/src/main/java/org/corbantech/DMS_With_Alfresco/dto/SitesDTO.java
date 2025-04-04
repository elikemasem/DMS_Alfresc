package org.corbantech.DMS_With_Alfresco.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.id.GUIDGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SitesDTO {
    private String role;
    private String visibility;
    private String guid;
    private String description;
    private String id;
    private String preset;
    private String title;
}
