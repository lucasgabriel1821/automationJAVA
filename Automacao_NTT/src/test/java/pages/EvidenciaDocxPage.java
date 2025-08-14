package pages;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EvidenciaDocxPage {
    private final WebDriver driver;
    private static final String BASE_PATH = "CAMINHO\\PARA\\O ARQUIVO";
    private String currentTestFolder;
    private XWPFDocument document;
    private FileOutputStream out;
    private String docxFilePath;

    public EvidenciaDocxPage(WebDriver driver) {
        this.driver = driver;
        this.document = new XWPFDocument();
        createTestFolder();
        createDocument();
    }

    private void createTestFolder() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM");
        String folderName = dateFormat.format(new Date());
        currentTestFolder = BASE_PATH + folderName + "\\";

        try {
            Path path = Paths.get(currentTestFolder);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar pasta de evidências: " + e.getMessage());
        }
    }

    private void createDocument() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
        String timestamp = timeFormat.format(new Date());
        docxFilePath = currentTestFolder + "Evidencia_" + timestamp + ".docx";

        try {
            out = new FileOutputStream(docxFilePath);
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao criar documento Word: " + e.getMessage());
        }
    }

    public void addEvidenceToDocx(String stepName, String status, String description) {
        try {

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Adiciona título do passo
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontSize(14);
            run.setText(stepName + " - " + status);
            run.addBreak();

            // Adiciona descrição
            run.setBold(false);
            run.setFontSize(12);
            run.setText(description);
            run.addBreak();

            // Adiciona a imagem
            run.addPicture(new ByteArrayInputStream(screenshot),
                    XWPFDocument.PICTURE_TYPE_PNG,
                    stepName,
                    Units.toEMU(500),
                    Units.toEMU(300));

            // Adiciona separador
            paragraph = document.createParagraph();
            run = paragraph.createRun();
            run.setText("________________________________________________________");
            run.addBreak();
            run.addBreak();

        } catch (Exception e) {
            System.err.println("Erro ao adicionar evidência ao documento: " + e.getMessage());
        }
    }

    public void reportaTestePassou(String stepName) {
        String description = "Data: " + new Date() + "\nStatus: Passou";
        addEvidenceToDocx(stepName, "PASSOU", description);
    }

    public void reportaTesteFalhou(String stepName, String errorMessage) {
        String description = "Data: " + new Date() + "\nStatus: Falhou\nErro: " + errorMessage;
        addEvidenceToDocx(stepName, "FALHOU", description);
    }

    public void finalizarDocumento() {
        try {
            document.write(out);
            out.close();
            document.close();
        } catch (IOException e) {
            System.err.println("Erro ao finalizar documento: " + e.getMessage());
        }
    }
}
