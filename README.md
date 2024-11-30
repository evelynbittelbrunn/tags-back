# Tags Back-End: Como rodar o projeto em ambiente local

## Instalar o Java JDK e o Eclipse IDE

- Instalar o Java JDK 17: Para instalar o Java JDK 17, acesse o site oficial da Oracle: [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html). 

Escolha a versão 17 e siga as instruções de instalação fornecidas no site.

- Instalar o Eclipse IDE: Para instalar o Eclipse IDE, visite o site oficial: [Eclipse](https://www.eclipse.org/downloads/).

Escolha a versão do Eclipse IDE para desenvolvedores Java e siga o passo a passo de instalação do site.

## Clonar o repositório

Clone o repositório para o seu ambiente local com o seguinte comando:

```
https://github.com/evelynbittelbrunn/tags-back.git
```

Navegue até a pasta do projeto:

```
cd tags-back
```

## Importar o Projeto no Eclipse

1 - Abra o Eclipse.
2 - No menu superior, clique em File > Import.
3- Selecione Existing Maven Projects para importar um projeto Maven.
4 - Na janela seguinte, clique em Browse e selecione a pasta onde você clonou o projeto.
5- Clique em Finish para concluir a importação. O Eclipse reconhecerá automaticamente o projeto Maven e começará a baixar as dependências necessárias.

 ## Executar o Projeto

1 - No Eclipse, localize a classe principal que contém o método main.
2 - Clique com o botão direito sobre ela e selecione Run As > Java Application.

O servidor será iniciado na porta 8080. Acesse a API em:

```
http://localhost:8080.
```

E pronto! O projeto estará rodando localmente. 🌻
