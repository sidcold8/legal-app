package com.btl.Dao;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Controller.Firebaseinitialize;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;

public class AppointmentDao {
    public static void adddata(String Collection,String Document,Map<String,Object> data){
            DocumentReference docref = Firebaseinitialize.db.collection(Collection).document(Document);

            ApiFuture<WriteResult> result = docref.set(data);

            try {
                result.get();
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            } catch (ExecutionException e) {
                
                e.printStackTrace();
            }
            
    }
}
