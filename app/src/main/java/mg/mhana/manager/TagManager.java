package mg.mhana.manager;

/**
 * Created by Rindra Loic on 3/9/2017.
 */

public class TagManager {
    public static String getUID(byte[]uid){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < uid.length; i++) {
            String x = Integer.toHexString(((int) uid[i] & 0xff));
            if (x.length() == 1) {
                x = '0' + x;
            }
            builder.append(x);
            builder.append(':');
        }
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }
}
