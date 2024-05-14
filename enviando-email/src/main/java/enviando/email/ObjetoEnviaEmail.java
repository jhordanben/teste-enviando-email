package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ObjetoEnviaEmail {

	private String userName = "jhordanjava@gmail.com";
	private String senha = "srzw dkan yigm gwnu";
	
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String textoEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String textoEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.textoEmail = textoEmail;
	}
	
	
	public void enviarEmail(boolean envioHtml) throws Exception {
	
	Properties properties = new Properties();
	
	properties.put("mail.smtp.ssl.trust", "*");
	properties.put("mail.smtp.auth", "true"); //autorização
	properties.put("mail.smtp.starttls", "true"); //autenticação
	properties.put("mail.smtp.host", "smtp.gmail.com"); //servidor gmail google
	properties.put("mail.smtp.port", "465"); //porta do servidor
	properties.put("mail.smtp.socketFactory.port", "465" ); //expecifica a porta 
	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //classe socket de conexão ao SMTP
	
	Session session = Session.getInstance(properties, new Authenticator() {
	
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(userName, senha);	
		
		}
		
	
	});
		
	
	
	Address[] toUser = InternetAddress.parse(listaDestinatarios);
	
	Message message = new MimeMessage(session);
	message.setFrom(new InternetAddress(userName, nomeRemetente)); //quem esta enviando
	message.setRecipients(Message.RecipientType.TO, toUser); //email de destino
	message.setSubject(assuntoEmail); //assunto do email
	
	
	
	
	
	if (envioHtml) {
		message.setContent(textoEmail, "text/html; charset=utf-8");
	
	} else {
		message.setText(textoEmail); //texto
	}
	
	
		Transport.send(message);
	
	}
	
	public void enviarEmailAnexo(boolean envioHtml) throws Exception {
		
		Properties properties = new Properties();
		
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true"); //autorização
		properties.put("mail.smtp.starttls", "true"); //autenticação
		properties.put("mail.smtp.host", "smtp.gmail.com"); //servidor gmail google
		properties.put("mail.smtp.port", "465"); //porta do servidor
		properties.put("mail.smtp.socketFactory.port", "465" ); //expecifica a porta 
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //classe socket de conexão ao SMTP
		
		Session session = Session.getInstance(properties, new Authenticator() {
		
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, senha);	
			
			}
			
		
		});
			
		
		
		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, nomeRemetente)); //quem esta enviando
		message.setRecipients(Message.RecipientType.TO, toUser); //email de destino
		message.setSubject(assuntoEmail); //assunto do email
		
		//parte 1 do email que é texto e a descrição do email
		
		MimeBodyPart corpoEmail = new MimeBodyPart();
		
		
		if (envioHtml) {
			corpoEmail.setContent(textoEmail, "text/html; charset=utf-8");
		
		} else {
			corpoEmail.setText(textoEmail); //texto
		}
		
		
		List<FileInputStream> arquivos = new ArrayList<FileInputStream>();
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		arquivos.add(simuladorDePdf());
		
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(corpoEmail);
		
		int index = 0;
		for (FileInputStream fileInputStream : arquivos) {
			
			
			//parte 2 do email que são os anexos em pdf
			MimeBodyPart anexoEmail = new MimeBodyPart();
			
			//onde é passado o simuladorDePdf você passa o seu arquivo gravado no banco de dados
			anexoEmail.setDataHandler(new DataHandler(new ByteArrayDataSource(fileInputStream, "application/pdf")));
			anexoEmail.setFileName("anexoemail"+index+".pdf");
			
			
			multipart.addBodyPart(anexoEmail);
			
			index++;
		
		}
		
		message.setContent(multipart);
		
			Transport.send(message);
		
		}
	
	//esse metodo simula o pdf ou qualquer arquivo que possa ser enviado por email
	//voce pode pegar o arquivo no seu banco de dados base64, byte[], stream de arquivos.
	//pode estar em um banco de dados, ou em uma pasta.	
	
	 private FileInputStream simuladorDePdf() throws Exception {
		 Document document = new Document();
		 File file = new File ("fileanexo.pdf");
		 file.createNewFile();
		 PdfWriter.getInstance(document, new FileOutputStream(file));
		 document.open();
		 document.add(new Paragraph("Conteudo do PDF anexo com Java Mail, texto do PDF"));
		 document.close();
		 
		 return new FileInputStream(file);
	 }



}
