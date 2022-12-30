//package com.proceed.swhackathon.service;
//
//import com.proceed.swhackathon.dto.excel.ExcelDTO;
//import com.proceed.swhackathon.exception.Message;
//import com.proceed.swhackathon.exception.user.UserNotFoundException;
//import com.proceed.swhackathon.model.User;
//import com.proceed.swhackathon.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class ExcelDownloadService {
//    private final UserRepository userRepository;
//
//    public void excelDownload(String userId, HttpServletResponse response) throws IOException {
//        User user = userRepository.findById(userId).orElseThrow(() -> {
//            log.warn("{}", Message.USER_NOT_FOUND);
//            throw new UserNotFoundException();
//        });
//        user.isBoss();
//
//        final String filename = "attachment;filename="
//                + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
//                + "_" + user.getUsername()
//                + ".xlsx";
//
//        response.setHeader("Content-Disposition", filename);
//        response.setContentType("application/octet-stream");
//
//        List<String> header = Arrays.asList("주문번호", "가게", "일자", "품목명", "가격", "수량", "총액");
//
//
//        List<ExcelDTO> contents = new ArrayList<>();
//
//    }
//}
