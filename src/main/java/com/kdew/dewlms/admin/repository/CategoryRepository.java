package com.kdew.dewlms.admin.repository;

import com.kdew.dewlms.admin.entity.Category;
import com.kdew.dewlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
