package org.corbantech.DMS_With_Alfresco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingDTO {
    private int maxItems;
    private int skipCount;
}
