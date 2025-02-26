package com.devesh.cricket.dao;

import com.devesh.cricket.entity.Inning;
import com.devesh.cricket.repository.InningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InningsDAO {
    private final InningRepository inningRepository;

    public List<Inning> getAllInnings() {
        return inningRepository.findAll();
    }

    public Inning getInningById(Long inningId) {
        return inningRepository.findById(inningId).orElse(null);
    }

    public List<Inning> getInningsByMatch(Long matchId) {
        return inningRepository.getAllByMatch_Id(matchId);
    }
}
