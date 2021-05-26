package br.com.rgm.processos.repositories;

import br.com.rgm.processos.entities.Assunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssuntoRepository extends JpaRepository<Assunto, Integer> {

    @Query("SELECT a FROM Assunto a WHERE a.flAtivo = ?1")
    List<Assunto> findAllActive(Character ativo);

}
