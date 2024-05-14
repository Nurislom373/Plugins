package org.khasanof;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ProxyLayerEnclosureAutoConfiguration.class})
class SpringProxyLayerEnclosureApplicationTests {

    @Autowired
    private ProxyLayerEnclosure proxyLayerEnclosure;

    @Test
    void firstTestProxyLayerEnclosureShouldSuccess() throws InterruptedException {
        Operation operation = new Operation();

        Operation proxyOperation = proxyLayerEnclosure.addProxyLayer(operation, "\\b(set\\w*)\\b",
                (operation1 -> System.out.println("operation is changed = " + operation1)));

        proxyOperation.setName("Hello");

        Thread.sleep(300);

        String name = proxyOperation.getName();
        System.out.println("name = " + name);

        Thread.sleep(300);

        proxyOperation.setName("Jeck");

        assertEquals(proxyOperation.getName(), "Jeck");
    }
}
