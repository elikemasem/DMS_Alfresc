package org.corbantech.DMS_With_Alfresco.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SitesDTO {
    private UUID id;
    private String shortName;
    private String title;
    private String description;

    public static List<SitesDTO> generateSampleSites() {
        List<SitesDTO> sitesList = new ArrayList<>();
        sitesList.add(new SitesDTO(UUID.fromString("b9a545c2-654f-4c9c-b4f6-a2dd5e1ce561"), "google", "Google", "A search engine and tech company."));
        sitesList.add(new SitesDTO(UUID.fromString("f6716da9-e0b5-43be-88a7-c600653f8358"), "facebook", "Facebook", "A social media platform."));
        sitesList.add(new SitesDTO(UUID.fromString("2fe2765a-0033-4cf3-81df-4bd5648bfdbb"), "amazon", "Amazon", "An online marketplace."));
        sitesList.add(new SitesDTO(UUID.fromString("53b7071c-ca46-47f4-9938-67766001be5f"), "apple", "Apple Inc.", "A technology company."));
        sitesList.add(new SitesDTO(UUID.fromString("3b450309-d2e2-4d1a-971b-6865606dd4e8"), "microsoft", "Microsoft", "A software and tech company."));
        sitesList.add(new SitesDTO(UUID.fromString("6fc4ad7f-1b54-4e19-bc55-7faf2edd6afd"), "twitter", "Twitter", "A microblogging platform."));
        sitesList.add(new SitesDTO(UUID.fromString("133b5239-197c-40fe-866c-750913e13e3c"), "netflix", "Netflix", "A streaming service."));
        sitesList.add(new SitesDTO(UUID.fromString("9bccbab0-e5c8-4799-9715-9ab93c373fbe"), "youtube", "YouTube", "A video-sharing platform."));
        sitesList.add(new SitesDTO(UUID.fromString("789bb1b9-2cdf-4dfa-a18f-80cf8d13ee51"), "linkedin", "LinkedIn", "A professional networking site."));
        sitesList.add(new SitesDTO(UUID.fromString("304ed717-768f-4042-a6c8-160847c85841"), "wikipedia", "Wikipedia", "A free online encyclopedia."));
        sitesList.add(new SitesDTO(UUID.fromString("548a6b5d-7904-4401-99bd-e6ad18dd1b77"), "instagram", "Instagram", "A photo and video sharing app."));
        sitesList.add(new SitesDTO(UUID.fromString("4f57d480-8c48-4060-b925-4eb687604e1d"), "github", "GitHub", "A code hosting platform for version control."));
        sitesList.add(new SitesDTO(UUID.fromString("2449d3f9-4c4f-469e-9bc3-80457983ab91"), "reddit", "Reddit", "A discussion and community forum site."));
        sitesList.add(new SitesDTO(UUID.fromString("29410a23-4d57-497e-a819-0c94b968df94"), "ebay", "eBay", "An online auction and shopping website."));
        sitesList.add(new SitesDTO(UUID.fromString("0c42360b-93eb-4fc4-989c-d7c37e2fa030"), "spotify", "Spotify", "A music streaming service."));
        sitesList.add(new SitesDTO(UUID.fromString("2153be94-a246-4771-b181-c85c28a3577b"), "tesla", "Tesla", "An electric vehicle and clean energy company."));
        sitesList.add(new SitesDTO(UUID.fromString("fdfe7c1d-03b1-47e2-89b6-f520c76c30ae"), "zoom", "Zoom", "A video conferencing application."));
        sitesList.add(new SitesDTO(UUID.fromString("9e9bc5c0-a163-4e0d-a8ca-e0e2a357a663"), "slack", "Slack", "A messaging platform for teams."));
        sitesList.add(new SitesDTO(UUID.fromString("05ac4602-fc76-4a4f-aa11-0b6bbdd89ebc"), "dropbox", "Dropbox", "A cloud file storage service."));
        sitesList.add(new SitesDTO(UUID.fromString("5709a1d8-1f4c-418e-b3d4-76d9a92699bf"), "adobe", "Adobe", "A creative software company."));
        return sitesList;
    }
}
