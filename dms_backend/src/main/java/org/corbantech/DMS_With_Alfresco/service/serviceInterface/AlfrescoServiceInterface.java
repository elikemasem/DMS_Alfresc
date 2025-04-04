package org.corbantech.DMS_With_Alfresco.service.serviceInterface;


import org.corbantech.DMS_With_Alfresco.dto.SitesDTO;
import org.corbantech.DMS_With_Alfresco.dto.TicketResponseDTO;
import org.corbantech.DMS_With_Alfresco.dto.UserTicketDTO;

import java.util.List;

public interface AlfrescoServiceInterface {
    TicketResponseDTO loginTicketGeneration(UserTicketDTO userTicketDTO);
    List<SitesDTO> getSites(Integer skipCount, Integer maxItems, String orderBy, String where);
}
