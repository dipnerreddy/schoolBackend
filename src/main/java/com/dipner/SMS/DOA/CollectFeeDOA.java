package com.dipner.SMS.DOA;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CollectFeeDOA {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    public Double getTotalPendingFee(String className){
        TypedQuery<Double> mobileQuery = entityManager
                .createQuery("SELECT u.totalPendingFee FROM SchoolClass u WHERE u.className = :className", Double.class);
        mobileQuery.setParameter("className", className);
        Double tPendingFee = mobileQuery.getSingleResult();
//            log.info("Mobile No: " + dbpassword);
        return tPendingFee;
    }

    @Transactional
    public Double updateTotalFee(String className, Double totalPendingFee){
        Query updateQuery=entityManager.createQuery("UPDATE SchoolClass u SET u.totalPendingFee=:totalPendingFee WHERE u.className= :className");
        updateQuery.setParameter("className",className);
        updateQuery.setParameter("totalPendingFee",totalPendingFee);
        int updatedCount = updateQuery.executeUpdate();
        return null;
    }

    //  Query updateQuery = entityManager
    //                .createQuery("UPDATE Books u SET u.issued = true WHERE u.bookSpecialID = :bookSpecialID");
    //        updateQuery.setParameter("bookSpecialID", bookSpecialID);
//    @Transactional
//    public void updateBloodBank(String email, int oPositive, int oNegative, int aPositive, int aNegative, int bPositive, int bNegative) {
//        Query updateQuery = entityManager
//                .createQuery("UPDATE BloodBank bb SET bb.oPositive = :oPositive, bb.oNegative= :oNegative, bb.aPositive= :aPositive, bb.aNegative=:aNegative, bb.bPositive=:bPositive, bb.bNegative=:bNegative WHERE bb.email=:email");
//        updateQuery.setParameter("oPositive",oPositive);
//        updateQuery.setParameter("oNegative",oNegative);
//        updateQuery.setParameter("aPositive",aPositive);
//        updateQuery.setParameter("aNegative",aNegative);
//        updateQuery.setParameter("bPositive",bPositive);
//        updateQuery.setParameter("bNegative",bNegative);
//        updateQuery.setParameter("email",email);
//        int updatedCount = updateQuery.executeUpdate();
//    }
}
