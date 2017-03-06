# Firebase_Real-Time_Map_example

Firebase를 이용하여 One-Server Multi-Client를 체험할 수 있는 예제입니다.

## 사용한 Firebase 서비스

**Authentication** : 구글 로그인 구현
**Real-Time Database** : 맵 마커 및 유저 정보 저장
**Storage** : Client에서 업로드한 이미지 저장
**Hosting** : 정적 웹 사이트 호스팅

> Hosting 폴더에 정적 웹사이트 파일들이 첨부되어 있습니다.

## Firebase Version

```{java}
com.google.firebase:firebase-core:10.0.1
com.google.firebase:firebase-auth:10.0.1
com.google.firebase:firebase-database:10.0.1
com.google.firebase:firebase-storage:10.0.1
```

## Firebase Database Rules

```{json}
{
  "rules": {
    ".read": "auth != null",
    ".write": "auth != null"
  }
}
```
최초 생성시의 기본 권한으로 인증받은 유저만 접근할 수 있습니다.

## 추가 사용된 라이브러리와 Version
```{java}
/* 구글 로그인 */
com.google.android.gms:play-services-auth:10.0.1
/* 네이버 지도 API */
com.naver.maps.open:naver-map-api:2.1.2@aar
```
