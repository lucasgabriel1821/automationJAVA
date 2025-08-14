package steps;

import io.cucumber.java.pt.Dado;
import pages.PesquisaPage;
import static hooks.hookPetz.driver;

public class StepAcessarSite {

    @Dado("que eu acesso o site {string}")
    public void queEuAcessoOSite(String url) {
        //private WebDriver driver;
        PesquisaPage pesquisaPage = new PesquisaPage(driver);
        pesquisaPage.abrirUrl(url);
    }
}
