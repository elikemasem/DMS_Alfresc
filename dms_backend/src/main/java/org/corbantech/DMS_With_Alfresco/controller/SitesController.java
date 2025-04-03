package org.corbantech.DMS_With_Alfresco.controller;

import org.corbantech.DMS_With_Alfresco.dto.ResponseDTO;
import org.corbantech.DMS_With_Alfresco.dto.SitesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sites")
public class SitesController {

    @GetMapping("")
    public ResponseDTO getAllSites(@RequestParam(required = false, defaultValue = "0") Integer pageNo, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<SitesDTO> sitesDTOList = SitesDTO.generateSampleSites();

        // Create a Pageable object
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        // Calculate pagination
        int start = Math.min((int) pageable.getOffset(), sitesDTOList.size());
        int end = Math.min(start + pageable.getPageSize(), sitesDTOList.size());
        List<SitesDTO> pagedList = sitesDTOList.subList(start, end);

        // Create a Page object
        Page<SitesDTO> pageData = new PageImpl<>(pagedList, pageable, sitesDTOList.size());
        List<SitesDTO> list = pageData.getContent();
        return new ResponseDTO(200, "Success", LocalDateTime.now(), "/api/sites", list);
    }

    @GetMapping("/{id}")
    public ResponseDTO getSiteById(@RequestParam(required = true ) UUID id) {
        if (id == null || id.toString().isEmpty()) {
            return new ResponseDTO(400, "Id is required", LocalDateTime.now(), "/api/sites/", null);
        }
        List<SitesDTO> sitesDTOList = SitesDTO.generateSampleSites();
        SitesDTO sitesDTO = sitesDTOList.stream().filter(site -> site.getId().equals(id)).findFirst().get();
        return new ResponseDTO(200, "Success", LocalDateTime.now(), "/api/sites/" + id.toString(), sitesDTO);
    }
}
