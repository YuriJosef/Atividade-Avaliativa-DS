# Atividade Avaliativa de Desenvolvimento de Sistemas

## Objetivo

Praticar testes automatizados de interface com Java, Selenium WebDriver, Maven e JUnit 5, validando mensagens, carregamentos dinâmicos e fluxos de compra em aplicações web públicas.

## Tecnologias

- Java 17 ou superior
- Maven 3.8 ou superior
- Selenium WebDriver 4.35.0, com Selenium Manager para localizar o driver
- JUnit Jupiter 5.12.0
- Google Chrome ou Chromium

## Questões

- [Questão 01](questao-01/): valida a mensagem e a classe CSS de erro ao enviar o login vazio.
- [Questão 02](questao-02/): aguarda o carregamento dinâmico e valida a exibição de `Hello World!`.
- [Questão 03](questao-03/): autentica no SauceDemo, adiciona três produtos e remove um do carrinho.
- [Questão 04](questao-04/): coleta os laptops exibidos no Demoblaze e identifica o de maior preço.
- [Questão 05](questao-05/): coleta os produtos do SauceDemo e imprime os que custam menos de US$ 20,00.

## Estrutura

```text
.
├── questao-01/
├── questao-02/
├── questao-03/
├── questao-04/
├── questao-05/
├── pom.xml
└── README.md
```

Cada módulo contém seu próprio `pom.xml` e teste em `src/test/java`, podendo ser executado de forma independente.

## Pré-requisitos

Instale Java 17 ou superior, Maven e Google Chrome/Chromium. O Selenium Manager gerencia automaticamente o ChromeDriver; o navegador precisa estar instalado e disponível no ambiente. Em Linux e Codespaces, os testes usam Chrome headless com `--no-sandbox` e `--disable-dev-shm-usage`.

## Execução

Na raiz, execute a suíte completa:

```sh
mvn test
```

Para executar uma questão individualmente, rode o comando correspondente a partir da raiz (um por vez):

```sh
cd questao-01 && mvn test
cd questao-02 && mvn test
cd questao-03 && mvn test
cd questao-04 && mvn test
cd questao-05 && mvn test
```

Os testes 01 a 04 devem concluir suas validações JUnit; a questão 05 imprime cada produto abaixo do limite ou informa claramente quando nenhum corresponder. A questão 04 imprime o nome e o preço do laptop mais caro encontrado na categoria carregada.

## Resultados e disponibilidade

Os valores e nomes de produtos são obtidos diretamente dos sites durante a execução, sem listas de resultados fixas. Os testes dependem de acesso à internet, disponibilidade dos sites externos e de um Chrome/Chromium funcional. Falhas de rede, alterações nos sites ou ausência do navegador podem impedir a execução, independentemente da compilação do projeto.