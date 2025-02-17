package com.devesh.cricket.service;

import com.devesh.cricket.model.enums.Status;
import com.devesh.cricket.model.Tournament;
import com.devesh.cricket.repository.TournamentRepository;
import com.devesh.cricket.utils.GameUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TournamentService {
    private final GameUtils utils;
    private final TournamentRepository tournamentRepository;

    public TournamentService(GameUtils utils, TournamentRepository tournamentRepository) {
        this.utils = utils;
        this.tournamentRepository = tournamentRepository;
    }


    public Tournament saveTournament(Tournament tournament) {
        Tournament saveTournament = new Tournament();
        saveTournament.setTournamentName(tournament.getTournamentName());
        saveTournament.setLocation(tournament.getLocation());
        saveTournament.setStartDate(tournament.getStartDate());
        saveTournament.setTotalTeams(tournament.getTotalTeams());
        saveTournament.setFormat(tournament.getFormat());

//        int totalMatches = utils.getTotalMatches(tournament.getTotalTeams());
        int totalMatches = 8;

        int totalDays = totalMatches + 2;

        saveTournament.setTotalMatches(totalMatches);
        saveTournament.setStatus(Status.UPCOMING);
        saveTournament.setEndDate(LocalDate.from(tournament.getStartDate()).plusDays(totalDays));


        return tournamentRepository.save(saveTournament);
    }

    public Tournament getTournamentById(Long tournamentId) {
        return tournamentRepository.findById(tournamentId).orElseThrow(() -> new IllegalArgumentException("Tournament not found"));
    }

    public Tournament getTournamentByName(String tournamentName) {
        return tournamentRepository.findByTournamentName(tournamentName);
    }

    public List<Tournament> getTournamentByLocation(String tournamentLocation) {
        return tournamentRepository.findAll().stream().filter(tournament ->  tournament.getLocation().equalsIgnoreCase(tournamentLocation)).toList();
    }

    public List<Tournament> getTournamentByStatus(String tournamentStatus) {
        return tournamentRepository.findAll().stream().filter(tournament -> tournament.getStatus().name().equalsIgnoreCase(tournamentStatus)).toList();
    }

    public List<Tournament> getTournamentByFormat(String tournamentFormat) {
        return tournamentRepository.findAll().stream().filter(tournament -> tournament.getFormat().name().equalsIgnoreCase(tournamentFormat)).toList();
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }
}
