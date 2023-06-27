package com.example.testcaseskypro.service;

import com.example.testcaseskypro.entity.Socks;
import com.example.testcaseskypro.repository.SocksRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocksService {
    private final SocksRepository socksRepository;

    /**
     * Adds new socks with given parameters or increases quantity of existed one.
     *
     * @return A socks-record that was saved in database.
     */
    public Socks addSocks(Socks socks) {
        Optional<Socks> foundSocks = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (foundSocks.isEmpty()) {
            return socksRepository.save(socks);
        }
        foundSocks.get().setQuantity(foundSocks.get().getQuantity() + socks.getQuantity());
        return socksRepository.save(foundSocks.get());
    }

    /**
     * Subtracts socks quantity with given parameters if given
     * entity exists in database and has needed quantity.
     *
     * @return A socks-record that was saved in database.
     */
    public Optional<Socks> subtractSocks(Socks socks) {
        Optional<Socks> foundSocks = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (foundSocks.isEmpty() || foundSocks.get().getQuantity() < socks.getQuantity()) {
            return Optional.empty();
        }
        foundSocks.get().setQuantity(foundSocks.get().getQuantity() - socks.getQuantity());
        return Optional.of(socksRepository.save(foundSocks.get()));
    }

    /**
     * Finds socks with given color and cottonPart more than specified value
     *
     * @return List of found entities.
     */
    public List<Socks> getSocksByParametersWhereCottonPartIsGreaterThan(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartIsGreaterThan(color, cottonPart);
    }

    /**
     * Finds socks with given color and cottonPart less than specified value
     *
     * @return List of found entities.
     */
    public List<Socks> getSocksByParametersWhereCottonPartIsLessThan(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartIsLessThan(color, cottonPart);
    }

    /**
     * Finds socks with given color and cottonPart equals to specified value
     *
     * @return List of found entities.
     */
    public List<Socks> getSocksByParametersWhereCottonPartIsEqual(String color, Integer cottonPart) {
        return socksRepository.findAllByColorEqualsAndCottonPartEquals(color, cottonPart);
    }


}
