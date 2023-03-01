package hellojpa;

import javax.persistence.*;
import java.util.*;

public class JpaMain {
    public static void main(String[] args) {
        //데이터 베이스 연결, 로딩 시점에 한번만 만들어야한다. - 하나로 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //엔티티 매니저는 스레드간 공유하지 말아야하고, 사용 후 버려야한다.
        EntityManager em = emf.createEntityManager();   //쉽게 데이터베이스 커넥션을 받아왔다고 생각하면 됨

        //JPA의 데이터 변경은 모두 트랜잭션 안에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction(); //트랜잭션을 얻음
        tx.begin(); //트랜잭션 시작

        /*데이터 수정
        * JPA로 데이터를 가져오게되면 JPA가 관리하며 데이터를 확인한다.
        * 트랜잭션 커밋 시점에 데이터의 변경이 있으면 해당 변경 내용을 쿼리로 만들어 보낸다.
        * */
        try{

            //객체 대상으로 전체 데이터를 가져온다. - JPQL
            List<Member> result = em.createQuery("select m from Member", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();   //형식으로 작성하면 페이징같은 문제가 편해진다.


            Member findMember = em.find(Member.class, 1L);//em은 내 객체를 대신 저장해준다.
            System.out.println(findMember.getId() + " / " + findMember.getName());
            findMember.setName("HelloJpa");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        /*데이터 저장 - 좋은 코드(실무에서 사용)
        try{
            Member member = new Member();
            member.setId(3L);
            member.setName("test");

            em.persist(member);
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
        /*good*/

        /*실제 데이터 처리 - 안좋은 코드*/
        /* Member member = new Member();   //객체 생성
        member.setId(2L);               //저장할 데이터 set
        member.setName("Helloㅠ");
        em.persist(member);             //객체 저장
        tx.commit();                    //트랜잭션 커밋
        em.close();
        emf.close();
        /*데이터 처리 여기까지*/



    }

}
