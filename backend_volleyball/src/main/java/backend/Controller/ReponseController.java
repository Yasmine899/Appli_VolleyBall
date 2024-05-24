package backend.Controller;

import backend.Model.Reponse;
import backend.Service.ReponseService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReponseController {

    public ReponseService reponseService;

    @RequestMapping("/responseForQuestion/{questionId}")
    public List<Reponse> getReponsesByQuestion(@PathVariable int questionId){
        return ReponseService.getReponsesByQuestion(questionId);
    }
}
