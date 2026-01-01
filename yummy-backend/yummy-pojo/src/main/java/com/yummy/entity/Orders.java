package com.yummy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * orders entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders implements Serializable {

    /**
     * order status: 1-to-pay, 2-to-be-confirmed, 3-confirmed, 4-in-delivery, 5-complete,
     * 6-cancelled
     */
    public static final Integer PENDING_PAYMENT = 1;
    public static final Integer TO_BE_CONFIRMED = 2;
    public static final Integer CONFIRMED = 3;
    public static final Integer DELIVERY_IN_PROGRESS = 4;
    public static final Integer COMPLETED = 5;
    public static final Integer CANCELLED = 6;

    /**
     * payment status: 0-unpaid, 1-paid, 2-refund
     */
    public static final Integer UN_PAID = 0;
    public static final Integer PAID = 1;
    public static final Integer REFUND = 2;

    private static final long serialVersionUID = 1L;

    private Long id;

    //order number
    private String number;

    //订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款
    private Integer status;

    //user id
    private Long userId;

    //address id
    private Long addressBookId;

    //order time
    private LocalDateTime orderTime;

    //checkout time
    private LocalDateTime checkoutTime;

    //payment method: 1-wechat, 2-alipay
    private Integer payMethod;

    //支付状态 0未支付 1已支付 2退款
    private Integer payStatus;

    //received amount
    private BigDecimal amount;

    //remarks
    private String remark;

    //user name
    private String userName;

    //phone number
    private String phone;

    //address
    private String address;

    //recipient name
    private String consignee;

    //reason for order cancellation
    private String cancelReason;

    //reason for order rejection
    private String rejectionReason;

    //time of order cancellation
    private LocalDateTime cancelTime;

    //estimated delivery time
    private LocalDateTime estimatedDeliveryTime;

    //delivery status:  1-deliver right now,  0-deliver later
    private Integer deliveryStatus;

    //delivery time
    private LocalDateTime deliveryTime;

    //packaging fee
    private int packAmount;

    //number of tableware
    private int tablewareNumber;

    //餐具数量状态  1按餐量提供  0选择具体数量
    private Integer tablewareStatus;
}
