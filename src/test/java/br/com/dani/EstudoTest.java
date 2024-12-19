package br.com.dani; // Bibliotecas

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

// Classe principal
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Teste de Login")
public class EstudoTest {

    // Atributos
    private static WebDriver driver;
    private static WebDriverWait wait;

    // Antes de todos os testes
    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Antes de cada teste
    @BeforeEach
    public void padraoCarregamento() {
        driver.get("https://front.serverest.dev/login");
    }

    // Depois de todos os testes
    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            System.out.println("Teste finalizado");
            driver.quit();
        }
    }

    // Teste 1: Cadastro com dados válidos
    @Test
    @Order(1)
    @DisplayName("Teste realizado no caminho feliz com sucesso")
    public void testeCadastroComDadosValidos() {
        driver.findElement(By.linkText("Cadastre-se")).click();

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        nome.sendKeys("Pandora");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("t17@cat.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123");

        WebElement adminCheckbox = driver.findElement(By.id("administrador"));
        adminCheckbox.click();

        WebElement btnCadastrar = driver.findElement(By.cssSelector(".btn-primary"));
        assertEquals("Cadastrar", btnCadastrar.getText(), "Texto do botão 'Cadastrar' está incorreto.");

        btnCadastrar.click();

        WebElement mensagemBemVindo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1")));
        assertEquals("Bem Vindo Pandora", mensagemBemVindo.getText(), "Mensagem de boas-vindas está incorreta.");
    }

    // Teste 2: Cadastro com email já existente
    @Test
    @Order(2)
    @DisplayName("Teste realizado com mensagem para email existente")
    public void testeCadastroComEmailExiste() {
        driver.findElement(By.linkText("Cadastre-se")).click();

        WebElement nome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
        nome.sendKeys("Pandora");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("t95@cat.com");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123");

        WebElement adminCheckbox = driver.findElement(By.id("administrador"));
        adminCheckbox.click();

        WebElement btnCadastrar = driver.findElement(By.cssSelector(".btn-primary"));
        btnCadastrar.click();

        WebElement alertaMensagem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.alert.alert-secondary.alert-dismissible")));
        assertTrue(alertaMensagem.getText().contains("email já está sendo usado"), "Mensagem de erro para email existente está incorreta.");
    }
}