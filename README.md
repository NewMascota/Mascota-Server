# Mascota Server

๐ Base URL : https://mascota.kr

</br>

## :octocat: api ๋ช์ธ


| Method | URI | Description | ๊ตฌํ์๋ฃ |
|:-------------:| -- | -- |:-------------:|
||:house_with_garden:**HOME**|
| GET | /homes/:userIdx | [ํ ์กฐํ](https://organic-manta-fd9.notion.site/1-3ba2d3438e7d48afba1782e8abddae25) |โ๏ธ|  
||:evergreen_tree:**์ค๋นํ๊ธฐ**|
| GET |/readies/questions/:userIdx/:petIdx| [์ค๋นํ๊ธฐ ์ ์ฒด ์ง๋ฌธ ์กฐํ](https://organic-manta-fd9.notion.site/1-77adc3648008451b84466cda6fdc62d1) |โ๏ธ| 
| GET | /readies/answers/:userIdx/:readyAnswerIdx | [์ค๋นํ๊ธฐ ๋ต๋ณ ์กฐํ](https://organic-manta-fd9.notion.site/1-93e864f5374f41e4ba8276d87db55463)  |โ๏ธ| 
| POST | /readies/answers/:userIdx/:petIdx/:readyQuestionIdx | [์ค๋นํ๊ธฐ ๋ต๋ณ ์์ฑ](https://organic-manta-fd9.notion.site/1-259c835ac26f447e91c30456d057f9f9)  |โ๏ธ| 
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx| [์ค๋นํ๊ธฐ ๋ต๋ณ ์์ ](https://organic-manta-fd9.notion.site/1-120f9198b9484d4d8c50723c60a1b5b6) |โ๏ธ|
| PATCH |/readies/answers/:userIdx/:readyAnswerIdx/status| [์ค๋นํ๊ธฐ ๋ต๋ณ ์ญ์ ](https://organic-manta-fd9.notion.site/1-d86432d0898d4ca49079c4ee978189e1) |โ๏ธ|
||:droplet:**์ถ์ตํ๊ธฐ**|
| GET | /memories/questions/unanswered/:userIdx/:petIdx | [์ถ์ตํ๊ธฐ ์ ์ฒด ์ง๋ฌธ ์กฐํ (๋ต๋ณํ๊ธฐ ํญ)](https://organic-manta-fd9.notion.site/1-a5b359e04cc94a8ca98eb2f0f7653598) |โ๏ธ|
| GET |/memories/questions/answered/:userIdx/:petIdx| [์ถ์ตํ๊ธฐ ์ ์ฒด ์ง๋ฌธ ์กฐํ (๋ชจ์๋ณด๊ธฐ ํญ)](https://organic-manta-fd9.notion.site/1-f79b0dbae5d249c69467d14111184eee) |โ๏ธ| 
| GET |/memories/answers/:userIdx/:memoryAnswerIdx| [์ถ์ตํ๊ธฐ ๋ต๋ณ ์กฐํ](https://organic-manta-fd9.notion.site/1-910bdd9bbac74c1e8145625233d492b3) |โ๏ธ|
| POST | /memories/one/:userIdx/:petIdx/:memoryQuestionIdx | [์ถ์ตํ๊ธฐ ๋ต๋ณ ์์ฑ](https://organic-manta-fd9.notion.site/1-9f0b9cf3a472483094dbdeacbbffbf4b) |โ๏ธ| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx| [์ถ์ตํ๊ธฐ ๋ต๋ณ ์์ ](https://organic-manta-fd9.notion.site/1-b956d0b7582d41298ab675a16e484eb5) |โ๏ธ| 
| PATCH |/memories/answers/:userIdx/:memoryAnswerIdx/status| [์ถ์ตํ๊ธฐ ๋ต๋ณ ์ญ์ ](https://organic-manta-fd9.notion.site/1-510d2da65e384e87836ff6b5407a2905) |โ๏ธ| 
||:closed_lock_with_key:**๋ง์ดํ์ด์ง**|
| GET | /myPages/:userIdx | [๋ง์ดํ์ด์ง ์ ์ฒด ์กฐํ](https://organic-manta-fd9.notion.site/1-076eda2078b244178dd88c2aa2688761) |โ๏ธ|
| GET | /myPages/myInfo/:userIdx | [๋ง์ดํ์ด์ง ๊ฐ์ธ์ ๋ณด ์กฐํ](https://organic-manta-fd9.notion.site/1-9e928c30b0dc4e7f83a448773e1c674e) |โ๏ธ| 
| PATCH | /myPages/bookInfo/:userIdx | [๋ง์ดํ์ด์ง ์ฑ ํ์ง ์์ ](https://organic-manta-fd9.notion.site/1-5774eaf92e44454bb419d76e4d0c1c5e) |โ๏ธ| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx | [๋ง์ดํ์ด์ง ๋ฐ๋ ค๋๋ฌผ ํ๋กํ ์์ ](https://organic-manta-fd9.notion.site/1-fcb72ca892bb4a0f98a00e5703b123e0) |โ๏ธ| 
| PATCH | /myPages/petInfo/:userIdx/:petIdx/status | [๋ง์ดํ์ด์ง ๋ฐ๋ ค๋๋ฌผ ํ๋กํ ์ญ์ ](https://organic-manta-fd9.notion.site/1-5722d9316063480ab02d65503dd0c79e) |โ๏ธ| 
| PATCH | /mypages/user-info/password/:userIdx | [๋ง์ดํ์ด์ง ๋น๋ฐ๋ฒํธ ๋ณ๊ฒฝ](https://organic-manta-fd9.notion.site/1-91b917b8111f445499509145c8273664) |โ๏ธ| 
||:date:**์บ๋ฆฐ๋**|
| GET | /calendar/user-moods/:userId/:diaryIdx | ์ ์  ๊ธฐ๋ถ ์บ๋ฆฐ๋ ์กฐํ | | 
| GET |/calendar/pet-moods/:userId/:diaryIdx/:petIdx | ๋ฐ๋ ค๋๋ฌผ ๊ธฐ๋ถ ์บ๋ฆฐ๋ ์กฐํ | | 
