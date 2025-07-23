package com.yummy.mapper;

import com.yummy.entity.AddressBook;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressBookMapper {

    @Insert("insert into address_book" +
            "(user_id, consignee, phone, sex, province_code, province_name, city_code, city_name," +
            " district_code, district_name, detail, label, is_default)" +
            "values (#{userId}, #{consignee}, #{phone}, #{sex}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}," +
            "#{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void insert(AddressBook addressBook);
}
