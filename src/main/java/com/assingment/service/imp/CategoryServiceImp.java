package com.assingment.service.imp;

import com.assingment.models.AuthResponse;
import com.assingment.models.CategorisedTransactions;
import com.assingment.models.UpdateCategory4Transaction;
import com.assingment.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by mesutcang on 06/08/2019.
 */
@Component
public class CategoryServiceImp implements CategoryService {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImp.class);
    @Autowired
    RestTemplate restTemplate;

    @Value("${api.key:key}")
    private String apiKey;

    @Value("${api.partnerId:id}")
    private String partnerId;

    @Value("${api.sandboxUrl:sandbox.askfractal.com}")
    private String sandboxUrl;

    @Override
    public CategorisedTransactions getCategorisedTransactions(String categoryId) {
        String token  = getToken(restTemplate);
        log.debug("token generated for category=" + categoryId);
        if (!token.isEmpty()) {
            HttpHeaders headers = getAuthenticatedHttpHeaders(token);

            try{
                log.info("request is sending to rest resource");
                ResponseEntity<CategorisedTransactions> resp = restTemplate.exchange(sandboxUrl + "/categories/"
                                + categoryId + "/transactions", HttpMethod.GET,
                        new HttpEntity<>(null, headers), CategorisedTransactions.class);
                return resp.getBody();

            }catch (Exception e){
                log.info("selected category doesn't contain data, category=" + categoryId);

            }

        }
        return null;
    }

    @Override
    public boolean updateCategory4Transaction(UpdateCategory4Transaction updateRequest) {
        String token  = getToken(restTemplate);
        log.debug("token generated token =" + token);

        if (!token.isEmpty()) {
            HttpHeaders headers = getAuthenticatedHttpHeaders(token);

            try{
                log.info("request is sending to rest resource");
                ResponseEntity<ResponseEntity> exchange = restTemplate.exchange(sandboxUrl + "/categories/",
                        HttpMethod.PUT, new HttpEntity<>(updateRequest, headers), ResponseEntity.class);
                return true;

            }catch (Exception e){
                log.info("Requested category or transaction cannot be found for data=" + updateRequest );

            }

        }
        return false;
    }

    /**
     * gets token to access remote rest resource
     * @param restTemplate spring restTemplate for http requests
     * @return token for successfull requests empty string for error
     */
    private String getToken(RestTemplate restTemplate){
        ResponseEntity<AuthResponse> res = null;
        HttpHeaders headers = getHttpHeaders(apiKey, partnerId);
        try {
            res = restTemplate.exchange(sandboxUrl + "/token",
                    HttpMethod.POST, new HttpEntity<>(null, headers), AuthResponse.class);
            return res.getBody().getAccessToken();
        }catch (HttpClientErrorException ex){
            log.error("given api key or partner id is not valid");
        }catch (Exception ex){
            log.error("there is a problem during the connection the api");
        }
        return "";

    }

    /**
     * generate http headers for rest request
     * @param apiKey for the rest resource
     * @param partnerId for the partner
     * @return http header generated
     */
    private HttpHeaders getHttpHeaders(String apiKey, String partnerId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Api-Key", apiKey);
        headers.set("X-Partner-Id", partnerId);
        return headers;
    }

    /**
     * generate headers containing authentication information
     * @param token for the rest resource
     * @return http headers contain authentication info
     */
    private HttpHeaders getAuthenticatedHttpHeaders(String token) {
        HttpHeaders headers = getHttpHeaders(apiKey, "5111acc7-c9b3-4d2a-9418-16e97b74b1e6");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

}
