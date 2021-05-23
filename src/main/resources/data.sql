insert into interessado (nm_interessado, nu_identificacao, dt_nascimento, fl_ativo)
values ('Jose Joao', '256.522.070-78', '2000-05-19T22:17:23.165Z', 'S'),
       ('Maria Joaquina', '217.787.750-47', '1998-05-19T22:17:23.165Z', 'S');


insert into assunto (descricao, dt_cadastro, fl_ativo)
values ('Autorização para Corte de Árvores - Área Pública', '2021-05-19T22:21:11.024Z', 'S'),
        ('Autorização para Utilização de Espaço Público', '2021-05-19T22:21:11.024Z', 'S'),
        ('Alteração de dados cadastrais da Inscrição Municipal de Pessoa Física/Pessoa Jurídica', '2021-05-19T22:21:11.024Z', 'S'),
        ('Certidao de Zoneamento (Uso e Ocupação de solo)', '2021-05-19T22:21:11.024Z', 'S'),
        ('Compensação de Auto de Infração/Notificação', '2021-05-19T22:21:11.024Z', 'S'),
        ('Consulta de Viabilidade para Instalação', '2021-05-19T22:21:11.024Z', 'S'),
        ('Consulta de Viabilidade para Instalação Automatizada', '2021-05-19T22:21:11.024Z', 'S'),
        ('Devolução/Restituição de Auto de Infração/Notificação', '2021-05-19T22:21:11.024Z', 'S'),
        ('Devolução/Restituição de Parcelamento', '2021-05-19T22:21:11.024Z', 'S'),
        ('Edital de Temporada de Verão 2021/2021', '2021-05-19T22:21:11.024Z', 'S'),
        ('Devolução/Restituição de Parcelamento', '2021-05-19T22:21:11.024Z', 'S'),
        ('Devolução/Restituição de Tributos Imobiliários', '2021-05-19T22:21:11.024Z', 'S'),
        ('Devolução/Restituição de Tributos Mobiliários', '2021-05-19T22:21:11.024Z', 'S'),
        ('Renovação da Autorização Ambiental Diversa', '2021-05-19T22:21:11.024Z', 'S'),
        ('Gratificação de Incentivo', '2021-05-19T22:21:11.024Z', 'S');

insert into processo (chave_processo, descricao, nu_ano, nu_processo, sg_orgao_setor, cd_assunto, cd_interessado)
values ('SOFT 1/2021', 'Processo', '2021', '1', 'soft', 1, 1),
       ('SOFT 2/2021', 'Processo', '2021', '2', 'soft', 2, 2);