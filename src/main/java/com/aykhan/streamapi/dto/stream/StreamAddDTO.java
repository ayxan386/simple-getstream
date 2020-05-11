package com.aykhan.streamapi.dto.stream;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StreamAddDTO {
    private String foreignID;
    private String message;
}
