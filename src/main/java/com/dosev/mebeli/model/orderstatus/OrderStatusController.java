package com.dosev.mebeli.model.orderstatus;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dosev.mebeli.common.EntityNames.ORDER_STATUS;

@RestController
@RequestMapping(ORDER_STATUS)
@RequiredArgsConstructor
public class OrderStatusController {
}
