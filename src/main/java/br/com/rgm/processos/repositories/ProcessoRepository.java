package br.com.rgm.processos.repositories;

import br.com.rgm.processos.entities.Assunto;
import br.com.rgm.processos.entities.Interessado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rgm.processos.entities.Processo;

import java.util.List;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo,Integer> {

    public Processo findByChaveProcesso(String chaveProcesso);
    public List<Processo> findByInteressado(Interessado interessado);
    public List<Processo> findByAssunto(Assunto assunto);

}
