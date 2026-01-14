package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author wenjing
 * @email 353761126@qq.com
 * @date 2025-12-17 15:20:10
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
