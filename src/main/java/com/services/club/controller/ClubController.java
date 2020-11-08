package com.services.club.controller;

import com.services.club.dto.ClubDTO;
import com.services.club.entity.Club;
import com.services.club.service.ClubService;
import javassist.NotFoundException;
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

    @PatchMapping("{moneyAmount}")
    public ResponseEntity<?> updateClub(@PathVariable int moneyAmount){
        try {
            clubAccountService.saveClub(moneyAmount);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Changed the money amount of club.");

        }
        catch (NotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
        }
    }
}
