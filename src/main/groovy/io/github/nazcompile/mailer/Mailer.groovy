package io.github.nazcompile.mailer

class Mailer {
	
	public static def SMTP_HOST
	public static def SMTP_PORT
	private static def TRANSPORT_TYPE = 'smtp'
	
	private static Properties PROPERTIES
	
	public static def configure(Closure closure) {
		closure(this)
		
		PROPERTIES = new Properties()
		PROPERTIES.put('mail.smtp.host', SMTP_HOST)
		PROPERTIES.put('mail.smtp.port', SMTP_PORT)
		PROPERTIES.put('mail.transport.protocol', TRANSPORT_TYPE)
	}	
	
}