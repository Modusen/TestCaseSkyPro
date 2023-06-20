package com.example.testcaseskypro.service;

import com.example.testcaseskypro.entity.Socks;
import com.example.testcaseskypro.repository.SocksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SocksService {
    private final SocksRepository socksRepository;

    public Socks addSocks(Socks socks) {
        return socksRepository.save(socks);
    }

    public Socks subtractSocks(Socks socks) {
        Socks foundSocks = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        foundSocks.setQuantity(foundSocks.getQuantity() - socks.getQuantity());
        return socksRepository.save(foundSocks);
    }
    public List<Socks> getSocksByParametersWhereCottonPartIsGreaterThan(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartIsGreaterThan(color, cottonPart);
    }

    public List<Socks> getSocksByParametersWhereCottonPartIsLessThan(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartIsLessThan(color, cottonPart);
    }

    public List<Socks> getSocksByParametersWhereCottonPartIsEqual(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartEquals(color, cottonPart);
    }


}
