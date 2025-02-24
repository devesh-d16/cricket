package com.devesh.cricket.controller;


import com.devesh.cricket.model.Over;
import com.devesh.cricket.service.OverService;
import com.devesh.cricket.service.queryService.OverQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apis/overs")
@RequiredArgsConstructor
public class OverController {

    private final OverQueryService overQueryService;

    @GetMapping
    public ResponseEntity<?> getAllOvers() {
        try{
            List<Over> overs = overQueryService.findAllOvers();
            if(overs.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(overs);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{overId}")
    public ResponseEntity<?> getOverById(@PathVariable Long overId) {
        try {
            Over over = overQueryService.findOverById(overId);
            if(over == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(over);
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/inning/{inningId}")
    public ResponseEntity<?> getOverByInningId(@PathVariable Long inningId) {
        try{
            List<Over> overs = overQueryService.getOverByInningId(inningId);
            if(overs.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(overs);
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
