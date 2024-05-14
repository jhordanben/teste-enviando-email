package enviando.email;

import static org.junit.Assert.assertTrue;

import java.net.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	
	
	@org.junit.Test
	public void testeEmail() throws Exception{
		
		StringBuilder stringBuilderTextoEmail = new StringBuilder();
		
		stringBuilderTextoEmail.append("Olá, <br/><br/>");
		stringBuilderTextoEmail.append("Você está recebendo acesso ao Instagram.<br/><br/>");
		stringBuilderTextoEmail.append("Para ter acesso clique no botão abaixo.<br/><br/>");
		stringBuilderTextoEmail.append("<b>Login:</b> jhordan@gmail.com<br/>");
		stringBuilderTextoEmail.append("<b>Senha:</b> jhordanzao<br/>");
		stringBuilderTextoEmail.append("<a target=\"_blank\" href=\"https://www.instagram.com style=\"color:#2525a7; padding: 14px 25px; text-align:center; text-decoration: none; display:inline-block; border-radius:30px; font-size:20px; font-family:courier; border : 3px solid green;background-color:#99DA39;\">Acessar o Instagram</a>");
		
		stringBuilderTextoEmail.append("<span style=\"font-size:8px\">Ass.: jhordan</span>");
		
			ObjetoEnviaEmail enviaEmail = 
					new ObjetoEnviaEmail("benaraujo74@gmail.com", 
										 "jhordan", 
										 "texto em java", 
										 "texto em java MANDADO POR JAVA");
										 stringBuilderTextoEmail.toString();
					 
		
		enviaEmail.enviarEmailAnexo(true);
		
		Thread.sleep(5000);
		
		
	}
	
	

}
