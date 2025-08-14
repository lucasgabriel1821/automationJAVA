#language: pt

Funcionalidade: Acessar o site Petz, buscar um produto, adicionar ao carrinho e verificar o preço

  Esquema do Cenário: Acessar o site Petz, buscar um produto, adicionar ao carrinho e verificar o preço

    Dado que eu acesso o site "<urlPetz>"
    E eu pesquisar por "<produto>"
    E eu clicar no produto "Escada Baw & Miaw Grafite para Cães e Gatos"
    Então o preço do produto deve ser "R$ 289,99"
    E adiciono ao carrinho
    E Finalizo captura do teste
    #    Quando eu verifico o produto no carrinho
    #Então vejo que o preço é igual ao do anúncio

    Exemplos:
      | urlPetz                      | produto                                                     |
      | https://www.petz.com.br/     | Escada Baw & Miaw Grafite para Cães e Gatos                 |
