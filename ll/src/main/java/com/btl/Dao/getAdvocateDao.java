package com.btl.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.btl.Controller.Firebaseinitialize;
import com.google.cloud.firestore.QueryDocumentSnapshot;

public class getAdvocateDao {

    public static String email;

    public static List getLawyer(String Collection) throws InterruptedException, ExecutionException{
        List<QueryDocumentSnapshot> documents = Firebaseinitialize.db.collection("Lawyerinfo").get().get().getDocuments();
       
        List<Map<String , Object>>lawyerdata = new ArrayList<>();
        //List<Map<String , Object>> fooddata = new ArrayList<>();

    for (int i = 0; i < documents.size(); i++) {
        QueryDocumentSnapshot document = documents.get(i);

        String fullname = document.getString("Fullname");
        String specialization = document.getString("Specialization");
        String exp = document.getString("Experience");
         email = document.getString("email");
         String bio = document.getString("Bio");
      //  String ValidFor = document.getString("ValidFor");

        System.out.println(fullname);
        System.out.println(specialization);
        System.out.println(exp);
     //   System.out.println(ValidFor);

        Map<String, Object> map = new HashMap<>();
        map.put("Fullname", fullname);
        map.put("Specialization", specialization);
        map.put("Experience", exp);
        map.put("Bio", bio);

        //map.put("ValidFor", ValidFor);

        lawyerdata.add(map);
    }

        System.out.println("Read all Successfully");
        return lawyerdata;
    }
    
}
