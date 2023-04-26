package com.kdew.dewlms.admin.mapper;

import com.kdew.dewlms.admin.dto.MemberDto;
import com.kdew.dewlms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    long selectListCount(MemberParam parameter); // 멤버 수 구하기
    List<MemberDto> selectList(MemberParam parameter);
}
