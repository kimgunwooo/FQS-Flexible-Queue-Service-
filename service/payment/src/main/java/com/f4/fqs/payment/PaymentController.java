package com.f4.fqs.payment;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    final String FINAL_REDIRECT_URL = "https://developers.kakao.com/success";

    //결제 요청
    @PostMapping("/ready")
    public ResponseEntity<PaymentReadyDto> readyToPay(@ModelAttribute PaymentReadyRequest request) {

        log.info("결제 요청: {}", request);

        PaymentReadyDto response = paymentService.PaymentReady(request);

        String tid = response.getTid();

        paymentService.saveTid(tid);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(response.getNext_redirect_pc_url()))
                .build();
    }

    @GetMapping("/approve")
    public ResponseEntity<PaymentApproveDto> approve(@RequestParam("pg_token") String pgToken){

        PaymentApproveDto response = paymentService.payApprove(pgToken);

//        paymentService.savePaymentInfo(response);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(FINAL_REDIRECT_URL))
                .build();

    }
}
