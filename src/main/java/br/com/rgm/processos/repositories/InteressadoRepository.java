package br.com.rgm.processos.repositories;

import br.com.rgm.processos.entities.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.rgm.processos.entities.Interessado;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteressadoRepository extends JpaRepository<Interessado,Integer> {
	
	public Optional<Interessado> findBynuIdentificacao(String nuIdentificacao);

	@Query("SELECT i FROM Interessado i WHERE i.flAtivo = ?1")
	List<Interessado> findAllActive(Character ativo);

}
