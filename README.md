# kafka-study-log

> 카프카 기본 개념에 대해 알아보고 직접 실습하며 기록을 남기는 공간입니다.

## 📌 [아파치 카프카 애플리케이션 프로그래밍 with 자바](https://product.kyobobook.co.kr/detail/S000001842177)

### kafka quick start
- 핵심 내용 : 카프카 브로커 설정 및 실행, 주키퍼 실행, 명령어 사용(topics, producer, consumer 등)
- [study log](https://obtainable-poppyseed-72e.notion.site/2-57ed435bbf414a609628c45c89bd4227?pvs=4)
- 트러블 슈팅 : 카프카 커맨드 라인 툴 실행 에러
<br>

|에러 화면|
|---|
|<img src="https://github.com/hiki-developer/kimgun95_kafka_project/assets/54833128/7d682e42-6f50-408e-b468-2c88982286a4" alt="에러1" width="750" height="35">|
|<img src="https://github.com/hiki-developer/kimgun95_kafka_project/assets/54833128/92c39893-0446-46e4-adee-6ea54ff022b9" alt="에러2" width="900" height="100">|

---
### kafka client
- 핵심 내용 : 프로듀서 API, 컨슈머 API, 어드민 API
- [study log](https://obtainable-poppyseed-72e.notion.site/3-4-c663edfb5c6f45a385acfc12d95228d2?pvs=4)
- 트러블 슈팅 : 로컬 윈도우 환경에서 kafka 서버 연결

---
### kafka streams
- 핵심 내용 : 스트림즈DSL(KTable, KStream 등), 프로세서 API
- [study log](https://obtainable-poppyseed-72e.notion.site/3-5-5932a49bbe3448d5b46d880831593e39?pvs=4)
- 트러블 슈팅 : 코파티셔닝 되지 않은 KTable과 KStream 토픽의 join
