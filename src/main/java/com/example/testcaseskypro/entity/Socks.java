package com.example.testcaseskypro.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "socks")
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String color;
    Integer cottonPart;
    Integer quantity;
}
