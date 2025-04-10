package org.corbantech.DMS_With_Alfresco.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.extern.slf4j.Slf4j;
import org.corbantech.DMS_With_Alfresco.dto.*;
import org.corbantech.DMS_With_Alfresco.service.serviceImpl.AlfrescoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/alfresco")
@Slf4j
public class AlfrescoController {

    @Autowired
    private AlfrescoServiceImpl alfrescoService;

    @PostMapping("/tickets")
    public ResponseEntity<ResponseDTO> getTicket(@RequestBody UserTicketDTO userTicketDTO) {
        try {
            log.info("Generating Alfresco Ticket");
            Object response = alfrescoService.loginTicketGeneration(userTicketDTO);
            log.debug("Alfresco Ticket Response: {} and body {}", response, userTicketDTO);
            return ResponseEntity.ok(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/tickets", response));
        }catch (Exception e) {
            log.error("Error generating Alfresco Ticket: {}", e.getMessage(), e);
            ResponseDTO errorResponse = new ResponseDTO(
                    500,
                    "Error",
                    LocalDateTime.now(),
                    "/alfresco/tickets",
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/sites")
    public ResponseEntity<ResponseDTO> listSites(@RequestParam(name = "skipCount", defaultValue = "0", required = false) Integer skipCount,
                                 @RequestParam(name = "maxItems", defaultValue = "100", required = false) Integer maxItems,
                                 @RequestParam(name = "orderBy", required = false) String orderBy,
                                 @RequestParam(name = "where", required = false) String where) {

        try {
            if (orderBy != null) {
                if (!(orderBy.equalsIgnoreCase("desc") || orderBy.equalsIgnoreCase("asc"))) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new ResponseDTO(400, "Invalid Order By", LocalDateTime.now(), "/alfresco/sites", null));
                }

                orderBy = orderBy.toUpperCase(); // safe now
            }

            List<SitesDTO> sites = alfrescoService.getSites(skipCount, maxItems, orderBy, where);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/sites", sites));
        }catch (Exception e) {
            log.error("Error listing Alfresco sites: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/sites", null));
        }


    }

    @GetMapping("/sites/{siteId}")
    public ResponseEntity<ResponseDTO> getSiteById(@PathVariable(name = "siteId", required = true) String siteId) {
        try {
            if (siteId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Invalid Site Id", LocalDateTime.now(), "/alfresco/sites", null));
            }
            SitesDTO sitesDTO = alfrescoService.getSite(siteId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/sites", sitesDTO));
        } catch (Exception e) {
            log.error("Error retrieving Alfresco site: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/sites", null));
        }

    }

    @GetMapping("/sites/{siteId}/containers")
    public ResponseEntity<ResponseDTO> getContainersBySiteId(@PathVariable(name = "siteId", required = true) String siteId) {
        try {
            if (siteId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Site Id must be present", LocalDateTime.now(), "/alfresco/sites", null));
            }
            List<ContainerDTO> containerDTOS = alfrescoService.listContainerBySiteId(siteId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/containers", containerDTOS));
        }catch (Exception e) {
            log.error("Error retrieving Alfresco site: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/sites", null));
        }
    }

    @GetMapping("/sites/{siteId}/containers/{containerId}")
    public ResponseEntity<ResponseDTO> getContainerDetailsWithContainerIdAndSiteId(@PathVariable(name = "siteId", required = true) String siteId, @PathVariable(name = "containerId", required = true) String containerId) {

        try {
            if (containerId == null || containerId.isEmpty() || siteId == null || siteId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Site Id and Container Id Required", LocalDateTime.now(), "/alfresco/containers", null));
            }
            ContainerDTO containerDTO = alfrescoService.getContainerBySiteIdAndContainerId(siteId, containerId);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/containers", containerDTO));

        } catch (Exception e) {
            log.error("Error retrieving Alfresco site: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/sites", null));
        }
    }

    @GetMapping("/nodes/{nodeId}")
    public ResponseEntity<ResponseDTO> listNodeById(@PathVariable(name = "nodeId",  required = true) String nodeId){
        log.info("This in the list node ");
        try {
            if (nodeId == null || nodeId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Node Id Required", LocalDateTime.now(), "/alfresco/nodes", null));
            } else if (!(nodeId.equalsIgnoreCase("-root-") || nodeId.equalsIgnoreCase("-shared-") || nodeId.equalsIgnoreCase("-my-"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Invalid Node Id", LocalDateTime.now(), "/alfresco/nodes", null));
            }
            log.debug("Trying to list node {}", nodeId.toLowerCase());
            NodeDTO nodeDTO = alfrescoService.getNodesByNodeId(nodeId.toLowerCase());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/nodes", nodeDTO));
        }catch (Exception e) {
            log.error("Error retrieving Alfresco node: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/nodes", null));
        }
    }

    @GetMapping("/nodes/{nodeId}/children")
    public ResponseEntity<ResponseDTO> listNodeChildren(@PathVariable(name = "nodeId", required = true) String nodeId){
        log.info("This in the list node ");
        try {
            if (nodeId == null || nodeId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Node Id Required", LocalDateTime.now(), "/alfresco/nodes", null));
            } else if (!(nodeId.equalsIgnoreCase("-root-") || nodeId.equalsIgnoreCase("-shared-") || nodeId.equalsIgnoreCase("-my-"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO(400, "Invalid Node Id", LocalDateTime.now(), "/alfresco/nodes", null));
            }
            log.debug("Trying to list node {}", nodeId.toLowerCase());
            List<NodeListDTO> nodeDTO = alfrescoService.getChidrenByNodeId(nodeId.toLowerCase());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/nodes", nodeDTO));
        }catch (Exception e) {
            log.error("Error retrieving Alfresco node: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/nodes", null));
        }
    }

    @GetMapping("/node/{nodeId}/download")
    public ResponseEntity<?> downloadContentOfNode(@PathVariable(name = "nodeId") String nodeId) {
        log.info("Download of content for node: {}", nodeId);

        try {
            if (nodeId == null || nodeId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDTO(400, "Node Id Required", LocalDateTime.now(), "/alfresco/nodes", null));
            }

            byte[] fileData = alfrescoService.downloadContent(nodeId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition
                    .attachment()
                    .filename("downloaded-file") // Optional: You could look up actual file name from metadata
                    .build());

            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error downloading Alfresco node: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(500, "Error Occurred", LocalDateTime.now(), "/alfresco/nodes", null));
        }
    }

    @Operation(summary = "Upload file to Alfresco")
    @PostMapping("/upload/{parentId}")
    public ResponseEntity<?> uploadFile(
            @PathVariable String parentId,
            @Parameter(description = "File to upload", content = @Content(mediaType = "multipart/form-data"))
            @RequestParam("file") MultipartFile file
    ) {
        try {
            alfrescoService.uploadFile(parentId, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            log.error("Upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Search for text in a file")
    @GetMapping("/search/in-file")
    public ResponseEntity<ResponseDTO> searchInfile(@RequestParam(name = "text", required = true) String text,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                    @RequestParam(name = "skipCount", defaultValue = "0") Integer skipCount){
        try {
            log.info("Searching for text in a file");
            log.debug("Text: {} size: {}, skipCount: {}", text, size, skipCount);
            List<NodeListDTO> list = alfrescoService.returnInfileSearch(text, size, skipCount);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "Success", LocalDateTime.now(), "/alfresco/search", list));
        } catch (Exception e) {
            log.error("Search failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(500, "Error Occured", LocalDateTime.now(), "/alfresco/search", e.getMessage()));
        }
    }




}
