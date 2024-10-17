package com.f4.fqs.payment;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    //결제 요청
    @PostMapping("/ready")
    public ResponseEntity<PaymentReadyDto> readyToPay(@ModelAttribute PaymentReadyRequest request) {

        log.info("결제 요청: {}", request);

        PaymentReadyDto response = paymentService.PaymentReady(request);


        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(response.getNext_redirect_pc_url()))
                .build();
    }

    @PostMapping("/approve/{pg_token}")
    public ResponseEntity<PaymentApproveDto> approve(@RequestParam("pg_token") String pgToken){

        PaymentApproveDto response = paymentService.payApprove(pgToken);

        return ResponseEntity.ok(response);

    }
}
