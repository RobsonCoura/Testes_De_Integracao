package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

class LeilaoDaoTest {

    //Atributos da classe
    private LeilaoDao dao;
    private EntityManager em;

    //Criacao do EntityManager e do Leilaodao, sendo feita no método beforeEach antes dos teste.
    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    /*Após a execucao de cada teste ele vai chamar o método afterEach. O teste executou com tudo,
     que tinha que executar faz o rollback desfaz todos os insert tudo que foi feito no banco de dado,
     eai o proximo teste vai iniciar com o banco zerado.*/
    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    //Teste para verificar se está salvando um leilao no banco de dados
    @Test
    void deveriaCadastrarUmLeilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Notebook Dell G15", new BigDecimal("2500"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertNotNull(salvo);
    }

    @Test
    void deveriaAtualizarUmLeilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Notebook Dell G15", new BigDecimal("2500"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        leilao.setNome("Notebook Acer Nitro 5");
        leilao.setValorInicial(new BigDecimal("2000"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());
        Assert.assertEquals("Notebook Acer Nitro 5", salvo.getNome());
        Assert.assertEquals(new BigDecimal("2000"), salvo.getValorInicial());
    }

    //Método para criar objeto usuario, e retorna o objeto já salvo do banco de dado.
    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
