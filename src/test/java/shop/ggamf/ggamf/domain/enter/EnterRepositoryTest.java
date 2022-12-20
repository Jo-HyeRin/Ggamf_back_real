package shop.ggamf.ggamf.domain.enter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import shop.ggamf.ggamf.config.dummy.DummyEntity;
import shop.ggamf.ggamf.domain.follow.Follow;
import shop.ggamf.ggamf.domain.follow.FollowRepository;
import shop.ggamf.ggamf.domain.gameCode.GameCode;
import shop.ggamf.ggamf.domain.gameCode.GameCodeRepository;
import shop.ggamf.ggamf.domain.room.Room;
import shop.ggamf.ggamf.domain.room.RoomRepository;
import shop.ggamf.ggamf.domain.user.User;
import shop.ggamf.ggamf.domain.user.UserRepository;

@ActiveProfiles("test")
@DataJpaTest
public class EnterRepositoryTest extends DummyEntity {

    @Autowired
    private EntityManager em;
    @Autowired
    private EnterRepository enterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private GameCodeRepository gameCodeRepository;
    @Autowired
    private FollowRepository followRepository;

    @BeforeEach
    public void setUp() {
        dummy_init();
    }

    @AfterEach
    public void tearDown() {
        // auto-increment 초기화 시키는 쿼리
        em.createNativeQuery("ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE follow ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE game_code ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE room ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE enter ALTER COLUMN `id` RESTART WITH 1").executeUpdate();
    }

    @Test
    public void findByRoomIdAndUserId_test() throws Exception {
        // given
        Long roomId = 3L;
        Long userId = 2L;

        // when
        Enter enterPS = enterRepository.findByRoomIdAndUserId(roomId, userId)
                .orElseThrow();

        // then
        Assertions.assertThat(enterPS.getId()).isEqualTo(9L);
    }

    @Test
    public void findByRoomId_test() throws Exception {
        // given
        Long roomId = 2L;

        // when
        List<Enter> enterListPS = enterRepository.findByRoomId(roomId);

        // then
        Assertions.assertThat(enterListPS.get(0).getUser().getNickname()).isEqualTo("티모밴하면던짐");
    }

    @Test
    public void findByUserId_test() throws Exception {
        // given
        Long userId = 2L;

        // when
        List<Enter> enterListPS = enterRepository.findByUserId(userId);

        // then
        Assertions.assertThat(enterListPS.get(0).getRoom().getRoomName()).isEqualTo("칼바람전사들 대환영 선착순!");
    }

    @Test
    public void findEnterRoom_test() throws Exception { // 내가 참여했던 방 목록 역순
        // given
        Long userId = 2L;

        // when
        List<Enter> enterListPS = enterRepository.findEnterRoom(userId);

        // then
        Assertions.assertThat(enterListPS.get(0).getRoom().getRoomName()).isEqualTo("골플 자랭 구함 대기 가능");
    }

    @Test
    public void findTogether_test() throws Exception {
        // given
        Long userId = 1L;
        List<Long> roomIdList = new ArrayList();
        roomIdList.add(4L);
        roomIdList.add(8L);

        // when
        List<Enter> enterListPS = enterRepository.findTogether(userId, roomIdList);

        // then
        Assertions.assertThat(enterListPS.size()).isEqualTo(6);
        Assertions.assertThat(enterListPS.size()).isEqualTo(6);
    }

    private void dummy_init() {
        // User : 유저
        User admin = userRepository.save(newAdmin("admin", "01012345678"));
        User ssar = userRepository.save(newUser("ssar", "김쌀", "그만둬 베이가", "01011112222", "안녕하세요"));
        User cos = userRepository.save(newUser("cos", "김코스", "티모밴하면던짐", "01012123456", "티모밴하면던짐"));
        User lala = userRepository
                .save(newUser("lala", "곽형수", "소나없는소나원챔", "01011234567", "듀오구해요"));
        User dada = userRepository.save(newUser("dada", "전창희", "CoolCat", "01012216789", "."));
        User kaka = userRepository
                .save(newUser("kaka", "박진석", "벌써포기하는거냐", "01034567899", "오직노력"));
        User vovo = userRepository
                .save(newUser("vovo", "이상지", "우리팀아힘좀내봐", "01021413568", "근성"));
        User toto = userRepository.save(newUser("toto", "송성훈", "행날", "01065984215", "안녕하세요"));
        User ohoh = userRepository.save(newUser("ohoh", "김혜석", "건축학과", "01060214502", "안녕하세요"));
        User yeye = userRepository
                .save(newUser("yeye", "김장군", "아맞다퇴사", "01073214832", "항상 사직서를.."));
        User gogo = userRepository
                .save(newUser("gogo", "최정웅", "냉장고등어", "01002367535", "주말에만 게임해요"));
        User romio = userRepository.save(newUser("romio", "서재균", "메카마루쉐", "01033094416", "마루쉐"));
        User jeje = userRepository
                .save(newUser("jeje", "고상희", "Phiroth", "01065302408", "롤 랭겜 듀오구해요"));
        User money = userRepository
                .save(newUser("money", "이승호", "Woodang", "01099814520", "저녁에 게임하실 분"));
        User terry = userRepository.save(newUser("terry", "이민서", "이거넴", "01011220242", "인생겜 찾음"));
        User wow = userRepository.save(newUser("wow", "권주안", "Awesomes", "01047286636", "안녕하세요"));
        User cash = userRepository.save(newUser("cash", "진하은", "햄버거피자", "01059453824", "떡볶이마라탕"));
        User power = userRepository
                .save(newUser("power", "김소정", "망겜소생기원", "01078560214", "안녕하세요"));
        User house = userRepository.save(newUser("house", "임주영", "양날의검", "01020221219", "안녕하세요"));
        User nero20 = userRepository.save(newUser("nero20", "정유진", "포치타", "01091212202", "안녕하세요"));
        User poll = userRepository.save(newUser("poll", "최은아", "화목", "01034128972", "안녕하세요"));
        User user22 = userRepository
                .save(newUser("tension", "이세연", "텐션유지가능", "01022048864", "안녕하세요"));
        User user23 = userRepository.save(newUser("alive", "최지원", "부활술사", "01068482848", "안녕하세요"));
        User user24 = userRepository.save(newUser("pepe", "홍승현", "페페", "01010011004", "귀엽지"));
        User 탑솔러그자체 = userRepository
                .save(newUser("탑솔러그자체", "윤세정", "탑솔러그자체", "01094368540", "탑솔러"));
        User 아기사자 = userRepository.save(newUser("아기사자", "김비트", "아기사자", "01033690889", "아기사자입니다"));
        User 둘리 = userRepository.save(newUser("둘리", "이둘리", "둘리", "01022347557", "요리보고"));
        User 키보드부순당 = userRepository.save(newUser("키보드부순당", "박샷건", "키보드부순당", "01023498765", "조금만 잘하자 얘들아"));
        User 임요환짱 = userRepository.save(newUser("임요환짱", "임요환", "임요환짱", "01011110111", "테란황제"));
        User 페이커팬 = userRepository.save(newUser("페이커팬", "이상혁", "페이커팬", "01008270313", "페이커 최고"));
        User 뜨뜨뜨뜨 = userRepository.save(newUser("뜨뜨뜨뜨", "이또또", "뜨뜨뜨뜨", "01077777866", "ㄸㄸㄸㄸ"));
        User 딜찍누 = userRepository.save(newUser("딜찍누", "김딜찍", "딜찍누", "01098897889", "공대원구합니다"));
        User 스피드레이서 = userRepository.save(newUser("스피드레이서", "김속도", "스피드레이서", "01015883061", "이길때됐음"));
        // Follow : 겜프
        Follow f1 = followRepository.save(newFollow(ssar, cos, false));
        Follow f11 = followRepository.save(newFollow(ssar, vovo, false));
        Follow f2 = followRepository.save(newFollow(lala, ssar, false));
        Follow f22 = followRepository.save(newFollow(toto, ssar, false));
        Follow f3 = followRepository.save(newFollow(ssar, dada, true));
        Follow f33 = followRepository.save(newFollow(ssar, ohoh, true));
        Follow f4 = followRepository.save(newFollow(kaka, ssar, true));
        Follow f44 = followRepository.save(newFollow(yeye, ssar, true));
        Follow f5 = followRepository.save(newFollow(ssar, terry, true));
        Follow f55 = followRepository.save(newFollow(ssar, wow, true));
        Follow f6 = followRepository.save(newFollow(cash, ssar, false));
        Follow f66 = followRepository.save(newFollow(power, ssar, false));
        // GameCode_Logo
        String etc_logo = "data:image/png;base64,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";
        String LoL_logo = "data:image/jpeg;base64,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";
        String starcraft_logo = "data:image/jpeg;base64,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";
        String battleground_logo = "data:image/png;base64,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";
        String overwatch_logo = "data:image/png;base64,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";
        String lostark_logo = "data:image/jpeg;base64,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";
        // GameCode : 게임카테고리
        GameCode etc = gameCodeRepository.save(newGameCode("etc", etc_logo));
        GameCode LoL = gameCodeRepository.save(newGameCode("LeagueOfLegend", LoL_logo));
        GameCode starcraft = gameCodeRepository.save(newGameCode("starcraft", starcraft_logo));
        GameCode battleground = gameCodeRepository.save(newGameCode("battleground", battleground_logo));
        GameCode overwatch = gameCodeRepository.save(newGameCode("overwatch", overwatch_logo));
        GameCode lostark = gameCodeRepository.save(newGameCode("lostark", lostark_logo));
        // Room : 파티방
        Room endroom1 = roomRepository.save(endRoom("솔랭듀오가실분~", ssar, LoL, 2L));
        Room room2 = roomRepository.save(newRoom("배그한겜고고 누구나 참여가능 오셈", ssar, battleground, 4L));
        Room room3 = roomRepository.save(newRoom("칼바람전사들 대환영 선착순!", cos, LoL, 5L));
        Room endroom4 = roomRepository.save(endRoom("최고의 명장면 주인공은 나야나 ~", lala, overwatch, 5L));
        Room room5 = roomRepository.save(newRoom("3:3 빨무 팀 사다리탈거임 아재환영", yeye, starcraft, 6L));
        Room room6 = roomRepository.save(newRoom("오늘 치킨 마려움 빨리오셈 ㄱㄱㄱ", ohoh, battleground, 4L));
        Room room7 = roomRepository.save(newRoom("골플 자랭 구함 대기 가능", vovo, LoL, 8L));
        Room room8 = roomRepository.save(newRoom("내전 우리팀 멤버 구함 상대 플다", terry, LoL, 5L));
        Room room9 = roomRepository.save(newRoom("일겜에서 신챔 연습하실? 내가 남는데 감", jeje, LoL, 5L));
        Room room10 = roomRepository.save(newRoom("원랜디 하실분 구해요~~~", jeje, starcraft, 5L));
        Room room11 = roomRepository.save(newRoom("파티구함", money, lostark, 4L));
        Room room12 = roomRepository.save(newRoom("요들 5인팟 가실분 오세용~", money, LoL, 5L));
        Room room13 = roomRepository.save(newRoom("저 정글 가르쳐주실분 참고로 브론즈임", money, LoL, 4L));
        Room room14 = roomRepository.save(newRoom("메르시 출동 다 모이셈", terry, battleground, 5L));
        Room room15 = roomRepository.save(newRoom("오버워치 꿀챔 꿀팁 알려주세요~", jeje, overwatch, 6L));
        Room room16 = roomRepository.save(newRoom("4:4 테란vs토스 하실분", terry, starcraft, 8L));
        Room room17 = roomRepository.save(newRoom("실골 듀오 구해요", wow, LoL, 2L));
        Room room18 = roomRepository.save(newRoom("실골 칼바람 내전 우리팀 멤버 미리 구해요", wow, LoL, 5L));
        Room room19 = roomRepository.save(newRoom("로아 꿀팁주실 선생님들 모십니다!", wow, lostark, 8L));
        Room room20 = roomRepository.save(newRoom("다이아 승격전 도와주실 정글러 구해여", cash, LoL, 2L));
        Room room21 = roomRepository.save(newRoom("니달리 연습 도와줄 실골 친구들 구해용", 아기사자, LoL, 5L));
        Room room22 = roomRepository.save(newRoom("미드 아우솔 원챔입니다 캐리가능 함께하실 플레 유저들 오셈", 둘리, LoL, 5L));
        Room room23 = roomRepository.save(newRoom("2:2 파이썬 같이해요", 키보드부순당, starcraft, 4L));
        Room room24 = roomRepository.save(newRoom("입구막기 알려드림 시키는대로 하셈 고고", power, starcraft, 7L));
        Room room25 = roomRepository.save(newRoom("3:3 빨무 중수이상 도장깨기하러가자~", 임요환짱, starcraft, 3L));
        Room room26 = roomRepository.save(newRoom("자랭하실분 너만오면고 ~", 페이커팬, LoL, 5L));
        Room room27 = roomRepository.save(newRoom("롤체 일반 덱 연습하자 ㄱㄱ", house, LoL, 8L));
        Room room28 = roomRepository.save(newRoom("탑 칼리스타 출동 ~ 내가 캐리해줌 오셈", 뜨뜨뜨뜨, LoL, 5L));
        Room room29 = roomRepository.save(newRoom("아이언 세계 탐험하실 용사들 구함", nero20, LoL, 5L));
        Room room30 = roomRepository.save(newRoom("봇듀 하실분~ 딜은 내가 함 탱서폿 구해요", 딜찍누, LoL, 2L));
        Room room31 = roomRepository.save(newRoom("레이드 뛰실 파티원 구해요 !", nero20, lostark, 4L));
        Room room32 = roomRepository.save(newRoom("제대로 작전 짜서 이길 용자들 오셈", poll, battleground, 4L));
        Room room33 = roomRepository.save(newRoom("이기는 법을 까먹은 동지들 이제 이길 때 됐음", 스피드레이서, LoL, 5L));
        Room room34 = roomRepository.save(newRoom("골드 자랭 5인팟 구함", poll, LoL, 5L));
        Room endroom35 = roomRepository.save(endRoom("듀오하실 다이아 정글러 모십니다", 탑솔러그자체, LoL, 2L));
        Room endroom36 = roomRepository.save(endRoom("연승 이어가는 실버 자랭팟 ~~ ", user22, LoL, 5L));
        Room room37 = roomRepository.save(newRoom("로아 입문합니다 팁 공유 부탁드려요 ~~~ ", ssar, lostark, 8L));
        // Enter : 방 참여 정보
        Enter enter1111 = enterRepository.save(endEnter(ssar, endroom1));
        Enter enter1 = enterRepository.save(endEnter(lala, endroom1));
        Enter enter11 = enterRepository.save(endEnter(dada, endroom1));
        Enter enter111 = enterRepository.save(endEnter(gogo, endroom1));
        Enter enter2 = enterRepository.save(newEnter(cos, room2));
        Enter enter22 = enterRepository.save(newEnter(kaka, room2));
        Enter enter222 = enterRepository.save(newEnter(romio, room2));
        Enter enter2222 = enterRepository.save(newEnter(ssar, room2));
        Enter enter3 = enterRepository.save(newEnter(ssar, room3));
        Enter enter33 = enterRepository.save(newEnter(toto, room3));
        Enter enter3333 = enterRepository.save(newEnter(money, room3));
        Enter enter33333 = enterRepository.save(newEnter(gogo, room3));
        Enter enter333333 = enterRepository.save(newEnter(cos, room3));
        Enter endEnter4 = enterRepository.save(endEnter(ssar, endroom4));
        Enter endEnter44 = enterRepository.save(endEnter(cos, endroom4));
        Enter endEnter444 = enterRepository.save(endEnter(yeye, endroom4));
        Enter endEnter4444 = enterRepository.save(endEnter(romio, endroom4));
        Enter endEnter44444 = enterRepository.save(endEnter(lala, endroom4));
        Enter enter5 = enterRepository.save(newEnter(gogo, room5));
        Enter enter55 = enterRepository.save(newEnter(cos, room5));
        Enter enter555 = enterRepository.save(newEnter(dada, room5));
        Enter enter5555 = enterRepository.save(newEnter(yeye, room5));
        Enter enter6 = enterRepository.save(newEnter(ssar, room6));
        Enter enter66 = enterRepository.save(newEnter(lala, room6));
        Enter enter666 = enterRepository.save(newEnter(romio, room6));
        Enter enter6666 = enterRepository.save(newEnter(house, room6));
        Enter enter66666 = enterRepository.save(newEnter(ohoh, room6));
        Enter endenter7 = enterRepository.save(endEnter(ssar, room7));
        Enter endenter77 = enterRepository.save(endEnter(power, room7));
        Enter endenter777 = enterRepository.save(endEnter(nero20, room7));
        Enter endenter7777 = enterRepository.save(endEnter(house, room7));
        Enter endenter77777 = enterRepository.save(endEnter(user23, room7));
        Enter endenter777777 = enterRepository.save(endEnter(vovo, room7));
        Enter enter8 = enterRepository.save(newEnter(terry, room8));
        Enter enter9 = enterRepository.save(newEnter(jeje, room9));
        Enter enter10 = enterRepository.save(newEnter(jeje, room10));
        Enter enters11 = enterRepository.save(newEnter(money, room11));
        Enter enters12 = enterRepository.save(newEnter(money, room12));
        Enter enters13 = enterRepository.save(newEnter(money, room13));
        Enter enters14 = enterRepository.save(newEnter(terry, room14));
        Enter enters15 = enterRepository.save(newEnter(jeje, room15));
        Enter enters16 = enterRepository.save(newEnter(terry, room16));
        Enter enters17 = enterRepository.save(newEnter(wow, room17));
        Enter enters18 = enterRepository.save(newEnter(wow, room18));
        Enter enters19 = enterRepository.save(newEnter(wow, room19));
        Enter enters20 = enterRepository.save(newEnter(cash, room20));
        Enter enters21 = enterRepository.save(newEnter(아기사자, room21));
        Enter enters22 = enterRepository.save(newEnter(둘리, room22));
        Enter enters23 = enterRepository.save(newEnter(키보드부순당, room23));
        Enter enters24 = enterRepository.save(newEnter(power, room24));
        Enter enters25 = enterRepository.save(newEnter(임요환짱, room25));
        Enter enters26 = enterRepository.save(newEnter(페이커팬, room26));
        Enter enters27 = enterRepository.save(newEnter(house, room27));
        Enter enters28 = enterRepository.save(newEnter(뜨뜨뜨뜨, room28));
        Enter enters29 = enterRepository.save(newEnter(nero20, room29));
        Enter enters30 = enterRepository.save(newEnter(딜찍누, room30));
        Enter enters31 = enterRepository.save(newEnter(nero20, room31));
        Enter enters32 = enterRepository.save(newEnter(poll, room32));
        Enter enters33 = enterRepository.save(newEnter(스피드레이서, room33));
        Enter enters34 = enterRepository.save(newEnter(poll, room34));
        Enter endenter35 = enterRepository.save(endEnter(탑솔러그자체, endroom35));
        Enter endenter355 = enterRepository.save(endEnter(money, endroom35));
        Enter endenter36 = enterRepository.save(endEnter(user22, endroom36));
        Enter endenter366 = enterRepository.save(endEnter(toto, endroom36));
        Enter endenter3666 = enterRepository.save(endEnter(nero20, endroom36));
        Enter endenter36666 = enterRepository.save(endEnter(dada, endroom36));
        Enter enter37 = enterRepository.save(newEnter(ssar, room37));
        Enter enter377 = enterRepository.save(newEnter(user22, room37));
        Enter enter3777 = enterRepository.save(newEnter(house, room37));
        Enter enter37777 = enterRepository.save(newEnter(toto, room37));
    }
}
