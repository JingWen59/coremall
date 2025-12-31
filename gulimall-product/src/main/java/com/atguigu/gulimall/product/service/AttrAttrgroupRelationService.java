package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author wenjing
 * @email 353761126@qq.com
 * @date 2025-12-17 15:20:10
 */
@Mapper
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

