package com.example.demo.controller.controllers;

import com.example.demo.model.roombooking.RoomBooking;
import com.example.demo.service.RoomBookingService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payment")
public class PaymentPage {
    @Autowired
    private RoomBookingService roomBookingService;

    @GetMapping("/payment-result")
    public String bookingSuccessfully(@RequestParam("vnp_Amount") String amount,
                                      @RequestParam("vnp_BankCode") String bankCode,
                                      @RequestParam("vnp_CardType") String bankTranNo,
                                      @RequestParam("vnp_OrderInfo") String orderInfo,
                                      @RequestParam("vnp_PayDate") String payDate,
                                      @RequestParam("vnp_ResponseCode") String responseCode,
                                      @RequestParam("vnp_TmnCode") String tmnCode,
                                      @RequestParam("vnp_TransactionNo") String transactionNo,
                                      @RequestParam("vnp_TransactionStatus") String transactionStatus,
                                      @RequestParam("vnp_TxnRef") String txnRef,
                                      @RequestParam("vnp_SecureHash") String secureHash) {

        if (responseCode.equals("00")){
            roomBookingService.changeStatusToConfirmed(txnRef);
            return "web/index"; // sau trả về trang booking thành công
        } else {
            return "error/error-page";
        }


    }

}
