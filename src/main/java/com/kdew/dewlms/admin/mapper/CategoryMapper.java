package com.kdew.dewlms.admin.mapper;

import com.kdew.dewlms.admin.dto.CategoryDto;
import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryDto> select(CategoryDto parameter);
}
