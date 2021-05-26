package br.com.rgm.processos.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProcessoTest {

	@Test
	void testaOMetodoValidaCamposAutoGerados() {
		// given
		Processo processo = new Processo();
		processo.setNuAno("2020");
		processo.setSgOrgaoSetor("SOFT");
		// when(nuProcesso = null)
		processo.validaCamposAutoGerados();
		
		// then
		assertEquals(processo.getNuProcesso(),0);
		assertEquals(processo.getChaveProcesso(),"SOFT 0/2020");
		
		// when(nuProcesso = notNull)
		processo.setNuProcesso(2);
		processo.validaCamposAutoGerados();
		
		// then
		assertEquals(processo.getNuProcesso(),2);
		assertEquals(processo.getChaveProcesso(),"SOFT 2/2020");
	}

	@Test
	void testaOMetodoGeraVaLoresCamposAutoGerados() {
		// given
		Processo processo = new Processo(1,0,"SOFT","2020","null","Processo Teste",null,null);
		// when
		processo.geraVaLoresCamposAutoGerados();
		
		// then
		assertEquals(processo.getNuProcesso(),1);
		assertEquals(processo.getChaveProcesso(),"SOFT 1/2020");
	}

}
