package steps;

import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;
import pages.PesquisaPage;
import hooks.hookPetz;

public class StepPesquisaProduto {

    PesquisaPage pesquisaPage = new PesquisaPage(hookPetz.driver);

    @Quando("eu pesquisar por {string}")
    public void euPesquisarPor(String produto) {
        pesquisaPage.pesquisarProduto(produto);
    }

    @E("eu clicar no produto {string}")
    public void euClicarNoProduto(String nomeProduto) {
        pesquisaPage.clicarProdutoPorNome(nomeProduto);
    }

    @E("adiciono ao carrinho")
    public void adicionoAoCarrinho() {
        pesquisaPage.adicionarProdutoAoCarrinho();
    }

    @E("o preço no carrinho deve ser {string}")
    public void oPrecoNoCarrinhoDeveSer(String precoEsperado) {
        pesquisaPage.validarPrecoNoCarrinho(precoEsperado);
    }

    @E("o preço do produto deve ser {string}")
    public void oPrecoDoProdutoDeveSer(String precoEsperado) {
        pesquisaPage.validarPrecoProduto(precoEsperado);
    }

    @E("Finalizo captura do teste")
    public void finalizarEvdc() {
       pesquisaPage.finalizarEvidencias();
    }
}
