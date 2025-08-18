package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Então;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoStepDefinitions {

    // Aqui vou guardar a resposta que vem da API
    private Response resposta;

    // Aqui vou montar os dados do produto
    private Map<String, Object> dadosDoProduto;

    // Aqui guardo o id do produto depois que crio ele
    private int idDoProduto;

    @Dado("que eu tenha um produto válido")
    public void produtoValido() {
        // Crio um HashMap para simular o corpo do JSON
        dadosDoProduto = new HashMap<>();
        dadosDoProduto.put("title", "Hyaluronic Acid Serum");
        dadosDoProduto.put("price", 19);
        dadosDoProduto.put("discountPercentage", 13.31);
        dadosDoProduto.put("stock", 110);
        dadosDoProduto.put("rating", 4.83);
        dadosDoProduto.put("images", List.of(
                "https://i.dummyjson.com/data/products/16/1.png",
                "https://i.dummyjson.com/data/products/16/2.webp",
                "https://i.dummyjson.com/data/products/16/3.jpg",
                "https://i.dummyjson.com/data/products/16/4.jpg",
                "https://i.dummyjson.com/data/products/16/thumbnail.jpg"
        ));
        dadosDoProduto.put("thumbnail", "https://i.dummyjson.com/data/products/16/thumbnail.jpg");
        dadosDoProduto.put("description", "L'Oréal Paris introduces Hyaluron Expert Replumping Serum formulated with 1.5% Hyaluronic Acid");
        dadosDoProduto.put("brand", "L'Oreal Paris");
        dadosDoProduto.put("category", "skincare");
    }

    @Dado("que eu tenha um produto sem campo obrigatório")
    public void produtoSemCampoObrigatorio() {
        // Aqui vou criar o produto, mas vou esquecer o "title"
        dadosDoProduto = new HashMap<>();
        dadosDoProduto.put("price", 19);
    }

    @Quando("eu envio uma requisição POST para {string}")
    public void enviarPost(String endpoint) {
        // Faço a chamada POST para a API
        resposta = RestAssured
                .given()
                .baseUri("https://dummyjson.com")
                .header("Content-Type", "application/json")
                .body(dadosDoProduto) // o corpo que montei no HashMap
                .post(endpoint)
                .andReturn();
    }

    @Quando("eu envio uma requisição GET para {string}")
    public void enviarGet(String endpoint) {
        // Se o endpoint tiver {id}, troco pelo id salvo
        String urlFinal;
        if (endpoint.contains("{id}")) {
            urlFinal = endpoint.replace("{id}", String.valueOf(idDoProduto));
        } else {
            urlFinal = endpoint;
        }

        // Faço a chamada GET para a API
        resposta = RestAssured
                .given()
                .baseUri("https://dummyjson.com")
                .get(urlFinal)
                .andReturn();
    }

    @Então("o status deve ser {int}")
    public void validarStatus(int statusEsperado) {
        // Comparo o status que espero com o que a API devolveu
        assertEquals(statusEsperado, resposta.getStatusCode());
    }

    @Então("o campo {string} deve estar correto")
    public void validarCampoEsperado(String campo) {
        // Pego o valor quwe eu mandei no HashMap e comparo com o que veio da API
        String valorEsperado = dadosDoProduto.get(campo).toString();
        String valorRecebido = resposta.jsonPath().getString(campo);
        assertEquals(valorEsperado, valorRecebido);
    }

    @Então("o campo {string} deve estar como {string}")
    public void validarCampoComo(String campo, String valorEsperado) {
        // Comparo o valor esperado (fixo no teste) com o que a API devolveu
        String valorRecebido = resposta.jsonPath().getString(campo);
        assertEquals(valorEsperado, valorRecebido);
    }

    @Então("eu salvo o id retornado")
    public void salvarId() {
        // Guardo o id retornado pela API para usar em outra requisição
        idDoProduto = resposta.jsonPath().getInt("id");
    }
}
