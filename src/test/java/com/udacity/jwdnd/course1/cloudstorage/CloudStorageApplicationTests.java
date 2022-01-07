package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	public int port;

	public static WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}


	// This test confirms that user cannot access the home page when they have not logged in.
	@Test
	public void testHomePageNotAccessibleWhenNotLoggedIn() {
		driver.get(baseURL + "/home");

		String expectedUrl = baseURL + "/home";
		String actualUrl = driver.getCurrentUrl();
		System.out.println(actualUrl);
		assertFalse(expectedUrl == actualUrl);
	}

	@Test
	public void testSignupAndLoginFlow() throws InterruptedException {
		String firstName = "Project";
		String lastName = "Student";
		String username = "project123";
		String password = "password123";

		driver.manage().window().maximize();
		driver.get("http://localhost:" + this.port + "/signup");

		// User is signed up.
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		// User is logged in.
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		String expectedUrl = baseURL + "/home";
		String actualUrl = driver.getCurrentUrl();

		// Assert that when the user logs in they can access the home page.
		assertEquals(expectedUrl, actualUrl);

		// expectedUrl and actualUrl are set to null;
		expectedUrl = null;
		actualUrl = null;

		// User is then logged out.
		HomePage homePage = new HomePage(driver);
		homePage.logout();

		// Logged out user attempts to access the home page.
		driver.get(baseURL + "/home");

		// This test asserts that a user who has been logged out cannot access.
		expectedUrl = baseURL + "/home";
		actualUrl = driver.getCurrentUrl();
		assertFalse(expectedUrl == actualUrl);
		Thread.sleep(5000);
	}

	@Test
	public void testAddEditDeleteNotes() throws InterruptedException {
		String firstName = "Project";
		String lastName = "Student";
		String username = "project123";
		String password = "password123";

		driver.manage().window().maximize();
		driver.get("http://localhost:" + this.port + "/signup");

		// User is signed up.
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		// User is logged in.
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		// Click on the Notes tab.
		NotePage notePage = new NotePage(driver);
		notePage.notes();
		Thread.sleep(1000);

		// ADD NEW NOTE
		String newNoteTitle = "This is a test";
		String newNoteDescription = "This is the description";
		notePage.addNewNote(newNoteTitle, newNoteDescription);
		notePage.notes();
		Thread.sleep(1000);

		// Check if note has been successfully added.
		List<String> notes = notePage.checkNotes();
		String actualTitle = notes.get(0);
		String actualDescription = notes.get(1);

		// Assert that the title and description match.
		assertEquals(newNoteTitle, actualTitle);
		assertEquals(newNoteDescription, actualDescription);

		// EDIT NOTE
		String editedNoteTitle = "EDITED NOTE TITLE";
		String editedNoteDescription = "EDITED NOTE DESCRIPTION";
		notePage.editNote(editedNoteTitle, editedNoteDescription);
		notePage.notes();
		Thread.sleep(1000);

		// Check if note has been successfully edited.
		List<String> editedNotes = notePage.checkNotes();
		String actualEditedTitle = editedNotes.get(0);
		String actualEditedDescription = editedNotes.get(1);

		// Assert that the title and description have been successfully edited.
		assertEquals(editedNoteTitle, actualEditedTitle);
		assertEquals(editedNoteDescription, actualEditedDescription);

		// DELETE NOTE
		notePage.deleteNote();

		// Check to ensure there are no notes
		List<String> deletedNotes = notePage.checkNotes();

		// Assert that there are no notes.
		assertEquals(0, deletedNotes.size());

		Thread.sleep(1000);
	}

	@Test
	public void testAddEditDeleteCredentials() throws InterruptedException {
		String firstName = "Project";
		String lastName = "Student";
		String username = "project123";
		String password = "password123";

		driver.manage().window().maximize();
		driver.get("http://localhost:" + this.port + "/signup");

		// User is signed up.
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		// User is logged in.
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		// Click on the Credentials tab.
		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.credentials();
		Thread.sleep(1000);

		// ADD NEW CREDENTIAL
		String newCredentialUrl = "www.google.com";
		String newCredentialUsername = "udacity123";
		String newCredentialPassword = "password123";
		credentialPage.addNewCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword);
		credentialPage.credentials();
		Thread.sleep(1000);

		// Check if credential has been successfully added.
		List<String> credentials = credentialPage.checkCredentials();
		String actualUrl = credentials.get(0);
		String actualUsername = credentials.get(1);
		String actualPassword = credentials.get(2);

		// Assert that the URL and Username match. Password needs to be encrypted.
		assertEquals(newCredentialUrl, actualUrl);
		assertEquals(newCredentialUsername, actualUsername);
		assertFalse(newCredentialPassword == actualPassword);

		// EDIT CREDENTIAL
		String editedCredentialUrl = "www.udacity.com";
		String editedCredentialUsername = "student123";
		String editedCredentialPassword = "editedPassword123";
		credentialPage.editCredential(editedCredentialUrl, editedCredentialUsername, editedCredentialPassword);
		credentialPage.credentials();
		Thread.sleep(1000);

		// Check if credential has been successfully edited.
		List<String> updateCredentials = credentialPage.checkCredentials();
		String updateActualUrl = updateCredentials.get(0);
		String updateActualUsername = updateCredentials.get(1);
		String updateActualPassword = updateCredentials.get(2);

		// Assert that the title and description have been successfully edited.
		assertEquals(editedCredentialUrl, updateActualUrl);
		assertEquals(editedCredentialUsername, updateActualUsername);
		assertFalse(editedCredentialPassword == updateActualPassword);

		// DELETE CREDENTIAL
		credentialPage.deleteCredential();

		// Check to ensure there are no credentials
		List<String> deletedCredentials = credentialPage.checkCredentials();

		// Assert that there are no notes.
		assertEquals(0, deletedCredentials.size());

		Thread.sleep(1000);
	}

}
