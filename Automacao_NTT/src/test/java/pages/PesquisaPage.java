package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PesquisaPage {

    private By campoPesquisa = By.xpath("//input[@id='headerSearch']");
    private By botaoPesquisar = By.xpath("//button[contains(@class,'search-button')]");
    private By precoProdutoPagina = By.xpath("//div[@class='current-price-left']/strong");

    private final WebDriver driver;
    private final EvidenciaDocxPage evidencia;

    public PesquisaPage(WebDriver driver) {
        this.driver = driver;
        this.evidencia = new EvidenciaDocxPage(driver);
    }

    // Abre a URL
    public void abrirUrl(String url) {
        driver.get(url);
        evidencia.reportaTestePassou("abrirUrl");
    }

    // Pesquisa um produto
    public void pesquisarProduto(String produto) {
        try {
            driver.findElement(campoPesquisa).clear();
            driver.findElement(campoPesquisa).sendKeys(produto + Keys.ENTER);
            evidencia.reportaTestePassou("pesquisarProduto");
        } catch (Exception e) {
            evidencia.reportaTesteFalhou("pesquisarProduto", e.getMessage());
            throw e;
        }
    }

    // Clica em um produto pelo nome
    public void clicarProdutoPorNome(String nomeProduto) {
        try {
            String xpathProduto = String.format("//a[contains(@data-nomeproduto,'%s')]", nomeProduto);
            driver.findElement(By.xpath(xpathProduto)).click();
            evidencia.reportaTestePassou("Clicado no produto com sucesso");
        } catch (Exception e) {
            evidencia.reportaTesteFalhou("clicarProdutoPorNome", e.getMessage());
            throw e;
        }
    }

    // Adiciona o produto ao carrinho
    public void adicionarProdutoAoCarrinho() {
        try {
            By botaoAdicionar = By.id("addToBag");
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(botaoAdicionar))
                    .click();
            evidencia.reportaTestePassou("Adicionado o produto no carrinho com sucesso");
        } catch (Exception e) {
            evidencia.reportaTesteFalhou("adicionarProdutoAoCarrinho", e.getMessage());
            throw e;
        }
    }

    // Valida o preço do produto na página de detalhes
    public void validarPrecoProduto(String precoEsperado) {
        try {
            String precoFormatado = precoEsperado.startsWith("R$") ? precoEsperado : "R$ " + precoEsperado;
            precoFormatado = precoFormatado.trim();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement precoElement = wait.until(ExpectedConditions.visibilityOfElementLocated(precoProdutoPagina));

            String precoTexto = precoElement.getText().replaceAll("\\s+", " ").trim();
            if (!precoTexto.contains(precoFormatado)) {
                throw new AssertionError("Preço incorreto! Esperado: '" + precoFormatado +
                        "' | Encontrado: '" + precoTexto + "'");
            }

            evidencia.reportaTestePassou("Preço do produto condizente: " + precoFormatado);

        } catch (Exception e) {
            evidencia.reportaTesteFalhou("validarPrecoProduto", e.getMessage());
            throw new AssertionError("Erro ao validar preço do produto: " + e.getMessage());
        }
    }

    // Valida o preço no carrinho
    public void validarPrecoNoCarrinho(String precoEsperado) {
        try {
            String precoFormatado = precoEsperado.startsWith("R$") ? precoEsperado : "R$ " + precoEsperado;
            precoFormatado = precoFormatado.trim();

            // Procura qualquer elemento que contenha o preço formatado
            By precoLocator = By.xpath("//*[contains(normalize-space(), '" + precoFormatado + "')]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            List<WebElement> elementosPreco = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(precoLocator));

            if (elementosPreco.isEmpty()) {
                throw new AssertionError("Nenhum elemento contendo o preço '" + precoFormatado + "' foi encontrado");
            }

            String precoTexto = elementosPreco.get(0).getText().trim();
            if (!precoTexto.contains(precoFormatado)) {
                throw new AssertionError("Preço incorreto! Esperado: '" + precoFormatado +
                        "' | Encontrado: '" + precoTexto + "'");
            }

            evidencia.reportaTestePassou("Preço no carrinho condizente: " + precoFormatado);

        } catch (Exception e) {
            evidencia.reportaTesteFalhou("validarPrecoNoCarrinho", e.getMessage());
            throw new AssertionError("Erro ao validar preço no carrinho: " + e.getMessage());
        }
    }

    // Finaliza o documento de evidências
    public void finalizarEvidencias() {
        evidencia.finalizarDocumento();
    }
}
