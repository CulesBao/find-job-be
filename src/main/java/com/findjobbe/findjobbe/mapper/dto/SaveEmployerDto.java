package com.findjobbe.findjobbe.mapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveEmployerDto extends BaseProfile {
    private UUID id;
    private String name;
    private String location;
    private String logoUrl;
    private int jobCount;
}
