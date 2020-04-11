package com.diconiumwvv.storesservice;

import com.diconiumwvv.storesservice.stores.StoresController;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StoresserviceApplicationTests {

    @Autowired
    private StoresController storesController;

    @Test
    public void contextLoads() {
        assertThat(storesController).isNotNull();
    }
}
