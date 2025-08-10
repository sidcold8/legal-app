package com.btl.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Controller.Firebaseinitialize;
import com.google.cloud.firestore.QueryDocumentSnapshot;

public class getnotificationDao {

    public static String email;

    public static List getLawyer(String Collection) throws InterruptedException, ExecutionException{
        List<QueryDocumentSnapshot> documents = Firebaseinitialize.db.collection("Appointmentinfo").get().get().getDocuments();
       
        List<Map<String , Object>>lawyerdata = new ArrayList<>();
        //List<Map<String , Object>> fooddata = new ArrayList<>();

    for (int i = 0; i < documents.size(); i++) {
        QueryDocumentSnapshot document = documents.get(i);

        String fullName = document.getString("Fullname");
        String email = document.getString("Email");
        String datePicker = document.getString("Date");
        // email = document.getString("Description");
         String desc = document.getString("Description");
      //  String ValidFor = document.getString("ValidFor");

        System.out.println(fullName);
        System.out.println(email);
        System.out.println(datePicker);
       System.out.println(desc);

        Map<String, Object> map = new HashMap<>();
        map.put("Fullname", fullName);
        map.put("Email", email);
        map.put("Date", datePicker);
        map.put("Description", desc);

        //map.put("ValidFor", ValidFor);

        lawyerdata.add(map);
    }

        System.out.println("Read all Successfully");
        return lawyerdata;
    }
    
}

