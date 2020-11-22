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
        try {
            return ResponseEntity.ok(clubAccountService.getAccount());
        }
        catch (NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
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

    @PostMapping("spend/{moneyAmount}")
    public ResponseEntity<String> spendMoney(@PathVariable int moneyAmount){
        try {
            clubAccountService.spendMoney(moneyAmount);
            return ResponseEntity.ok(String.format("Club spent %s dollars",moneyAmount));
        }
        catch (NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PostMapping("earn/{moneyAmount}")
    public ResponseEntity<String> earnMoney(@PathVariable int moneyAmount){
        try {
            clubAccountService.earnMoney(100);
            return ResponseEntity.ok(String.format("Club earned %s dollars",moneyAmount));
        }
        catch (NotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
