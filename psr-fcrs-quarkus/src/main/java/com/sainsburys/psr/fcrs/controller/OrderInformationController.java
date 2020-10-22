package com.sainsburys.psr.fcrs.controller;

import com.sainsburys.psr.fcrs.dto.OrderInformation;
import com.sainsburys.psr.fcrs.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderInformationController {

  private final OrderService orderService;

  @PostMapping(value = "orderInformation")
  public ResponseEntity<List<OrderInformation>> orderInformation(@RequestBody List<String> orderNumbers) {
    List<OrderInformation> orderInformation = orderService.orderInformation(orderNumbers);

    return new ResponseEntity<>(orderInformation, HttpStatus.OK);
  }
}
