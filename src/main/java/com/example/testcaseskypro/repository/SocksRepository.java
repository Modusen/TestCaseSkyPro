package com.example.testcaseskypro.repository;

import com.example.testcaseskypro.entity.Socks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocksRepository extends JpaRepository<Socks, Integer> {
    List<Socks> findAllByColorEqualsAndCottonPartEquals(String color, Integer cottonPart);
    List<Socks> findAllByColorEqualsAndCottonPartIsGreaterThan(String color, Integer cottonPart);
    List<Socks> findAllByColorEqualsAndCottonPartIsLessThan(String color, Integer cottonPart);

    Socks findByColorAndCottonPart(String color, Integer cottonPart);
}
