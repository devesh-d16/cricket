package com.devesh.cricket.dto;

import lombok.Data;


import java.util.List;

@Data
public class TeamRequestDTO {

    String teamName;
    List<String> players;
}
