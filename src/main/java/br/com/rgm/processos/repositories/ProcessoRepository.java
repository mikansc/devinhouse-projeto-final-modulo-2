package br.com.rgm.processos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rgm.processos.entities.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo,Integer> {

}
