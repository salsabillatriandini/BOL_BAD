package utils;

import javax.swing.*;
import javax.swing.text.*;
import java.util.Arrays;
import java.util.List;

public class CustomField {
    public static class CustomTextField extends JTextField {
        private final int maxChars;
        private final String regexPattern;

        public CustomTextField(int maxChars, String regexPattern) {
            this.maxChars = maxChars;
            this.regexPattern = regexPattern;
            ((AbstractDocument) getDocument()).setDocumentFilter(new CustomDocumentFilter());
        }

        private class CustomDocumentFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }

        @Override
        public void setText(String text) {
            try {
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public static class CustomTextArea extends JTextArea {

        private final int maxChars;
        private final String regexPattern;

        public CustomTextArea(int maxChars, String regexPattern) {
            this.maxChars = maxChars;
            this.regexPattern = regexPattern;
            ((AbstractDocument) getDocument()).setDocumentFilter(new CustomDocumentFilter());
        }

        private class CustomDocumentFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()).substring(0, offset) + text + fb.getDocument().getText(offset + length, fb.getDocument().getLength() - offset - length);
                if (newStr.length() <= maxChars && newStr.matches(regexPattern)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        }
        @Override
        public void setText(String text) {
            try {
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
    public static class CustomDateTextField extends JTextField {
        private final int maxChars = 11;
        private static final String PLACEHOLDER = "YYYY-MMM-DD";
        private static final String DATE_FORMAT_REGEX = "\\d{0,4}(-[a-zA-Z]{0,3})?(-\\d{0,2})?";
        private static final List<String> MONTHS = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        public CustomDateTextField(int columns) {
            super(PLACEHOLDER, columns);
            ((AbstractDocument) getDocument()).setDocumentFilter(new CustomDocumentFilter());
        }

        private class CustomDocumentFilter extends DocumentFilter {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder builder = new StringBuilder(currentText);
                builder.insert(offset, text);

                if (builder.toString().equals(PLACEHOLDER)) {
                    super.insertString(fb, offset, "", attrs);
                    setCaretPosition(offset + 1);
                } else if (builder.toString().length() <= maxChars &&  builder.toString().matches(DATE_FORMAT_REGEX)) {
                    super.insertString(fb, offset, text, attrs);
                    if (text.equals("-")) {
                        setCaretPosition(offset + 2);
                    } else {
                        setCaretPosition(offset + 1);
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder builder = new StringBuilder(currentText);
                builder.replace(offset, offset + length, text);

                if (builder.toString().equals(PLACEHOLDER)) {
                    super.replace(fb, offset, length, "", attrs);
                    setCaretPosition(offset + 1);
                } else if (builder.toString().length() <= maxChars && builder.toString().matches(DATE_FORMAT_REGEX)) {
                    super.replace(fb, offset, length, text, attrs);
                    if (text.equals("-")) {
                        setCaretPosition(offset + 2);
                    } else {
                        setCaretPosition(offset + 1);
                    }
                }
            }
        }

        public String getDate() {
            String text = getText();
            if (text.equals(PLACEHOLDER)) {
                return "";
            } else {
                String[] dateParts = text.split("-");
                int year = Integer.parseInt(dateParts[0]);
                String month = dateParts[1];
                int day = Integer.parseInt(dateParts[2]);
                int monthIndex = MONTHS.indexOf(month) + 1;
                return String.format("%04d-%s-%02d", year, monthIndex, day);
            }
        }

        @Override
        public void setText(String text) {
            try {
                Document doc = getDocument();
                doc.remove(0, doc.getLength());
                doc.insertString(0, text, null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }
}
