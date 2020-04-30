import com.cnqisoft.Callback;
import com.cnqisoft.MessageEntity;
import com.cnqisoft.ProtocolNumber;
import com.cnqisoft.Server;
import org.junit.Test;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Binary on 2020/4/30
 */
public class ClientTest {

    @Test
    public void test() {
        byte[] bytes = new byte[]{12, 21, 21, 31, 23, 21, 3};
        byte[] tmp = new byte[3];
        System.arraycopy(bytes, 2, tmp, 0 , 3);
        System.out.println(tmp);
    }

    @Test
    public void integerTest() {
        String s = Integer.toHexString(456464645);
        System.out.println(s);
        BigInteger bigint=new BigInteger(s, 16);
        System.out.println(bigint.intValue());
    }

    @Test
    public void testLogin() {
        Server server = new Server(new Callback() {

            @Override
            public void onDataReceived(MessageEntity messageEntity) {
                System.out.println("有数据!");

                if (messageEntity.getProtocolNumber() == ProtocolNumber.LOGIN) {
                    System.out.println("登录请求");
                    byte[] bytes = new byte[] { 0X78, 0X78, 0X01, 0X01, 0X0D, 0x0A };
                    try {
                        messageEntity.getOutputStream().write(bytes);
                        System.out.println("IMEI" + messageEntity.getIMEI());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println(1);
                }
            }
        });

        server.start();
    }

}
