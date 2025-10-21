package br.edu.utfpr.cp.espjava.crudcidades.cidade;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CidadeController {
    private final CidadeRepository cidadeRepository;

    public CidadeController(CidadeRepository cidadeRepository){
        this.cidadeRepository = cidadeRepository;
    }

    @GetMapping("/")
    public String listar(Model memoria){
        memoria.addAttribute("listaCidades", this.converteCidade(cidadeRepository.findAll()));

        return "crud";
    }

    private List<Cidade> converteCidade(List<CidadeEntidade> cidades){
        return cidades.stream()
                        .map(cidade ->
                                new Cidade(cidade.getNome(),
                                cidade.getEstado()))
                        .collect(Collectors.toList());
    }

    @PostMapping("/criar")
    public String criar(@Valid Cidade cidade, BindingResult result, Model memoria){
        if (result.hasErrors()){
            result.getFieldErrors().forEach(
                    error -> memoria.addAttribute(error.getField(), error.getDefaultMessage())
            );
            memoria.addAttribute("nomeInformado", cidade.getNome());
            memoria.addAttribute("estadoInformado", cidade.getEstado());
            memoria.addAttribute("listaCidades", this.converteCidade(cidadeRepository.findAll()));
            return "crud";
        } else {
            cidadeRepository.save(cidade.clonar());
        }
        return "redirect:/";
    }

    @GetMapping("/excluir")
    public String excluir(@RequestParam String nome, @RequestParam String estado) {
        var cidadeEstado = cidadeRepository.findByNomeAndEstado(nome, estado);
        cidadeEstado.ifPresent(cidadeRepository::delete);
        return "redirect:/";
    }

    @GetMapping("/preparaAlterar")
    public String preparaAlterar(@RequestParam String nome, @RequestParam String estado, Model memoria) {
        var cidadeAtual = cidadeRepository.findByNomeAndEstado(nome, estado);
        cidadeAtual.ifPresent(cidadeEncontrada -> {
            memoria.addAttribute("cidadeAtual", cidadeEncontrada);
            memoria.addAttribute("listaCidades", this.converteCidade(cidadeRepository.findAll()));
        });
        return "crud";
    };

    @PostMapping("/alterar")
    public String alterar(String nomeAtual, String estadoAtual, Cidade novaCidade, BindingResult result, Model memoria) {
        var cidadeAtual = cidadeRepository.findByNomeAndEstado(nomeAtual, estadoAtual);
        if (cidadeAtual.isPresent()) {
            var cidadeEncontrada = cidadeAtual.get();
            cidadeEncontrada.setNome(novaCidade.getNome());
            cidadeEncontrada.setEstado(novaCidade.getEstado());
            cidadeRepository.saveAndFlush(cidadeEncontrada);
        };
        return "redirect:/";
    };
}
