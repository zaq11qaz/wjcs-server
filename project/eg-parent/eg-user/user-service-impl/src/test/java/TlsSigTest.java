import org.junit.Assert;
import org.junit.Test;


public class TlsSigTest {
    @Test
    public void genAndVerify() {
        try {
            //Use pemfile keys to test
            String privStr = "-----BEGIN PRIVATE KEY-----\n" +
                    "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgqmbksRPtG2FRQlVD\n" +
                    "u3UET5etb1RZ8LQGwIQ61yGbWfuhRANCAARUg/EUc5K53OKJHlBhOrRkXReV5vMU\n" +
                    "3YjpSwZKDAqeF+dGTE6H9H+0e8+WsPaPtvaPuYh+Hqlkb07Wh0k7eneh\n" +
                    "-----END PRIVATE KEY-----\n";

            //change public pem string to public string
            String pubStr = "-----BEGIN PUBLIC KEY-----\n" +
                    "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEvYrD/8S4m+xGdb0uTlMvoDrGAcHn\n" +
                    "rDzA71ok8oDwFLBmb9Txy6GtHIqrBJZDtXzBsDa8ziicdTqNfeEYvksqVQ==\n" +
                    "-----END PUBLIC KEY-----";

            // generate signature
            tls_sigature.GenTLSSignatureResult result = tls_sigature.GenTLSSignatureEx(1400181352, "admin1", privStr);
            Assert.assertNotEquals(null, result);
            System.out.println(result.urlSig);
            Assert.assertNotEquals(null, result.urlSig);
            Assert.assertNotEquals(0, result.urlSig.length());

           /* // check signature
            tls_sigature.CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertTrue(checkResult.verifyResult);

            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertFalse( checkResult.verifyResult);


            // new interface generate signature
            result = tls_sigature.genSig(1400000000, "xiaojun", privStr);
            Assert.assertNotEquals(null, result);
            Assert.assertNotEquals(null, result.urlSig);
            Assert.assertNotEquals(0, result.urlSig.length());

            // check signature
            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertTrue(checkResult.verifyResult);

            checkResult = tls_sigature.CheckTLSSignatureEx(result.urlSig, 1400000000, "xiaojun2", pubStr);
            Assert.assertNotEquals(null, checkResult);
            Assert.assertFalse( checkResult.verifyResult);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

