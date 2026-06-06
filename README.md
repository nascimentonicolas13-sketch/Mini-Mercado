# Mini Mercado - Sistema de Gerenciamento

Sistema de terminal desenvolvido em Java para gerenciamento de mini mercado.

## Como Executar

**Pre-requisito:** Ter o JDK instalado (https://adoptium.net)

**1. Abra o terminal na pasta do projeto**

**2. Compile:**
```
javac *.java
```

**3. Execute:**
```
java Main
```

## Estrutura dos Arquivos

| Arquivo | Descricao |
|---|---|
| Main.java | Menu principal do sistema |
| Produto.java | Classe Produto (codigo, nome, preco, estoque) |
| Cliente.java | Classe Cliente (cpf, nome, telefone, email) |
| CrudProdutos.java | CRUD completo de Produtos |
| CrudClientes.java | CRUD completo de Clientes |
| Compra.java | Realizar Compra e Controle de Estoque |
| Arquivos.java | Salvar e carregar dados em CSV |

## Dados salvos

Os dados ficam na pasta `data/`:
- `data/produtos.csv`
- `data/clientes.csv`
