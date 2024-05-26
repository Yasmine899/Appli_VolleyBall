package backend.Controller;

import backend.Model.Question;
import backend.Service.QuestionService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class QuestionController {

    @RequestMapping("/questionForChapter/{chapter}")
    public List<Question> getQuestionsByChapter(@PathVariable int chapter){
        return QuestionService.getQuestionsByChapter(chapter);
    }

    @RequestMapping("/questions")
    public List<Question> getAllQuestions(){
        return QuestionService.getAllQuestions();
    }

    @RequestMapping("/question/{questionId}")
    public Question getQuestionById(@PathVariable int questionId){
        return QuestionService.getQuestionById(questionId);
    }
}
