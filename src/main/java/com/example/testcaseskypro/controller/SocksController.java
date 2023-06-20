package com.example.testcaseskypro.controller;

import com.example.testcaseskypro.entity.Socks;
import com.example.testcaseskypro.service.SocksService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/socks")
@AllArgsConstructor
public class SocksController {

    private final SocksService socksService;

    @GetMapping("greetings")
    public String sayHello() {
        return "Hello from SocksController";
    }

    @PostMapping("/income")
    public ResponseEntity<Socks> addSocks(@RequestBody Socks socks) {
        if (socks.getCottonPart() < 0 || socks.getCottonPart() > 100 || socks.getQuantity() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            return ResponseEntity.ok(socksService.addSocks(socks));
        }
    }

    @PostMapping("/outcome")
    public ResponseEntity<Socks> subtractSocks(@RequestBody Socks socks) {
        return ResponseEntity.ok(socksService.subtractSocks(socks));
    }

    @GetMapping()
    public ResponseEntity<Integer> getOverallQuantityOfSocks(@RequestParam("color") String color,
                                                             @RequestParam("operation") String operation,
                                                             @RequestParam("cottonPart") int cottonPart) {
        List<Socks> result = getSocks(color, operation, cottonPart).getBody();
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        int sum = 0;
        for (Socks socks: result) {
            sum += socks.getQuantity();
        }
        return ResponseEntity.ok(sum);
    }

    @GetMapping("/getSocks")
    public ResponseEntity<List<Socks>> getSocks(@RequestParam("color") String color,
                                                @RequestParam("operation") String operation,
                                                @RequestParam("cottonPart") int cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        switch (operation) {
            case ("moreThan"):
                return ResponseEntity.ok(socksService.getSocksByParametersWhereCottonPartGreaterThan(color, cottonPart));
            case ("lessThan"):
                return ResponseEntity.ok(socksService.getSocksByParametersWhereCottonPartLessThan(color, cottonPart));
            case ("equal"):
                return ResponseEntity.ok(socksService.getSocksByParametersWhereCottonPartEqual(color, cottonPart));
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
