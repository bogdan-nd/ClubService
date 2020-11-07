package com.services.club.controller;

import com.services.club.dto.ClubDTO;
import com.services.club.entity.Club;
import com.services.club.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;


@RestController
@RequestMapping("club")
@AllArgsConstructor
public class ClubController {
    private final ClubService clubAccountService;

    @GetMapping
    public ResponseEntity<Club> getClub(){
        return ResponseEntity.ok(clubAccountService.getAccount());
    }

    @PostMapping
    public ResponseEntity<?> addClub(@RequestBody ClubDTO clubAccountDTO){
        Club newClubAccount = new Club(clubAccountDTO.getSeedCapital());
        try{
            Club club = clubAccountService.addClubAccount(newClubAccount);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(club);

        } catch (InstanceAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Club> updateClub(@RequestBody Club club){
        return ResponseEntity.ok(clubAccountService.saveClub(club));
    }
}
