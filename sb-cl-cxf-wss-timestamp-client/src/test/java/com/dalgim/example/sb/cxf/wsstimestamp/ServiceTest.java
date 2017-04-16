package com.dalgim.example.sb.cxf.wsstimestamp;

import com.dalgim.example.sb.cxf.wsstimestamp.endpoint.FruitService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Mateusz Dalgiewicz on 15.04.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private FruitService fruitService;

    @Test
    public void name() throws Exception {
        fruitService.getAllFruit();
        assert true;
    }
}
