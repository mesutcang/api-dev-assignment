package com.assingment;

import com.assingment.models.AuthResponse;
import com.assingment.models.CategorisedTransactions;
import com.assingment.models.Transactions;
import com.assingment.service.imp.CategoryServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class CategoryServiceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CategoryServiceImp categoryServiceImp;

    AuthResponse authResponse;
    @Before
    public void setUp() throws Exception {
        authResponse = new AuthResponse();
        authResponse.setAccessToken("token");
    }

    @Test
    public void getCategorisedTransactions_shouldReturnValidRecord4ValidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(AuthResponse.class)))
                .thenReturn(new ResponseEntity( authResponse, HttpStatus.OK));

        CategorisedTransactions categorisedTransactions = new CategorisedTransactions();
        Transactions transactions = new Transactions();
        transactions.setAccountId("acc");
        transactions.setAmount(1000);
        categorisedTransactions.setProject(Arrays.asList(transactions));
        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(CategorisedTransactions.class)))
                .thenReturn(new ResponseEntity( categorisedTransactions, HttpStatus.OK));

       CategorisedTransactions response = categoryServiceImp.getCategorisedTransactions("mesut");

        Assert.assertEquals(1,response.getProject().size());
        Assert.assertEquals("acc", response.getProject().get(0).getAccountId());
        Assert.assertEquals(1000, response.getProject().get(0).getAmount());
    }

    @Test
    public void getCategorisedTransactions_shouldThrowException4InvalidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(AuthResponse.class)))
                .thenReturn(new ResponseEntity( authResponse, HttpStatus.OK));

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(CategorisedTransactions.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        CategorisedTransactions response = categoryServiceImp.getCategorisedTransactions("mesut");

        Assert.assertEquals(null,response);
    }

    @Test
    public void getToken_shouldThrowException4InvalidData() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(AuthResponse.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));


        CategorisedTransactions response = categoryServiceImp.getCategorisedTransactions("mesut");

        Assert.assertEquals(null,response);
    }

    @Test
    public void getToken_shouldThrowException4InvalidInput() throws Exception {

        when(restTemplate.exchange(anyString(), any(),
                any(HttpEntity.class),
                eq(AuthResponse.class)))
                .thenThrow(new RestClientException("ex"));


        CategorisedTransactions response = categoryServiceImp.getCategorisedTransactions("mesut");

        Assert.assertEquals(null,response);
    }
}
