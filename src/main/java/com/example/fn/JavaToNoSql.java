package com.example.fn;

import com.google.gson.Gson;
import oracle.nosql.driver.*;
import oracle.nosql.driver.iam.SignatureProvider;
import oracle.nosql.driver.ops.PutRequest;
import oracle.nosql.driver.ops.PutResult;
import oracle.nosql.driver.values.MapValue;

import java.io.IOException;
import java.util.UUID;


public class JavaToNoSql {
    CloudConfig cloudconfig = new CloudConfig();
    public String handleRequest(String input) throws IOException {
        Message message = null;
        Gson gson = new Gson();
        message = gson.fromJson(input,Message.class);

        AuthorizationProvider ap = new SignatureProvider(cloudconfig.tenantId,cloudconfig.userId,cloudconfig.fingerprint,cloudconfig.privateKey,cloudconfig.passphrase);

        /*Configurando Acesso para Oracle Cloud*/
        NoSQLHandleConfig config = new NoSQLHandleConfig(cloudconfig.region,ap);
        NoSQLHandle handle = NoSQLHandleFactory.createNoSQLHandle(config);


        try {
           return inserirRegistro(handle,message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            handle.close();
        }

        return null;
    }


    public String inserirRegistro(NoSQLHandle handle, Message input) throws Exception {

        /* Make a row and write it */
        MapValue value = new MapValue().put("key", UUID.randomUUID().toString()).put("title", input.getTitle()).put("message", input.getMessage()).put("sender", input.getSender());
        PutRequest putRequest = new PutRequest().setValue(value).setTableName(cloudconfig.tableName);

        PutResult putResult = handle.put(putRequest);
        if (putResult.getVersion() != null) {
            System.out.println("Registrado " + value);
            return("Registrado " + value);
        } else {
            System.out.println("Erro");
            return("Erro");
        }

    }

}