package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoBuider {

    private String nome;
    private BigDecimal valorInicial;
    private Usuario usuario;
    private LocalDate data;
    
    public LeilaoBuider comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LeilaoBuider comData(LocalDate data){
        this.data = data;
        return this;
    }

    public LeilaoBuider comValorInicial(String valor) {
        this.valorInicial = new BigDecimal(valor);
        return this;
    }

    public LeilaoBuider comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Leilao criar() {
        return  new Leilao(nome, valorInicial, data, usuario);
    }
}
