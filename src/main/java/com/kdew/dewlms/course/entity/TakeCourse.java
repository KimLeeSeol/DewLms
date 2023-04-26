package com.kdew.dewlms.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class TakeCourse implements TakeCourseCode{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long courseId;
    String userId;
    long payPrice; // 결제 금액(할인된 금액으로)
    String status; // 상태(수강신청,결제완료, 수강취소)

    LocalDateTime regDt; // 신청일
}
