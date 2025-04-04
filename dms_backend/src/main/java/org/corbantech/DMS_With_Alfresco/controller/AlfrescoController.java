package org.corbantech.DMS_With_Alfresco.controller;


import lombok.extern.slf4j.Slf4j;
import org.corbantech.DMS_With_Alfresco.dto.ResponseDTO;
import org.corbantech.DMS_With_Alfresco.dto.SitesDTO;
import org.corbantech.DMS_With_Alfresco.dto.UserTicketDTO;
import org.corbantech.DMS_With_Alfresco.service.serviceImpl.AlfrescoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



}
