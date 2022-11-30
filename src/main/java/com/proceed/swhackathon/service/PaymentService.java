package com.proceed.swhackathon.service;

import com.proceed.swhackathon.dto.ResponseDTO;
import com.proceed.swhackathon.dto.payment.PaymentReqDTO;
import com.proceed.swhackathon.exception.Message;
import com.proceed.swhackathon.exception.coupon.CouponExpiredException;
import com.proceed.swhackathon.exception.coupon.CouponUseNotFoundException;
import com.proceed.swhackathon.exception.payment.PaymentAmountNotMachException;
import com.proceed.swhackathon.exception.payment.PaymentNotFoundException;
import com.proceed.swhackathon.exception.user.UserNotFoundException;
import com.proceed.swhackathon.exception.user.UserUnAuthorizedException;
import com.proceed.swhackathon.exception.userOrderDetail.UserOrderDetailNotFoundException;
import com.proceed.swhackathon.model.CouponUse;
import com.proceed.swhackathon.model.Payment;
import com.proceed.swhackathon.model.User;
import com.proceed.swhackathon.model.UserOrderDetail;
import com.proceed.swhackathon.repository.CouponUseRepository;
import com.proceed.swhackathon.repository.PaymentRepository;
import com.proceed.swhackathon.repository.UserOrderDetailRepository;
import com.proceed.swhackathon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final CouponUseRepository couponUseRepository;
    private final UserOrderDetailRepository userOrderDetailRepository;

    @Value("${payments.toss.successUrl}")
    private String successUrl;

    @Value("${payments.toss.failUrl}")
    private String failUrl;

    @Transactional
    public ResponseDTO<?> paymentValid(final String userId,
                                       final PaymentReqDTO reqDTO){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("{}", Message.USER_NOT_FOUND);
            throw new UserNotFoundException();
        });

        int resultAmount = reqDTO.getAmount();

        if(reqDTO.getCouponUse_id() != 0) {
            CouponUse couponUse = couponUseRepository.findById(reqDTO.getCouponUse_id()).orElseThrow(() -> {
                log.warn("{}", Message.COUPONUSE_NOT_FOUND);
                throw new CouponUseNotFoundException();
            });

            if (couponUse.getCoupon().couponExpireCheck()) {
                log.warn("{}", Message.COUPON_EXPIRED);
                throw new CouponExpiredException();
            }

            couponUse.setCouponUse(true); // 쿠폰 사용표시
            resultAmount -= couponUse.getCoupon().getCouponPrice();
            if(resultAmount < 0) resultAmount = 0;
            log.info("쿠폰 사용금액 : {}", couponUse.getCoupon().getCouponPrice());
        }

        Payment payment = Payment.builder()
                .user_id(user.getId())
                .order_id(reqDTO.getOrder_id())
                .couponUse_id(reqDTO.getCouponUse_id())
                .orderName(reqDTO.getOrder_name())
                .totalAmount(resultAmount)
                .status("WAITING")
                .build();
        Payment savePayment = paymentRepository.save(payment);

        Map<String, Object> m = new HashMap<>();
        m.put("amount", savePayment.getTotalAmount());
        m.put("orderId", savePayment.getPayment_id());
        m.put("orderName", savePayment.getOrderName());
        m.put("customerName", user.getUsername());
        m.put("successUrl", successUrl);
        m.put("failUrl", failUrl);

        return new ResponseDTO<>(HttpStatus.OK.value(), m);
    }

    @Transactional
    public ResponseDTO<?> paymentConfirm(String clientKey,
                                         final String paymentKey,
                                         final String orderId,
                                         final Integer amount){

        Payment payment = paymentRepository.findById(orderId).orElseThrow(() -> {
            log.warn("PaymentNotFoundException occur..");
            throw new PaymentNotFoundException();
        });

        // 결제금액이 맞지않으면 오류발생시킴
        if(payment.getTotalAmount() != amount){
            log.warn("PaymentAmountNotMachException occur..");
            throw new PaymentAmountNotMachException();
        }

        try {
            URL address = new URL("https://api.tosspayments.com/v1/payments/confirm");
            byte[] bytes = (clientKey + ":").getBytes(StandardCharsets.UTF_8);
            clientKey = new String(Base64.getEncoder().encode(bytes));

            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + clientKey);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setDoOutput(true);

            // tid 확인
            String parameter = "{\"paymentKey\":\"" + paymentKey +
                    "\",\"orderId\":\"" + orderId +
                    "\",\"amount\":\"" + amount + "\"}";
            log.info("parameter info {}", parameter);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();

            int result = connection.getResponseCode();

            InputStream inputStream;
            if(result == 200){
                inputStream = connection.getInputStream();
                log.info("result 200!");
            }else{
                inputStream = connection.getErrorStream();
                log.warn("result {}...", result);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                JSONObject obj = new JSONObject(bufferedReader.readLine());

                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), obj.toMap());
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            log.info("Toss Payment Approve! ");

            JSONObject obj = new JSONObject(bufferedReader.readLine());

            Map<String, Object> obj_map = obj.toMap();
            obj_map.put("order_id", payment.getOrder_id());
            obj_map.put("couponUse_id", payment.getCouponUse_id());

            if(obj_map.containsKey("version"))  payment.setVersion((String)obj_map.get("version"));
            if(obj_map.containsKey("paymentKey"))  payment.setPaymentKey((String) obj_map.get("paymentKey"));
            if(obj_map.containsKey("type"))  payment.setType((String) obj_map.get("type"));
            if(obj_map.containsKey("orderId"))  payment.setOrderId((String)obj_map.get("orderId"));
            if(obj_map.containsKey("orderName"))  payment.setOrderName((String)obj_map.get("orderName"));
            if(obj_map.containsKey("mId"))  payment.setMId((String)obj_map.get("mId"));
            if(obj_map.containsKey("currency"))  payment.setCurrency((String)obj_map.get("currency"));
            if(obj_map.containsKey("method"))  payment.setMethod((String)obj_map.get("method"));
            if(obj_map.containsKey("totalAmount"))  payment.setTotalAmount((Integer)obj_map.get("totalAmount"));
            if(obj_map.containsKey("balanceAmount"))  payment.setBalanceAmount((Integer)obj_map.get("balanceAmount"));
            if(obj_map.containsKey("status"))  payment.setStatus((String)obj_map.get("status"));
            if(obj_map.containsKey("transactionKey"))  payment.setTransactionKey((String)obj_map.get("transactionKey"));
            if(obj_map.containsKey("lastTransactionKey"))  payment.setLastTransactionKey((String)obj_map.get("lastTransactionKey"));
            if(obj_map.containsKey("suppliedAmount"))  payment.setSuppliedAmount((Integer)obj_map.get("suppliedAmount"));
            if(obj_map.containsKey("vat"))  payment.setVat((Integer)obj_map.get("vat"));

            return new ResponseDTO<>(HttpStatus.OK.value(), payment);
        } catch (MalformedURLException e) {
            log.warn("Toss Payment Approve MalformedURLException Fail..");
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "error");
        } catch (IOException e) {
            log.warn("Toss Payment Approve IOException Fail..");
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "error");
        }
    }

    @Transactional
    public ResponseDTO<?> paymentCancel(String clientKey, String userId, Long userOrderDetailId){
        Payment payment = paymentRepository.findByUserOrderDetail_id(userOrderDetailId).orElseThrow(() -> {
            log.warn("PaymentNotFoundException occur..");
            throw new PaymentNotFoundException();
        });

        if(!payment.getUser_id().equals(userId)) {
            log.warn("{}", Message.USER_UNAUTHORIZED);
            throw new UserUnAuthorizedException();
        }

        try {
            final String url = "https://api.tosspayments.com/v1/payments/" + payment.getPaymentKey() + "/cancel";
            byte[] bytes = (clientKey + ":").getBytes(StandardCharsets.UTF_8);
            clientKey = new String(Base64.getEncoder().encode(bytes));

            URL address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + clientKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // tid 확인
            String parameter = "{\"cancelReason\":" + " \"고객이 취소를 원함\","
                    + "\"cancelAmount\":" + payment.getTotalAmount()
                    + "\", \"refundReceiveAccount\":{\"bank\": \"" + payment.getPaymentKey()
                    + "\", \"accountNumber\": \"" + payment.getPaymentKey()
                    + "\", \"holderName\": \"" + payment.getPaymentKey() + "\"}}";
            log.info("parameter info {}", parameter);
            OutputStream outputStream = connection.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(parameter);
            dataOutputStream.close();

            int result = connection.getResponseCode();

            InputStream inputStream;
            if(result == 200){
                inputStream = connection.getInputStream();
                log.info("result 200!");
            }else{
                inputStream = connection.getErrorStream();
                log.warn("result {}...", result);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                JSONObject obj = new JSONObject(bufferedReader.readLine());

                return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), obj.toMap());
            }
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            log.info("Toss Payment Approve! ");

            JSONObject obj = new JSONObject(bufferedReader.readLine());

            Map<String, Object> obj_map = obj.toMap();
            obj_map.put("order_id", payment.getOrder_id());

            if(obj_map.containsKey("version"))  payment.setVersion((String)obj_map.get("version"));
            if(obj_map.containsKey("paymentKey"))  payment.setPaymentKey((String) obj_map.get("paymentKey"));
            if(obj_map.containsKey("type"))  payment.setType((String) obj_map.get("type"));
            if(obj_map.containsKey("orderId"))  payment.setOrderId((String)obj_map.get("orderId"));
            if(obj_map.containsKey("orderName"))  payment.setOrderName((String)obj_map.get("orderName"));
            if(obj_map.containsKey("mId"))  payment.setMId((String)obj_map.get("mId"));
            if(obj_map.containsKey("currency"))  payment.setCurrency((String)obj_map.get("currency"));
            if(obj_map.containsKey("method"))  payment.setMethod((String)obj_map.get("method"));
            if(obj_map.containsKey("totalAmount"))  payment.setTotalAmount((Integer)obj_map.get("totalAmount"));
            if(obj_map.containsKey("balanceAmount"))  payment.setBalanceAmount((Integer)obj_map.get("balanceAmount"));
            if(obj_map.containsKey("status"))  payment.setStatus((String)obj_map.get("status"));
            if(obj_map.containsKey("transactionKey"))  payment.setTransactionKey((String)obj_map.get("transactionKey"));
            if(obj_map.containsKey("lastTransactionKey"))  payment.setLastTransactionKey((String)obj_map.get("lastTransactionKey"));
            if(obj_map.containsKey("suppliedAmount"))  payment.setSuppliedAmount((Integer)obj_map.get("suppliedAmount"));
            if(obj_map.containsKey("vat"))  payment.setVat((Integer)obj_map.get("vat"));

            UserOrderDetail uod = userOrderDetailRepository.findByIdWithOrder(userOrderDetailId).orElseThrow(() -> {
                log.warn("{}", Message.USER_ORDER_DETAIL_NOT_FOUND);
                throw new UserOrderDetailNotFoundException();
            });

            if(uod.equals("CANCEL")) { // 이미 취소상태일 경우
                log.warn("{}", Message.USER_UNAUTHORIZED );
                throw new UserUnAuthorizedException();
            }

            uod.cancel(); // 취소하고
            uod.setUserOrderDetailStatus("CANCEL"); // 상태변경

            return new ResponseDTO<>(HttpStatus.OK.value(), payment);
        } catch (MalformedURLException e) {
            log.warn("Toss Payment Cancel MalformedURLException Fail..");
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "error");
        } catch (IOException e) {
            log.warn("Toss Payment Cancel IOException Fail..");
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), "error");
        }
    }
}
