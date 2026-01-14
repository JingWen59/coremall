package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wenjing
 * @email 353761126@qq.com
 * @date 2025-12-27 19:27:50
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
