package com.devesh.cricket.controller;

import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.dao.InningsDAO;
import com.devesh.cricket.service.TeamService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/apis/innings")
@RequiredArgsConstructor
public class InningController {

    private final TeamService teamService;
    private final InningsDAO inningsDAO;


    @GetMapping
    public ResponseEntity<?> getAllInnings(){
        try{
            List<Inning> innings = inningsDAO.getAllInnings();
            if(innings.isEmpty()){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(innings);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{inningId}")
    public ResponseEntity<?> getInningById(@PathVariable Long inningId){
        try{
            Inning inning = inningsDAO.getInningById(inningId);
            if(inning == null){
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(inning);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getInningByMatchId(@PathVariable Long matchId){
        try{
            List<Inning> innings = inningsDAO.getInningsByMatch(matchId);
            if(innings.isEmpty()){
                return ResponseEntity.
                        status(HttpStatus.NOT_FOUND)
                        .build();
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(innings);
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
