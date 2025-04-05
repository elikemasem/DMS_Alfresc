package org.corbantech.DMS_With_Alfresco.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertiesDTO {
    private OwnerDTO cmOwner;

    // Getters and setters

    @JsonProperty("cm:owner")
    public OwnerDTO getCmOwner() {
        return cmOwner;
    }

    @JsonProperty("cm:owner")
    public void setCmOwner(OwnerDTO cmOwner) {
        this.cmOwner = cmOwner;
    }
}
