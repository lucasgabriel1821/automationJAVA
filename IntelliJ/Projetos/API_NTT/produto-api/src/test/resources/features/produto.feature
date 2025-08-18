# language: pt

Funcionalidade: Gerenciamento de Produtos
  Como sistema
  Quero gerenciar produtos
  Para garantir a integridade dos dados

  Cenário: Criar produto com sucesso
    Dado que eu tenha um produto válido
    Quando eu envio uma requisição POST para "/products/add"
    Então o status deve ser 201
    E o campo "title" deve estar correto
    E o campo "price" deve estar correto
    E o campo "stock" deve estar correto

  Cenário: Validar campos obrigatórios
    Dado que eu tenha um produto sem campo obrigatório
    Quando eu envio uma requisição POST para "/products/add"
    Então o status deve ser 201

  Cenário: Consultar produto existente
    Quando eu envio uma requisição GET para "/products/1"
    Então o status deve ser 200
    E o campo "title" deve estar como "Essence Mascara Lash Princess"
    E o campo "brand" deve estar como "Essence"
    E o campo "category" deve estar como "beauty"
