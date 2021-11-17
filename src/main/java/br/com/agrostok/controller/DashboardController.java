package br.com.agrostok.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.agrostok.dto.SaledProductDto;
import br.com.agrostok.dto.TotalSaledDto;
import br.com.agrostok.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;


    @GetMapping(value = "/products/top")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<SaledProductDto>> list() {
        return ResponseEntity.ok(service.getTopSaleProducts());
    }

    @GetMapping(value = "/sales/month")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<TotalSaledDto>> listTotalByMonth() {
        return ResponseEntity.ok(service.getSaleTotalPerMonth());
    }

    @GetMapping(value = "/products/sales")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<SaledProductDto>> listSalesByProduct() {
        return ResponseEntity.ok(service.listSalesByProduct());
    }

    @GetMapping(value = "/products/sales/month-and-date")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<SaledProductDto>> listSalesByProductAndMonthAndDate(@RequestParam("startDate") String startDateString) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateString, pattern);
        return ResponseEntity.ok(service.listSalesByProductAndMonth(startDate.withDayOfMonth(1)));
    }


    @GetMapping(value = "/revenue")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<SaledProductDto> listReceitaAndDespesa() {
        return ResponseEntity.ok(service.listReceitaAndDespesa());
    }


}
