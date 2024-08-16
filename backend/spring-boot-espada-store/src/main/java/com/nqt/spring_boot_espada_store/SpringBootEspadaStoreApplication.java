package com.nqt.spring_boot_espada_store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootEspadaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEspadaStoreApplication.class, args);
    }
}
