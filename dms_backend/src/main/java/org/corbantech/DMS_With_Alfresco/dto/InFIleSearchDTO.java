package org.corbantech.DMS_With_Alfresco.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InFIleSearchDTO {
    private QueryDTO query;
    private List<String> include;
    private PagingDTO paging;

}



