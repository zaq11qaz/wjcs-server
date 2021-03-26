package com.huihe.eg.comm;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtils {
    /**
     * 创建token
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     */
    public  static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        //id，issuer，subject，ttlMillis都是放在payload中的，可根据自己的需要修改
        //签名的算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //当前的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //签名算法的秘钥，解析token时的秘钥需要和此时的一样
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("miyao");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //构造
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        System.out.println("---token生成---");
        //给token设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            System.out.println("过期时间："+exp);
            builder.setExpiration(exp);
        }
        //压缩
        return builder.compact();
    }
}
