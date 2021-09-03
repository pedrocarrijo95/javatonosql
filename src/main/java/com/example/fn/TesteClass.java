package com.example.fn;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import oracle.nosql.driver.*;
import oracle.nosql.driver.iam.SignatureProvider;
import oracle.nosql.driver.ops.PutRequest;
import oracle.nosql.driver.ops.PutResult;
import oracle.nosql.driver.values.MapValue;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class TesteClass {
    static CloudConfig cloudconfig = new CloudConfig();
    public static void main(String[] args) throws IOException {

        Message msg = new Message();
        msg.setTitle("teste1");
        msg.setMessage("msg1");
        msg.setSender("sender1");
        Message message = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(msg);
            Gson gson = new Gson();
            message = gson.fromJson(json,Message.class);
            //System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        SignatureProvider ap = new SignatureProvider(cloudconfig.tenantId,cloudconfig.userId,cloudconfig.fingerprint,cloudconfig.privateKey,cloudconfig.passphrase);

        /*Configurando Acesso para Oracle Cloud*/
        NoSQLHandleConfig config = new NoSQLHandleConfig(cloudconfig.region,ap);
        NoSQLHandle handle = NoSQLHandleFactory.createNoSQLHandle(config);


        try {
            inserirRegistro(handle,message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            handle.close();
        }

        //return null;
    }


    public static void inserirRegistro(NoSQLHandle handle, Message input){

        /* Make a row and write it */
        MapValue value = new MapValue().put("key", UUID.randomUUID().toString()).put("title", input.getTitle()).put("message", input.getMessage()).put("sender", input.getSender());
        PutRequest putRequest = new PutRequest().setValue(value).setTableName(cloudconfig.tableName);

        PutResult putResult = handle.put(putRequest);
        if (putResult.getVersion() != null) {
            System.out.println("Enviado " + value);
            //return("Wrote " + value);
        } else {
            System.out.println("Erro");
            //return("Put failed");
        }

        /* Make a key and read the row */
        //MapValue key = new MapValue().put("id", 29);
        //GetRequest getRequest = new GetRequest().setKey(key)
        //.setTableName(tableName);

        //GetResult getRes = handle.get(getRequest);
        //System.out.println("Read " + getRes.getValue());

        /* At this point, you can see your table in the Identity Console */
    }
}
