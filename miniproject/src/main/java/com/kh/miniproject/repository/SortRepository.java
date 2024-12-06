package com.kh.miniproject.repository;

import com.kh.miniproject.vo.CategoryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SortRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<CategoryVo> sortInfo(String carName, String manufacturer, Integer minPrice, Integer maxPrice,
                                    String engineType, String classification, String sortBy, String sortType) {
        StringBuilder sql = new StringBuilder("SELECT * FROM VM_FILTER_CAR WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        log.error("repository : carName = {}, manufacturer = {}, min / max = {} / {}, engine = {}, classification = {}, sort = {}, sortType = {}",
                carName, manufacturer, minPrice, maxPrice, engineType, classification, sortBy, sortType);

        if (carName != null && !carName.isEmpty()) {
            sql.append("AND UPPER(CAR_NAME) LIKE UPPER(?) ");
            params.add("%" + carName + "%");  // LIKE 조건에 %를 추가
        }

        if (manufacturer != null && !manufacturer.isEmpty()) {
            List<String> manufacturerList = List.of(manufacturer.split(","));
            sql.append("AND ( 1=-1");
            for (String e : manufacturerList) {
                sql.append(" OR MANUFACTURER_NAME = ? ");
                params.add(e);
            }
            sql.append(")");
            
        }
        
        
        if (minPrice != null) {
            sql.append("AND CAR_PRICE >= ? ");
            params.add(minPrice);
        }
        
        if (maxPrice != null) {
            sql.append("AND CAR_PRICE <= ? ");
            params.add(maxPrice);
        }
        

        if (engineType != null && !engineType.isEmpty()) {
            List<String> engineList = List.of(engineType.split(","));
            sql.append("AND ( 1=-1");
            for (String e : engineList) {
                sql.append(" OR ENGINE_TYPE = ? ");
                params.add(e);
            }
            sql.append(")");
            
        }

        if (classification != null && !classification.isEmpty()) {
            List<String> classificationList = List.of(classification.split(","));
            sql.append("AND ( 1=-1");
            for (String e : classificationList) {
                sql.append(" OR CLASSIFICATION = ? ");
                params.add(e);
            }
            sql.append(")");
            
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            sql.append("ORDER BY ").append(sortBy).append(" ");
            if (sortType != null && (sortType.equalsIgnoreCase("ASC") || sortType.equalsIgnoreCase("DESC"))) {
                sql.append(sortType).append(" ");
            }
        }

        log.warn("실행된 쿼리문 : {}", sql);

        // 쿼리 실행 후 결과 반환
        return jdbcTemplate.query(sql.toString(), params.toArray(), new RowMapper<CategoryVo>() {
            @Override
            public CategoryVo mapRow(ResultSet rs, int rowNum) throws SQLException {
                CategoryVo CategoryVo = new CategoryVo();
                CategoryVo.setCarNo(rs.getInt("CAR_NO"));
                CategoryVo.setCarName(rs.getString("CAR_NAME"));
                CategoryVo.setManufacturer(rs.getString("MANUFACTURER_NAME"));
                CategoryVo.setPrice(rs.getInt("CAR_PRICE"));
                CategoryVo.setEngineType(rs.getString("ENGINE_TYPE"));
                CategoryVo.setClassification(rs.getString("CLASSIFICATION"));
                return CategoryVo;
            }
        });
    }
}

