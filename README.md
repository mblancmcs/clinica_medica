# Clinica médica

Projeto pessoal / API Rest de uma clinica médica, para praticar Spring Boot e Spring Data JPA com Hibernate.

## Stack
- Java 17: versão Long-term Support (LTS) até aproximadamente setembro de 2029.
- Spring Boot: framework para aplicações Java de bastante utilização no mercado.
- Spring Data JPA: com a implementação do ORM Hibernate.
- Spring Security: para o controle de acesso de usuários com Json Web Tokens (JWT).
- Lombok: para geração de códigos repetitivos como de construtores, getters e setters.
- Bean Validation: para fazer validações de forma automática.
- Flyway: para gerenciar as migrações / alterações do banco de dados.
- MySQL: SGBD (Sistema Gerenciador de Banco de dados) usual com aplicações Java.
- Maven: gerenciador de dependências.
- SpringDoc: utiliza do padrão OpenAPI para documentar de forma automática o projeto, e com a melhor visualização pelo Swagger.

## Requisitos
- Java (JRE ou JDK) - versão 17 (LTS).
- MySQL - a partir da versão 5.5.

## Proposta

### Marcação de consulta
O que é preciso informar:
- nome do paciente;
- informação do plano de saúde ou se é particular;

Antecipada:
- necessário informar o dia e horario da consulta;

OBS: o atendente liga para confirmar a consulta com o paciente, um dia antes do atendimento, e caso o mesmo cancele, não há ônus para ele.

Por ordem de chegada:
- Se tiver horário, é entregue uma senha para aguardar o atendimento.

OBS: só será chamado caso não haja conflito de horário com algum paciente agendado.

### Funcionamento

Atendente

- Os pacientes são chamados pela ordem das senhas ou horário previamente agendado.
- Caso o paciente não tenha ficha na clínica, é criado uma para ele com os seus dados básicos: CPF, nome, data de nascimento, endereço e telefones.
- Com a ficha do paciente em mãos, é registrado o motivo da consulta numa ficha de atendimento, anexada à ficha do paciente, e é colocada embaixo da pilha de fichas de atendimento (por ordem de chegada).
- É informado ao paciente aguardar para ser chamado e finaliza o atendimento.

Atendimento médico

É realizado pelo médico de acordo com a ordem da pilha de fichas de atendimento (ordenadas por ordem de chegada).

Antes de chamar o médico analisa:
- o histórico de atendimentos do paciente;
- o motivo da consulta atual;

Após o atendimento, o médico descreve detalhes do atendimento na ficha de atendimento, como:
- diagnóstico;
- receita de remédios;
- solicitação de retorno;
- complemento (outras informações).

Consulta finalizada e paciente liberado

## Execução

Para executar a versão de produção da aplicação, foi utilizado variáveis de ambiente para flexibilizar e deixar a aplicação mais segura, por esse motivo, para que não seja preciso configurá-las no servidor, é possível executar a aplicação através do seguinte comando (no diretório raíz da API):
```bash
java -Dspring.profiles.active=prod -DDATASOURCE_URL=jdbc:mysql://localhost:3306/clinica_medica_prod?createDatabaseIfNotExist=true -DDATASOURCE_USERNAME=root -DDATASOURCE_PASSWORD=root -jar ./target/clinica_medica-0.0.1-SNAPSHOT.jar
```
É necessário observar que:
- o "-Dspring.profiles.active=prod" é para executar o projeto em sua versão de produção;
- é necessário alterar o valor das variáveis de ambiente "DATASOURCE_USERNAME" e "DATASOURCE_PASSWORD" para respectivamente, o nome e senha configurados do banco de dados MySQL.
- na variável de ambiente "DATASOURCE_URL", conferir se a porta do banco de dados MySQL é a padrão 3306 ou se será preciso alterar no comando acima para a configurada no servidor.
- o TomCat utiliza a porta padrão 8080 para execução da API.
- ao fazer login, é necessário informar o "Bearer Token" / JWT recebido na resposta, no cabeçalho "Authorization" da requisição;
- ao executar a aplicação, é possível fazer os testes e entender melhor como a API funciona através do Swagger, pelo endereço: http://localhost:8080/swagger-ui/index.html

### Acesso
Usuário: admin

Senha: 123456

- Ao acessar com o usuário adminsitrador, é possível listar os usuários cadastrados ou cadastrar novos com as seguintes acessos: "ATENDENTE", "MEDICO" ou "ADMIN".
- O atendente terá acesso ao cadastro de consultas e pacientes.
- O médico terá acesso apenas aos atendimentos, que virá com informações das referentes consultas e do paciente.
- O administrador / ADMIN terá acesso total ao sistema.

### MER - Banco de dados
![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/1500ef38-7667-405a-8f96-e117e93d9006)

### Controller de autenticação

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/7d781356-f2fe-4caf-8b6d-ea634bdffa07)

### Controller de pacientes

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/cc884607-8032-46da-a2a0-bb3d1b29e2c1)

### Controller de consultas

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/5db65b0c-d1ad-449a-8a5d-a31a82ac0477)

### Controller de atendimentos

![image](https://github.com/mblancmcs/clinica_medica/assets/77879631/02aa56d6-97ec-49ec-9f03-07b5779c7060)

## Atualizações da aplicação

V.1 - Versão inicial.

V.1.01 - Melhorias e acréscimo da funcionalidade de buscar consultas por data.

## Contribuições

Pull requests são bem vindos. Para maiores alterações, por favor crie uma publicação para que seja discutido sobre.

Por favor faça o upload dos testes apropriados.

## Licença

[MIT](https://choosealicense.com/licenses/mit/)
