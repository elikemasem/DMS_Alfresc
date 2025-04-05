package org.corbantech.DMS_With_Alfresco.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.Header;
import org.corbantech.DMS_With_Alfresco.Utils.JsonUtils;
import org.corbantech.DMS_With_Alfresco.dto.*;
import org.corbantech.DMS_With_Alfresco.service.serviceInterface.AlfrescoServiceInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@Slf4j
public class AlfrescoServiceImpl implements AlfrescoServiceInterface {


    @Value("${alfresco.base-url}")
    private String baseUrl;
    @Value("${alfresco.auth.username}")
    private String defaultUsername;
    @Value("${alfresco.auth.password}")
    private String defaultPassword;
    @Value("${alfresco.auth.url}")
    private String authBaseUrl;


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This is the method used to generate tickets which can be used for authentication and authorizarion
     * It generates a ticket number to be used in doing operations
     * @param userTicketDTO - this contains user details
     * @return - returns the ticket number and the user id
     */
    @Override
    public TicketResponseDTO loginTicketGeneration(UserTicketDTO userTicketDTO) {
        String url = authBaseUrl + "/tickets";
        HttpHeaders headers = new HttpHeaders();

        // Check if userTicketDTO is null or has missing credentials
        if (userTicketDTO == null || userTicketDTO.getPassword() == null || userTicketDTO.getUserId() == null) {
            userTicketDTO = new UserTicketDTO(defaultUsername, defaultPassword); // Use default credentials
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userId", userTicketDTO.getUserId());
        requestBody.put("password", userTicketDTO.getPassword());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();

        Map<String, Object> entry = (Map<String, Object>) responseBody.get("entry");

//        return new TicketResponseDTO((String) entry.get("id"), (String) entry.get("userId"));
        return JsonUtils.fromJsonToObject(responseBody, TicketResponseDTO.class);
    }

    @Override
    public List<SitesDTO> getSites(Integer skipCount, Integer maxItems, String orderBy, String where) {

        log.info("Getting sites");
        String url = baseUrl + "/sites";

        // Build query parameters conditionally
        StringBuilder queryParams = new StringBuilder();
        boolean hasQuery = false;

        if (skipCount != null) {
            queryParams.append(hasQuery ? "&" : "?").append("skipCount=").append(skipCount);
            hasQuery = true;
        }

        if (maxItems != null) {
            queryParams.append(hasQuery ? "&" : "?").append("maxItems=").append(maxItems);
            hasQuery = true;
        }

        if (orderBy != null && !orderBy.isEmpty()) {
            queryParams.append(hasQuery ? "&" : "?").append("orderBy=").append(orderBy);
            hasQuery = true;
        }

        if (where != null && !where.isEmpty()) {
            queryParams.append(hasQuery ? "&" : "?").append("where=").append(where);
        }

        url += queryParams.toString();

        log.debug("This is the request: {}",url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(defaultUsername, defaultPassword);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        // Parse entries to SitesDTO
//        return extractSitesFromMap(response.getBody());
        return JsonUtils.fromJsonToList(response.getBody(), SitesDTO.class);
    }

    @Override
    public SitesDTO getSite(String siteId) {
        String url = baseUrl + "/sites/" + siteId;
        log.debug("This is the request for site by id: {}",url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(defaultUsername, defaultPassword);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

        return JsonUtils.fromJsonToObject(response.getBody(), SitesDTO.class);
    }

    @Override
    public List<ContainerDTO> listContainerBySiteId(String siteId) {
        String url = baseUrl + "/sites/" + siteId + "/containers";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(defaultUsername, defaultPassword);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return JsonUtils.fromJsonToList(response.getBody(), ContainerDTO.class);
    }

    @Override
    public ContainerDTO getContainerBySiteIdAndContainerId(String siteId, String containerId) {
       String url = baseUrl + "/sites/" + siteId + "/containers/" + containerId;
       HttpHeaders headers = new HttpHeaders();
       headers.setContentType(MediaType.APPLICATION_JSON);
       headers.setBasicAuth(defaultUsername, defaultPassword);
       HttpEntity<Void> entity = new HttpEntity<>(headers);
       ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
       return JsonUtils.fromJsonToObject(response.getBody(), ContainerDTO.class);
    }

    @Override
    public NodeDTO getNodesByNodeId(String nodeId) {
        String url = baseUrl + "/nodes/" + nodeId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(defaultUsername, defaultPassword);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return JsonUtils.fromJsonToObject(response.getBody(), NodeDTO.class);
    }

    @Override
    public List<NodeListDTO> getChidrenByNodeId(String nodeId) {
        String url = baseUrl + "/nodes/" + nodeId + "/children";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(defaultUsername, defaultPassword);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return JsonUtils.fromJsonToList(response.getBody(), NodeListDTO.class);
    }

    @Override
    public byte[] downloadContent(String nodeId) {
        String url = baseUrl + "/nodes/" + nodeId + "/content";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(defaultUsername, defaultPassword);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM)); // Expect binary

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                byte[].class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to download content. Status code: " + response.getStatusCode());
        }
    }

    public void uploadFile(String parentNodeId, MultipartFile file) throws IOException {
        String url = baseUrl + "/nodes/" + parentNodeId + "/children";

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(defaultUsername, defaultPassword);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("filedata", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        body.add("name", file.getOriginalFilename());
        body.add("nodeType", "cm:content");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        log.info("Upload response: {}", response.getBody());
    }



/*    private List<SitesDTO> extractSitesFromMap(Map<String, Object> responseBody) {
        List<SitesDTO> sites = new ArrayList<>();

        if (responseBody == null) return sites;

        Map<String, Object> list = (Map<String, Object>) responseBody.get("list");
        if (list == null) return sites;

        List<Map<String, Object>> entries = (List<Map<String, Object>>) list.get("entries");
        if (entries == null) return sites;

        for (Map<String, Object> entryWrapper : entries) {
            Map<String, Object> entry = (Map<String, Object>) entryWrapper.get("entry");
            if (entry != null) {
                SitesDTO site = objectMapper.convertValue(entry, SitesDTO.class);
                sites.add(site);
            }
        }

        return sites;
    }*/

}
