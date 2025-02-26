package com.devesh.cricket.dao;

import com.devesh.cricket.entity.Over;
import com.devesh.cricket.repository.OverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OverDAO {

    private final OverRepository overRepository;
    public List<Over> findAllOvers() {
        return overRepository.findAll();
    }


    public Over findOverById(Long overId) {
        return overRepository.findById(overId).orElse(null);
    }

    public List<Over> getOverByInningId(Long inningId) {
        return overRepository.findOversByInning_Id(inningId);
    }
}
