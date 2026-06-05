# Mini Mercado - Sistema de Gerenciamento

Sistema de terminal desenvolvido em Java para gerenciamento de mini mercado.

## Como Executar

**Pré-requisito:** Ter o JDK instalado (https://adoptium.net)

**1. Compilar:**
```
javac *.java
```

**2. Executar:**
```
java Main
```

> Execute sempre na pasta raiz do projeto (onde estão os arquivos .java)

## Funcionalidades

- CRUD completo de Produtos
- CRUD completo de Clientes
- Realizar Compra com cupom fiscal
- Controle de Estoque
- Persistência de dados em arquivos CSV (pasta `data/`)

## Arquivos

| Arquivo | Descrição |
|---|---|
| Main.java | Sistema principal com todos os menus |
| Produto.java | Classe Produto |
| Cliente.java | Classe Cliente |
| Arquivos.java | Leitura e escrita dos arquivos CSV |
| data/produtos.csv | Dados dos produtos |
| data/clientes.csv | Dados dos clientes |
