package com.assoc.jad.login.tools;

public class SendEmail implements Runnable {

	private String[] contactEmails;
	private String message;
	private String subject;

	public SendEmail(String[] contactEmails, String message, String subject) {
		this.contactEmails = contactEmails;
		this.message = message;
		this.subject = subject;
	}
	private synchronized void waiting() {
		
		try {
			wait(60*60*1000);
		} catch (Exception e) {
			System.out.println("SendEmail::waiting "+" "+"\n"+e);
			return;
		}
	}
	private boolean bldAndSend() {
        try {
            EMailWithAttachment sender = new EMailWithAttachment(System.getenv("EMAILID"),System.getenv("EMAILPSW"));

            sender.setHost("smtp." + "comcast.net");
            sender.setBody(message);
            sender.setFrom(System.getenv("EMAILID"));
            sender.setSubject(subject);
            sender.setTo(contactEmails);
            sender.addAttachment("a/f/c/");
            sender.send();
        } catch (Exception e) {
//        	TODO failed email what needs to be done
            System.out.println("ExecSelectedAppsActivity \n"+e.getMessage());
            e.printStackTrace();
            return false;
        }
		return true;
	}

	@Override
	public void run() {
		while (!bldAndSend()) {
			this.waiting();
		}
 	}

}
