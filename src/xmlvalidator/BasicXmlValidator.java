package xmlvalidator;

import static java.lang.System.*;

import java.util.regex.*;

public class BasicXmlValidator implements XmlValidator {

	@Override
	public String[] validate(String xmlDocument) {
		// TODO Auto-generated method stub
		StringBuilder error = new StringBuilder();
		String expr = "<[^<>]+>";
		boolean check = false;
		BasicStringStack xmlChecker = new BasicStringStack();
		String name;
		String[] parts = xmlDocument.split(lineSeparator());
		Pattern p = Pattern.compile(expr);
		int line = 1;
		for (String A : parts) {
			Matcher m = p.matcher(A);
			while (m.find()) {
				check = false;
				if ((name = checkForOpen(m.group())) != null) {
					xmlChecker.push(name);
					xmlChecker.push(Integer.toString(line));
				} else if ((name = checkForClose(m.group())) != null) {
					if (xmlChecker.getCount() != 0) {
						if (xmlChecker.peek(1).equals(name)) {
							xmlChecker.remove(1);
							xmlChecker.remove(0);
							check = true;
						} else if (!check) {
							error.append("Tag mismatch");
							error.append(",");
							error.append(xmlChecker.remove(1));
							error.append(",");
							error.append(xmlChecker.remove(0));
							error.append(",");
							error.append(name);
							error.append(",");
							error.append(line);

						}
						for (int i = 0; i < xmlChecker.getCount(); i++) {
							if (xmlChecker.peek(i).equals(name)) {
								xmlChecker.remove(i);
								xmlChecker.remove(i - 1);
								check = true;
							}
						}
					} else if (xmlChecker.getCount() == 0) {
						error.append("Orphan closing tag");
						error.append(",");
						error.append(name);
						error.append(",");
						error.append(line);

					}

				}
			}
			line++;
		}
		if (xmlChecker.getCount() != 0) {
			error.append("Unclosed tag at end");
			error.append(",");
			error.append(xmlChecker.remove(1));
			error.append(",");
			error.append(xmlChecker.remove(0));
		}

		line = 1;
		if (error.toString().length() > 0) {
			String[] answer = error.toString().split(",");
			return answer;
		} else {
			return null;
		}
	}

	public String checkForOpen(String A) {
		int i = 0;
		i++;
		if (A.charAt(i) != '/' && A.charAt(i) != '!' && A.charAt(i) != '?') {
			while (A.charAt(i) != ' ' && A.charAt(i) != '>') {
				i++;
			}

			return A.substring(1, i);
		} else {
			return null;
		}

	}

	public String checkForClose(String A) {
		int i = 0;
		i++;
		if (A.charAt(i) == '/' && A.charAt(i) != '!' && A.charAt(i) != '?') {
			return A.substring(2, A.length() - 1);
		} else {
			return null;
		}

	}
}
