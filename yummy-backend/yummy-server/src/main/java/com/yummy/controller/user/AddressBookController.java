package com.yummy.controller.user;

import com.yummy.context.BaseContext;
import com.yummy.entity.AddressBook;
import com.yummy.result.Result;
import com.yummy.service.AddressBookService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * Add a new address
     * @param addressBook
     * @return
     */
    @PostMapping
    @ApiOperation("add new address")
    public Result save(@RequestBody AddressBook addressBook) {
        addressBookService.save(addressBook);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("List addresses of the current user")
    public Result<List<AddressBook>> list() {
        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        // why does here use addressBook as para for list? instead of userId?
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);
    }


}
