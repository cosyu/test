package com.example.test;

import com.example.pattern.builder.MacBookDirector;
import com.example.pattern.builder.MacbookPro;
import com.example.pattern.builder.MacbookProBuilder;
import com.example.pattern.builder.MacbookProBuilder2018;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuilderTests {

    public BuilderTests(){

    }

    @Test
    public void test(){

        /*
        * client will access to director and no need to know how to build the products
        * */
        MacbookProBuilder macbookProBuilder = new MacbookProBuilder2018();
        MacBookDirector macBookDirector = new MacBookDirector(macbookProBuilder);

        MacbookPro macbookPro = macBookDirector.lowSpec();
        System.out.println(macbookPro.toString());

        macbookPro = macBookDirector.highSpec();
        System.out.println(macbookPro.toString());

        macbookPro = macbookProBuilder
                 .buildCPU(new MacbookPro.Processor("2.9GHz 6 核心第八代 Intel Core i9 處理器"))
                .buildMemory(new MacbookPro.Memory(32))
                .buildStore(new MacbookPro.Storage(4096))
                .buildKeyboard(new MacbookPro.Keyboard("英文"))
                .buildGraphics(new MacbookPro.Graphics("Radeon Pro 560X 配備 4GB GDDR5 記憶體"))
                .build();
        System.out.println(macbookPro.toString());
    }
}
