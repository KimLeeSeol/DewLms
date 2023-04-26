package com.kdew.dewlms.admin.service;


import com.kdew.dewlms.admin.dto.CategoryDto;
import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.admin.entity.Category;
import com.kdew.dewlms.admin.mapper.CategoryMapper;
import com.kdew.dewlms.admin.mapper.MemberMapper;
import com.kdew.dewlms.admin.model.CategoryInput;
import com.kdew.dewlms.admin.model.MemberParam;
import com.kdew.dewlms.admin.repository.CategoryRepository;
import com.kdew.dewlms.components.MailComponents;
import com.kdew.dewlms.member.entity.Member;
import com.kdew.dewlms.member.exception.MemberNotEmailAuthException;
import com.kdew.dewlms.member.exception.MemberStopUserException;
import com.kdew.dewlms.member.model.MemberInput;
import com.kdew.dewlms.member.model.ResetPasswordInput;
import com.kdew.dewlms.member.repository.MemberRepository;
import com.kdew.dewlms.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC,"sortValue");
    }

    @Override
    public List<CategoryDto> list() {

        List<Category> categories = categoryRepository.findAll(getSortBySortValueDesc()); // 리스트 가져오기

        return CategoryDto.of(categories);
    }

    @Override
    public boolean add(String categoryName) {

        // 카테고리명이 중복인 경우 체크해야 함!

        Category category = Category.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .sortValue(0)
                .build();

        categoryRepository.save(category);

        return true;
    }

    @Override
    public boolean update(CategoryInput parameter) {

        Optional<Category> optionalCategory = categoryRepository.findById(parameter.getId());
        if (optionalCategory.isPresent()) {

            Category category = optionalCategory.get();

            category.setCategoryName(parameter.getCategoryName());
            category.setSortValue(parameter.getSortValue());
            category.setUsingYn(parameter.isUsingYn());
            categoryRepository.save(category);
        }
        return true;
    }


    @Override
    public boolean del(Long id) {

        categoryRepository.deleteById(id);

        return true;
    }

    @Override
    public List<CategoryDto> frontList(CategoryDto parameter) {

        return categoryMapper.select(parameter);
    }
}
