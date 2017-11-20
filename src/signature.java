import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;

public class signature {
  public static void main(String[] args) throws Exception {
    

    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

    keyGen.initialize(512, new SecureRandom());

    KeyPair keyPair = keyGen.generateKeyPair();
    Signature signature = Signature.getInstance("SHA1withRSA");

    signature.initSign(keyPair.getPrivate(), new SecureRandom());

    byte[] message = "abc".getBytes();
    System.out.println(message.toString());
    signature.update(message);

    byte[] sigBytes = signature.sign();
    signature.initVerify(keyPair.getPublic());
    signature.update(message);
    System.out.println(message);
    System.out.println(signature.verify(sigBytes));

  }
}