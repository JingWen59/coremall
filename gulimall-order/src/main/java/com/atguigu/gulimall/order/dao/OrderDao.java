package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author wenjing
 * @email 353761126@qq.com
 * @date 2025-12-28 11:36:15
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
