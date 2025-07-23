package com.yummy.service.impl;

import com.yummy.context.BaseContext;
import com.yummy.entity.AddressBook;
import com.yummy.mapper.AddressBookMapper;
import com.yummy.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * Add a new address
     * @param addressBook
     */
    @Override
    public void save(AddressBook addressBook) {
        // which user?
        Long userId = BaseContext.getCurrentId();
        addressBook.setUserId(userId);
        // is default? no
        addressBook.setIsDefault(0);
        // insert with mapper
        addressBookMapper.insert(addressBook);
    }

    @Override
    public List<AddressBook> list(AddressBook addressBook) {
        return addressBookMapper.list(addressBook);
    }

    @Override
    public AddressBook getById(Long id) {
        AddressBook addressBook = addressBookMapper.getById(id);
        return addressBook;
    }

    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    @Override
    public void setDefault(AddressBook addressBook) {
        // 1. set all addresses of the current user to not default
        // update address_book set is_default = ? where user_id = ?
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateIsDefaultByUserId(addressBook);

        // 2. set the current address to default
        // update address_book set is_default = ? where id = ?
        addressBook.setIsDefault(1);
        addressBookMapper.update(addressBook);
    }
}
