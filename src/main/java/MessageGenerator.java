import java.security.SecureRandom;

public class MessageGenerator {

    public String getRandomWord(int length, String alphabet) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom rnd = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int len = alphabet.length();
            int random = rnd.nextInt(len);
            char c = alphabet.charAt(random);
            sb.append(c);
        }
        return sb.toString();
    }

    public int rndMsgLength(Integer maxLength){
        return (int) (Math.random() * maxLength) + 1;
    }
}
