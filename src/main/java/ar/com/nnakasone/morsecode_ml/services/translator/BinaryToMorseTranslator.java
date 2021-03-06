package ar.com.nnakasone.morsecode_ml.services.translator;

import java.util.*;
import ar.com.nnakasone.morsecode_ml.entities.*;
import ar.com.nnakasone.morsecode_ml.exception.UnknownCodeException;
import ar.com.nnakasone.morsecode_ml.services.PatternAnalyzerService;
import ar.com.nnakasone.morsecode_ml.services.TranslateService;
import ar.com.nnakasone.morsecode_ml.services.patternanalyzer.KMeans;

/**
 * @author Nicolas Nakasone
 *
 */

public class BinaryToMorseTranslator implements TranslateService {

	private PatternAnalyzerService pas;
	
	private List<String> parsedMessage;
	
	/**
	 * Constructor de BinaryToMorseTranslator
	 */
	public BinaryToMorseTranslator(List<String> parsedMessage) {
		pas = new KMeans(parsedMessage);
		this.parsedMessage = parsedMessage;
	}

	/**
	 * Traduce un mensaje binario parseado a morse
	 * En caso de encontrar un codigo no existente en la tabla, lanzara una excepcion
	 * @return result
	 * @throws UnknownCodeException
	 */
	@Override
	public String translate() throws UnknownCodeException{
		List<String> translatedMessage = new ArrayList<String>();
		boolean firstTime = true;
		
		while (pas.hasOtherOption()) {
			Iterator<String> it = this.parsedMessage.iterator();
			while(it.hasNext()) {
				String value = it.next();
				translatedMessage.add(pas.determineValue(value));
			}
			
			if (isValidMorseCode(translatedMessage)) {
				return convertToString(translatedMessage);
			} else {
				if (firstTime) {
					firstTime = false;
				} else {
					pas.undo();
				}
				translatedMessage.clear();
				pas.change();
			}
		}
		throw new UnknownCodeException("El codigo ingresado no es traducible");
	}

	/**
	 * Convierte un mensaje expresado en una lista en un string
	 * @param translatedMessage
	 * @return
	 */
	private String convertToString(List<String> translatedMessage) {
		Iterator<String> it = translatedMessage.iterator();
		String response = "";
		while (it.hasNext()) {
			response = response.concat(it.next());
		}
		return response;
	}

	/**
	 * Devuelve si el mensaje es un codigo morse existente
	 * @param translatedMessage
	 * @return
	 */
	private boolean isValidMorseCode(List<String> translatedMessage) {
		Code morse = new Morse();
		boolean response = true;
		String aLetter = "";
		
		Iterator<String> it = translatedMessage.iterator();
		while (it.hasNext() && response) {
			String actual = it.next();
			if (actual.equals(" ")) {
				response = morse.exists(aLetter);		
				aLetter = "";
			} else {
				aLetter = aLetter.concat(actual);				
			}
		}
		return response;
	}
}
