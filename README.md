# Clínica médica

Projeto inicial / API Rest de uma clínica médica desenvolvido em Java, Spring Boot, Hibernate como implementação do Spring Data JPA e Spring Security para controle de acesso de usuários.

## Stack
- Java 17: versão Long-term Support (LTS) até aproximadamente setembro de 2029.
- Spring Boot: framework para aplicações Java de bastante utilização no mercado.
- Spring Data JPA: com a implementação do ORM Hibernate.
- Spring Security: para o controle de acesso de usuários com Json Web Tokens (JWT).
- Lombok: para geração de códigos repetitivos como de construtores, getters e setters.
- Bean Validation: para realizar validações de forma automática.
- Flyway: para gerenciar as migrações / alterações do banco de dados.
- MySQL: SGBD (Sistema Gerenciador de Banco de dados) usual com aplicações Java.
- Maven: gerenciador de dependências.
- SpringDoc: utiliza do padrão OpenAPI para documentar de forma automática o projeto, e com a melhor visualização pelo Swagger.

## Requisitos
- Java (JRE ou JDK) - versão 17 (LTS).
- MySQL - a partir da versão 5.5.

## Execução

Para executar a versão de produção da aplicação, foi utilizado variáveis de ambiente para flexibilizar e deixar a aplicação mais segura, por esse motivo, para que não seja preciso configurá-las no servidor, é possível executar a aplicação através do seguinte comando (no diretório raíz da API):
```bash
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost:3306/clinica_medica_prod?createDatabaseIfNotExist=true -DDATASOURCE_USERNAME=root -DDATASOURCE_PASSWORD=root -jar clinica_medica-0.0.1-SNAPSHOT.jar
```
É necessário observar que:
- O "-Dspring.profiles.active=prod" é para executar o projeto em sua versão de produção;
- É necessário alterar o valor das variáveis de ambiente "DATASOURCE_USERNAME" e "DATASOURCE_PASSWORD" para respectivamente, o nome e senha configurados do banco de dados MySQL, a não ser que também tenham o valor "root";
- Na variável de ambiente "DATASOURCE_URL", conferir se a porta do banco de dados MySQL é a padrão 3306 ou se será preciso alterar no comando acima para a configurada no servidor;
- O Tomcat utiliza a porta padrão 8080 para execução da API.
- Ao fazer login, é necessário informar o "Bearer Token" / JWT recebido na resposta, no cabeçalho "Authorization" das próximas requisições;
- Ao executar a aplicação, é possível fazer os testes e entender melhor como a API funciona através do Swagger, pelo endereço: http://localhost:8080/swagger-ui/index.html

## Funcionamento

### Marcação de consulta

#### O que é preciso informar:
- nome do paciente;
- informação do plano de saúde ou se é particular;

#### Se for antecipada:
- necessário informar o dia e horário da consulta;
OBS: o atendente liga para confirmar a consulta com o paciente, um dia antes do atendimento. Caso o mesmo cancele, não há ônus para ele.

#### Por ordem de chegada:
- Se tiver horário disponível, é entregue uma senha para aguardar o atendimento.
OBS: só será chamado caso não haja conflito de horário com algum paciente agendado.

### Atendente

Os pacientes são chamados pela ordem das senhas ou horário previamente agendado.

Caso o paciente não tenha ficha na clínica, é criado uma para ele com os seus dados básicos: CPF, nome, data de nascimento, endereço e telefones.

Com a ficha do paciente em mãos, é registrado o motivo da consulta numa ficha de atendimento, anexada à ficha do paciente, e colocada embaixo da pilha de fichas de atendimento (para os atendimentos serem realizados por ordem de chegada).

É informado ao paciente aguardar para ser chamado e finaliza o atendimento.

### Atendimento médico

Realizado de acordo com a ordem da pilha de fichas de atendimento (ordenadas por ordem de chegada).

#### Antes de chamar o médico analisa:
- o histórico de atendimentos do paciente;
- o motivo da consulta atual;

#### Após o atendimento, o médico descreve detalhes do atendimento na ficha de atendimento, como:
- diagnóstico;
- receita de remédios;
- solicitação de retorno;
- complemento (outras informações).

### Modelo Entidade Relacionamento (MER) - Banco de dados
![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/4cf78187-c669-4188-b9e2-182bf9635aa0)

### Autenticação e autorização
Usuário: admin

Senha: 123456

- Ao acessar com o usuário administrador, é possível listar os usuários cadastrados ou cadastrar novos com os seguintes acessos: "ATENDENTE", "MEDICO" ou "ADMIN".
- O atendente terá acesso a todos métodos dos controllers de consultas e pacientes.
- O médico terá acesso apenas aos atendimentos, que virão com informações da referente consulta e do paciente.
- O administrador / ADMIN terá acesso total ao sistema.
- Ao fazer login, é necessário informar o "Bearer Token" / JWT recebido na resposta, no cabeçalho "Authorization" das próximas requisições.

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/160cc523-9a16-4137-bda6-93d6dd9468b4)

### Endpoints de paciente

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/54ef8964-0179-4c11-9c01-5e30ff0c61aa)

### Endpoints de consulta

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/178aa140-faf6-44a5-979d-71b3678b192a)

### Endpoints de atendimento

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/521f7725-9929-49f1-b276-f1da187d7f7e)

## Atualizações da aplicação

V.2 - Ajustes necessários de acordo com o front-end.

V.1.01 - Melhoria: implementação para listar consultas por data.

V.1 - Versão inicial.

## Contribuições

Pull requests são bem vindos. Para maiores alterações, por favor crie uma publicação para que seja discutido sobre.

Por favor faça o upload dos testes apropriados.

## Licença

[MIT](https://choosealicense.com/licenses/mit/)
