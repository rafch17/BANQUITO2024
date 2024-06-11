package com.banquito.core.bank.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.banquito.core.bank.controller.dto.BankUserDTO;
import com.banquito.core.bank.model.Bank;
import com.banquito.core.bank.model.BankUser;
import com.banquito.core.bank.repository.BankRepository;
import com.banquito.core.bank.repository.BankUserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BankUserService {

    private final BankUserRepository repository;
    private final BankRepository bankRepository;

    public BankUserService(BankUserRepository repository, BankRepository bankRepository) {
        this.repository = repository;
        this.bankRepository = bankRepository;
    }

    @Transactional(Transactional.TxType.NEVER)
    public BankUser obtainUserById(Long id) {
        Optional<BankUser> userOpt = this.repository.findById(id);
        if (userOpt.isPresent()) {
            return userOpt.get();
        } else {
            throw new RuntimeException("No existe el usuario con id: " + id);
        }
    }

    public BankUser obtainByUserName(String userName) {
        BankUser user = this.repository.findByUsername(userName);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("No existe usuario con el userName:" + userName);
        }
    }

    public BankUser obtainByEmail(String email) {
        BankUser user = this.repository.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("No existe usuario con el email:" + email);
        }
    }

    public BankUser create(BankUserDTO dto) {
        if (this.repository.findByUsername(dto.getUserName()) != null) {
            throw new RuntimeException("usuario repetido");
        }
        if (this.repository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("Email repetido");
        }
        BankUser user = new BankUser();
        Bank bank = this.bankRepository.findAll().getFirst();
        user.setCodeBank(bank.getCode());
        user.setCodeRole(dto.getCodeRole());
        user.setCreationDate(new Date());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setFullName(dto.getFullName());
        user.setLastName(dto.getLastName());
        user.setState("BLO");
        user.setTypeUser("TEL");
        user.setUsername(dto.getUserName());
        return this.repository.save(user);
    }


}
