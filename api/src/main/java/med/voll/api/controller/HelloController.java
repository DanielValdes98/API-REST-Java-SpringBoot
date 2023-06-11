package med.voll.api.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// Cual ruta HTTP sigue:
@RequestMapping("/hello")
public class HelloController {

    // Mapeo la función el método GET en la ruta de arriba /hello:
    @GetMapping
    public String helloWord(){
        return "Hello World from Colombia";
    }
}
