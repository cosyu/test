package com.example.test;

import com.example.pattern.adapter.Adapter;
import com.example.pattern.adapter.BlackMan;
import com.example.pattern.adapter.BlackManAdapter;
import com.example.pattern.adapter.HongKongMan;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdapterTests {

    public AdapterTests(){

    }

    @Test
    public void test() {

        //BlackMan is to be translated to obtained required business for client side
        BlackMan blackMan = new BlackMan("black");
        blackMan.helloEnglish();
        blackMan.selfIntroEnglish();

        //Adapter is used to implement required business for client side
        Adapter adapter = new BlackManAdapter(blackMan.getName());
        adapter.hello();
        adapter.selfIntro();
        HongKongMan hongKongMan = new HongKongMan(adapter);
        hongKongMan.hello();
        hongKongMan.selfIntro();
    }

}
