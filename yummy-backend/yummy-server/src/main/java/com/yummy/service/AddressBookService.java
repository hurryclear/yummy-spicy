package com.yummy.service;

import com.yummy.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    void save(AddressBook addressBook);

    List<AddressBook> list(AddressBook addressBook);
}
