package hello.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Transactional // @Transactional 사용하려면 TransactionManager type Spring bean이 등록되어 있어야 함
    @Test
    void memberTest() {
        memberRepository.initTable();

        Member member = new Member("idA", "memberA");
        memberRepository.save(member);

        Member foundMember = memberRepository.find(member.getMemberId());

        assertThat(foundMember.getMemberId()).isEqualTo(member.getMemberId());
        assertThat(foundMember.getName()).isEqualTo(member.getName());
    }
}
