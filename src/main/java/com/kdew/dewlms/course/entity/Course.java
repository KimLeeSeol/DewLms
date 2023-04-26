package com.kdew.dewlms.course.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    long categoryId;

    String imagePath;
    String keyword;
    String subject;

    @Column(length = 1000)
    String summary;

    @Lob
    String contents; // 데이터가 많이 저장되기 때문
    long price;
    long salePrice;
    LocalDate saleEndDt;

    LocalDateTime regDt; // 등록 날짜
    LocalDateTime uptDt; // 수정 날짜

}
