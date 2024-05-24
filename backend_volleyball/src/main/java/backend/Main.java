package backend;

import backend.Service.QuestionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class Main {

    public static void main(String[] args) {

        QuestionService questionService = new QuestionService();
        System.out.println(QuestionService.getQuestionsByChapter(1));
    }

}
