package com.example.test;

import com.example.pattern.observer.IObserver;
import com.example.pattern.observer.IObserved;
import com.example.pattern.observer.PodcastA;
import com.example.pattern.observer.Student;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObserverTests {

    public ObserverTests(){

    }

    @Test
    public void test(){

        IObserved podcast = new PodcastA();
        IObserver student = new Student(podcast);
        podcast.add(student);
        podcast.notifyObservers();
        podcast.setName("Chinese Podcast");
        podcast.notifyObservers();
    }
}
