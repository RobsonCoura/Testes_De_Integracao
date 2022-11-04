package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	//Atributos da classe
	private UsuarioDao dao;
	private EntityManager em;

	//Criacao do EntityManager e do Usuariodao, sendo feita no método beforeEach antes dos teste.
	@BeforeEach
	public void beforeEach(){
		this.em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();
	}

	/*Após a execucao de cada teste ele vai chamar o método afterEach. O teste executou com tudo,
	 que tinha que executar faz o rollback desfaz todos os insert tudo que foi feito no banco de dado,
	 eai o proximo teste vai iniciar com o banco zerado.*/
	@AfterEach
	public void	afterEach(){
		em.getTransaction().rollback();
	}
	@Test
	void deveriaEncontrarUsuarioCadastrado() {
		Usuario usuario = criarUsuario();
		Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(encontrado);
	}

	@Test
	void naoDeveriaEncontrarUsuarioNaoCadastrado() {
		criarUsuario();
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
	}

	//Método para apagar um usuario do banco de dados
	@Test
	void deveriaRemoverUmUsuario(){
		Usuario usuario = criarUsuario();
		dao.deletar(usuario);
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
	}

	//Método para criar objeto usuario, e retorna o objeto já salvo do banco de dado.
		private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		em.persist(usuario);
		return usuario;
	}
}
