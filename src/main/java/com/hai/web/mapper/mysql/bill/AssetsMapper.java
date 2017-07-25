package com.hai.web.mapper.mysql.bill;

import com.hai.web.model.AssetsEntity;
import org.apache.ibatis.annotations.Param;

/**
 * Created by cloud on 2017/6/6.
 */
public interface AssetsMapper {
    public AssetsEntity findAssetsByUid(@Param("uid")long uid);
}
