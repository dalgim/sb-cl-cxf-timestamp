package com.dalgim.example.sb.cxf.endpoint;

import com.dalgim.example.sb.cxf.model.Fruit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dalgim on 08.04.2017.
 */
@Service
public class FruitServiceImpl implements FruitService {

    private static List<Fruit> fruitList = new ArrayList<>(2);

    static {
        Fruit apple = Fruit.builder()
                .name("Apple")
                .carbo(20.5)
                .protein(0.5)
                .fat(0.5)
                .id(1L)
                .build();
        Fruit banana = Fruit.builder()
                .name("Banana")
                .carbo(26.5)
                .protein(0.5)
                .fat(0.5)
                .id(1L)
                .build();

        fruitList.add(apple);
        fruitList.add(banana);
    }

    @Override
    public Collection<Fruit> getAllFruit() {
        return fruitList;
    }
}
