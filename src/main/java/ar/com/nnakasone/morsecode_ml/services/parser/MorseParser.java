package ar.com.nnakasone.morsecode_ml.services.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ar.com.nnakasone.morsecode_ml.dto.MessageRequest;
import ar.com.nnakasone.morsecode_ml.services.ParseService;

/**
 * @author Nicolas Nakasone
 *
 */
public class MorseParser implements ParseService {

	private MessageRequest messageRequest;
	
	/**
	 * Constructor de MorseParser
	 * @param message
	 */
	public MorseParser(MessageRequest message) {
		this.messageRequest = message;
	}

	/**
	 * Separa cada codigo del mensaje en elementos separados
	 * @return parsedMessage
	 */
	public List<String> parse() {
		List<String> parsedMessage = new ArrayList<String>(Arrays.asList(messageRequest.getText().split("\\s")));
		
		for (int i=0 ; i<parsedMessage.size() ; i++) {
			if (parsedMessage.get(i).isEmpty()) parsedMessage.set(i," ");
		}
		
		return parsedMessage;
	}

}
