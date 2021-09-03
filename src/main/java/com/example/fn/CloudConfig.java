package com.example.fn;

import com.google.common.io.Resources;
import oracle.nosql.driver.Region;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CloudConfig {

    Region region = Region.SA_SAOPAULO_1;
    String tenantId = "ocid1.tenancy.oc1..aaaaaaaadwdhpalotxkw6rnugaoemjpzgvq25inijoquvfhznrif343yz3pa";
    String userId = "ocid1.user.oc1..aaaaaaaa6tulllast4a5rsieatm3jhlbdqzyrjl3sv5nlv7mn2cy3gufxw7q";
    String fingerprint = "70:f6:b2:cd:fe:11:9e:e5:55:55:e2:46:71:5c:a5:d6";

    //Ler privatekey da pasta resources
    URL url = TesteClass.class.getResource("chavepriv.pem");
    String privateKey;

    {
        try {
            privateKey = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //int password = 0;
    char[] passphrase = null;


    String tableName = "ocid1.nosqltable.oc1.sa-saopaulo-1.amaaaaaaupwapsqacnx534zvlothebx73i4tz5vznwitsnazdkfu5pp3owma";


    //Getters
    public Region getRegion() {
        return region;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public char[] getPassphrase() {
        return passphrase;
    }

    public String getTableName() {
        return tableName;
    }





}
