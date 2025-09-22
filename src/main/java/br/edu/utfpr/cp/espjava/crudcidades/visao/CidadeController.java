package br.edu.utfpr.cp.espjava.crudcidades.visao;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class CidadeController {
    @GetMapping("/")
    public String listar(Model memoria){
        var cidades = Set.of(
            new Cidade("Curitiba", "PR"),
            new Cidade("Pomerode", "SC"),
            new Cidade("Fortaleza", "CE"),
            new Cidade("Cornélio Procópio", "PR")
        );
        memoria.addAttribute("listaCidades", cidades);
        return "crud";
    }
}
