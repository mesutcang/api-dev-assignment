package com.assingment;

import com.assingment.models.UpdateCategory4Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AssignmentControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void should_getCategorisedTransaction4GivenCategory_ReturnSuccesfulldataForValidInput() throws Exception{
        mockMvc.perform(get("/v1/b6as2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.results", hasSize(1)))
                .andExpect(jsonPath("$.results.[0].accountId" , is("fakeAcct1")))
                .andExpect(jsonPath("$.results.[0].bankId" , is(7)))
                .andExpect(jsonPath("$.results.[0].amount" , is(2000)))
                .andExpect(jsonPath("$.results.[0].category" , is("R&D Costs")))
                .andExpect(jsonPath("$.results.[0].companyId" , is(2)));
    }

    @Test
    public void should_getCategorisedTransaction4GivenCategory_ReturnSuccesfulldataForInvalidInput() throws Exception{
        mockMvc.perform(get("/v1/somerandomtext"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_updateCategory4TransactionResource_ReturnNodataForValidInput() throws Exception{
        mockMvc.perform(put("/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("{\"transactionId\": \"fakeTrx13\",\"categoryId\": \"b6as2\"}"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void should_updateCategory4TransactionResource_ReturnNotfoundForInalidInput() throws Exception{
        mockMvc.perform(put("/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"transactionId\": \"\",\"categoryId\": \"b6as2\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_updateCategory4TransactionResource_ReturnfoundForInalidInput() throws Exception{
        mockMvc.perform(put("/v1/updatecategory").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"transactionId\": \"somerandomtext\",\"categoryId\": \"random\"}"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}
