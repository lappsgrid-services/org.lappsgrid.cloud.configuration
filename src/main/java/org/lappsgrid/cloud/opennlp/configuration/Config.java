package org.lappsgrid.cloud.opennlp.configuration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

/**
 *
 */
public class Config {
	public static final String USER = "RABBIT_USERNAME";
	public static final String PASS = "RABBIT_PASSWORD";

	public static final String TOKENIZER_BOX = "tokenizer.mailbox";
	public static final String MAXENT_TAGGER_BOX = "maxent-tagger.mailbox";
	public static final String MAXENT_PIPELINE_BOX = "maxent.pipeline.mailbox";
	public static final String PERCEPTRON_TAGGER_BOX = "perceptron-tagger.mailbox";
	public static final String SPLITTER_BOX = "splitter.mailbox";
	public static final String LEMMATIZER_BOX = "lemmatizer.mailbox";
	public static final String LEMMATIZER_PIPELINE_BOX = "lemmatizer.pipeline.mailbox";

	public static final String NER_BOX = "ner.mailbox";
	public static final String NER_PIPELINE_BOX = "ner.pipeline.mailbox";
	public static final String PIPELINE_BOX = "pipeline.mailbox";

	public static final String HOST = "rabbitmq.lappsgrid.org/nlp";
	public static final String OPENNLP_EXCHANGE = "opennlp";
	public static final String STANFORD_EXCHANGE = "stanford";

	public static final String MBOX = "opennlp.cloud.mailbox";
	public static final String HALT = "opennlp.command.halt";

	/**
	 * Subsystems can use the init method to ensure the RabbitMQ credential are set.
	 */
	public static void init() {
		String username = System.getProperty(USER);
		if (username == null) {
			username = System.getenv(USER);
		}
		String password = System.getProperty(PASS);
		if (password == null) {
			password = System.getenv(PASS);
		}
		if (username != null && password != null) {
			// Use the environment variables if set.
			return;
		}

		// Otherwise look for .ini files.
		File file = new File("/etc/lapps/rabbit-nlp.ini");
		if (!file.exists()) {
			file = new File("/run/secrets/rabbit-nlp.ini");
		}
		if (file.exists()) try {
			Properties props = new Properties();
			props.load(new FileReader(file));
			System.setProperty(USER, props.get(USER).toString());
			System.setProperty(PASS, props.get(PASS).toString());
		}
		catch (IOException ignored) {
			// If the default username/password doesn't work then something else
			// will have to deal with it.
		}


	}
	private Config() { }
}
