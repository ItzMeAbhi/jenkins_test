package com.icici.controller;

import com.icici.model.ItemsModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/items")
public class TestController {

    private List<ItemsModel> dbList = new LinkedList<>();

    @PostMapping("saveItems")
    private  Map<String,Object> saveItems(@RequestBody ItemsModel itemsModel){
        dbList.add(itemsModel);
        Map<String,Object> respMap = new LinkedHashMap<>();
        respMap.put("MSG","Record Inserted Successfully");
        respMap.put("STATUS","Success");
        respMap.put("DATA",dbList);
        return respMap;
    }

    @GetMapping("listItems")
    private List<ItemsModel> listItems(){
        return dbList;
    }

}
