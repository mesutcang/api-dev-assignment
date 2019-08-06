package com.assingment.controllers;

import com.assingment.models.AuthResponse;
import com.assingment.models.CategorisedTransactions;
import com.assingment.models.UpdateCategory4Transaction;
import com.assingment.service.imp.CategoryServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableAutoConfiguration
@Api(value="Categorised transactions and update category", description="Operations for getting categorised transaction data for given category and updates category info for transaction")
public class AssignmentController {

    @Autowired
    CategoryServiceImp categoryServiceImp;

    private static final Logger log = LoggerFactory.getLogger(AssignmentController.class);


    @GetMapping("/v1/{categoryId}")
    @ResponseBody
    @ApiOperation(value = "Get the transaction list for given category", response = CategorisedTransactions.class)
    ResponseEntity<?> getCategorisedTransaction4GivenCategory(@PathVariable String categoryId) {
        CategorisedTransactions categorisedTransactions = categoryServiceImp.getCategorisedTransactions(categoryId);
        if (categorisedTransactions == null ){
            return new ResponseEntity<>("Given category doesn't contain data", null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categorisedTransactions, null, HttpStatus.OK);
    }

    @PutMapping(value = "/v1/updatecategory")
    @ApiOperation(value = "Update the category info for given transaction")
    ResponseEntity<?> updateCategory4TransactionResource(@RequestBody UpdateCategory4Transaction updateRequest){
        if (StringUtils.isEmpty(updateRequest.getTransactionId()) || StringUtils.isEmpty(updateRequest.getCategoryId())){
            log.info("updatecategory is called with empty data");
            return new ResponseEntity<>("transaction or category cannot be null", null, HttpStatus.BAD_REQUEST);

        }
        boolean response = categoryServiceImp.updateCategory4Transaction(updateRequest);
        if (response == true){
            return new ResponseEntity<>(null, null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Requested category or transaction cannot be found", null, HttpStatus.NOT_FOUND);

    }

}