package br.edu.utfpr.cp.espjava.crudcidades.cidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public final class Cidade {
    @NotBlank(message = "{app.cidade.blank}")
    @Size(min = 5, max = 60, message = "{app.cidade.size}")
    private String nome;

    @NotBlank(message = "{app.estado.blank}")
    @Size(min = 2, max = 2, message = "{app.estado.size}")
    private String estado;

    public Cidade(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public Cidade clonar(CidadeEntidade cidadeEntidade) {
        return new Cidade(cidadeEntidade.getNome(), cidadeEntidade.getEstado());
    }

    public CidadeEntidade clonar(){
        var cidadeEntidade = new CidadeEntidade();

        cidadeEntidade.setNome(this.nome);
        cidadeEntidade.setEstado(this.estado);
        return cidadeEntidade;
    };
}
