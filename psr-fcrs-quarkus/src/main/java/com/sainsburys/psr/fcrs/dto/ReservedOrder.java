package com.sainsburys.psr.fcrs.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ReservedOrder {

    @NotNull
    @JsonProperty(value = "orderNumber", required = true)
    private String orderNumber;

    @NotNull
    @JsonProperty(value = "flrsId", required = true)
    private String flrsId;

    @NotNull
    @JsonProperty(value = "startDateTime", required = true)
    private LocalDateTime startDateTime;

    @NotNull
    @JsonProperty(value = "endDateTime", required = true)
    private LocalDateTime endDateTime;

    @NotNull
    @JsonProperty(value = "type", required = true)
    private String type;

    @JsonCreator
    public ReservedOrder(String orderNumber,
                         String flrsId,
                         LocalDateTime startDateTime,
                         LocalDateTime endDateTime,
                         String type) {
        this.orderNumber = orderNumber;
        this.flrsId = flrsId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.type = type;
    }
}
