public class Decompress {

    /**
     * Assume perfect input. No need for validation.
     *
     * @param args
     */
    public static void main(String[] args) {
        Decompress d = new Decompress();
        String temp = "2[6[2[3[g]A]]sb]";
        System.out.println(d.getSub(temp));
    }

    private String getSub(String input) {
        int length = input.length();
        if (input != null && input.length() > 0 && input.contains("[")) {
            char[] inputArray = input.toCharArray();
            int fistDigitIndex = 0, lastDigitIndex = 1;
            int openFirstBracketIndex = 0, openSecondBracketIndex = 0, closeBracketIndex = 0;
            int i = 0;
            for (Character c : inputArray) {
                if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
                    if (fistDigitIndex == 0 && lastDigitIndex == 0) {
                        fistDigitIndex = i;
                        lastDigitIndex = i;
                    } else {
                        fistDigitIndex = lastDigitIndex;
                        lastDigitIndex = i;
                    }
                    i++;
                    continue;
                } else if (c == '[') {
                    if (openFirstBracketIndex == 0 && openSecondBracketIndex == 0) {
                        openFirstBracketIndex = i;
                        openSecondBracketIndex = i;
                    } else {
                        openFirstBracketIndex = openSecondBracketIndex;
                        openSecondBracketIndex = i;
                    }
                    i++;
                    continue;
                } else if (c == ']') {
                    closeBracketIndex = i;
                    break;
                }
                i++;
            }

            String newInput = "";
            if (openSecondBracketIndex > 0 && closeBracketIndex > 0) {
                int repeat = Integer.parseInt(input.substring(lastDigitIndex, openSecondBracketIndex));
                String text = input.substring(openSecondBracketIndex + 1, closeBracketIndex);
                MyToken token = new MyToken(repeat, text);
                String decomp = token.deCompress();

                if (openSecondBracketIndex <= 1) {
                    newInput = decomp + input.substring(closeBracketIndex + 1, length);
                } else {
                    newInput = input.substring(0, openFirstBracketIndex + 1) + decomp + input.substring(closeBracketIndex + 1, length);
                }
            }

            if (!newInput.contains("[")) {
                return newInput;
            } else {
                return getSub(newInput);
            }
        }
        return "";
    }

    public class MyToken {

        int    repeat = 1;
        String text   = "";

        public MyToken(int repeat, String test) {
            this.repeat = repeat;
            this.text = test;
        }

        public String deCompress() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < repeat; i++) {
                sb.append(text);
            }
            return sb.toString();
        }
    }

}
