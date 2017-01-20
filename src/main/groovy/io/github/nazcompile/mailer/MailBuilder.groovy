package io.github.nazcompile.mailer

class MailBuilder {

	private Mail mail

	private def toMode = false
	private def ccMode = false
	private def bccMode = false
	private def attachmentMode = false


	Mail build(Closure definition) {
		mail = new Mail()
		runClosure definition
		mail
	}

	void to(Closure email) {
		toMode = true
		runClosure email
		toMode = false
	}

	void cc(Closure email) {
		ccMode = true
		runClosure email
		ccMode = false
	}

	void bcc(Closure email) {
		bccMode = true
		runClosure email
		bccMode = false
	}

	void email(String toEmail) {
		if (toMode) {
			mail.to << toEmail
		} else if (ccMode) {
			mail.cc << toEmail
		} else if (bccMode) {
			mail.bcc << toEmail
		} else {
			throw new IllegalStateException("email() only allowed in to, cc or bcc context.")
		}
	}
	
	def methodMissing(String name, arguments) {
		if (name == 'from') {
			mail.from = arguments[0]
		}
	}

	void attachment (Closure closure) {
		attachmentMode = true
		runClosure closure
		attachmentMode = false
	}
	
	void name(String fileName) {
		if (attachmentMode) {
			mail.attachments << fileName
		} else {
			throw new IllegalStateException("name() only allowed in attachment context.")
		}
	}
	
	private runClosure(Closure closure) {
		Closure runClone = closure.clone()
		runClone.delegate = this
		runClone.resolveStrategy = Closure.DELEGATE_ONLY
		runClone()
	}
}

