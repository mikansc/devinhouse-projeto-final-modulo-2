package br.com.rgm.processos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rgm.processos.entities.Interessado;

import java.util.Optional;

@Repository
public interface InteressadoRepository extends JpaRepository<Interessado,Integer> {
	
	public Optional<Interessado> findBynuIdentificacao(String nuIdentificacao);

}
