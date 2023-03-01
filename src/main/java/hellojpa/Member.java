package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //JPA가 관리하다고 인식한다.
@Table(name="Member")   //name을 지정하면 쿼리에 들어가는 테이블의 명을 설정할 수 있다.
public class Member {

    @Id //Jpa에게 PK라고 알려줌
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
