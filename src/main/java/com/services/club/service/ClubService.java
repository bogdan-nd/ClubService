package com.services.club.service;

import com.services.club.entity.Club;
import com.services.club.repo.ClubRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClubService {
    private final ClubRepository repository;

    @Autowired
    public ClubService(ClubRepository repository){
        this.repository = repository;
    }

    @Transactional
    public Club saveClubAccount(Club clubAccount) {
        return repository.save(clubAccount);
    }

    @Transactional
    public Club addClubAccount(Club clubAccount) throws InstanceAlreadyExistsException {
        List<Club> clubs = repository.findAll();
        if (clubs.size() != 0)
            throw new InstanceAlreadyExistsException("Club Account already exist");
        return repository.save(clubAccount);

    }

    @Transactional
    public Club getAccount(){
        List<Club> clubs = repository.findAll();
        if (clubs.size() == 0)
            return null;
        return clubs.get(0);
    }

    @Transactional
    public Club spendMoney(int moneyAmount){
        Club club = getAccount();
        club.spendMoney(moneyAmount);
        saveClubAccount(club);
        return club;
    }

    @Transactional
    public Club earnMoney(int moneyAmount){
        Club club = getAccount();
        club.earnMoney(moneyAmount);
        saveClubAccount(club);
        return club;
    }

    @Transactional
    public Club saveClub(int moneyAmount) throws NotFoundException {
        Club club = getAccount();
        if(club == null)
            throw new NotFoundException("No Club account");

        club.setMoneyAmount(moneyAmount);
        return repository.save(club);
    }
}
