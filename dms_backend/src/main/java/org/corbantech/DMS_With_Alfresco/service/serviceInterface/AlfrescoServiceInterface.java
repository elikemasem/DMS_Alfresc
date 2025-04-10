package org.corbantech.DMS_With_Alfresco.service.serviceInterface;


import org.corbantech.DMS_With_Alfresco.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlfrescoServiceInterface {
    TicketResponseDTO loginTicketGeneration(UserTicketDTO userTicketDTO);
    List<SitesDTO> getSites(Integer skipCount, Integer maxItems, String orderBy, String where);
    SitesDTO getSite(String siteId);
    List<ContainerDTO> listContainerBySiteId(String siteId);
    ContainerDTO getContainerBySiteIdAndContainerId(String siteId, String containerId);
    NodeDTO getNodesByNodeId(String nodeId);
    List<NodeListDTO> getChidrenByNodeId(String nodeId);
    byte[] downloadContent(String nodeId);
    void uploadFile(String nodeId, MultipartFile file) throws IOException;
    List<NodeListDTO> returnInfileSearch(String text, Integer size, Integer skipCount);

}
