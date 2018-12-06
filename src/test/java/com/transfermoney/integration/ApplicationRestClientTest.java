package com.transfermoney.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transfermoney.model.Account;
import com.transfermoney.model.ResponseResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@RestClientTest(ApplicationRestClient.class)
public class ApplicationRestClientTest {

    @Autowired
    private ApplicationRestClient applicationRestClient;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        Account account = new Account();
        account.setAccountNumber(1001L);
        account.setBalance(new BigDecimal(100.00));

        ResponseResult responseResult = new ResponseResult(account);

        String stringResult = objectMapper.writeValueAsString(responseResult);

        this.server.expect(MockRestRequestMatchers.requestTo("/getbalance/1001"))
                .andRespond(MockRestResponseCreators.withSuccess(stringResult, MediaType.APPLICATION_JSON));
    }

    @Test
    public void whenCallingCallGetRestApiThenClientMakeCorrectCall() throws Exception {
        ResponseResult responseResult = this.applicationRestClient.callGetRestApi("getbalance/1001");

        Assert.assertTrue(responseResult.isValid());
        Assert.assertNotNull(responseResult.getAccount());
    }

}
