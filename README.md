### Guide

#### Deploy
1. pull repository to local
2. confirm that you have installed docker
3. docker-compose build
4. docker-compose up

#### Test Feature
1. open <a>localhost:3000</a> using browser.
2. click 'login with Google' Button.
3. select account and confirm.
4. if you didn't connect google analytics, the dialog will be opened, enter agree, incremental authetication will be triggered,  three charts will be displayed after you confirm it. if disagree, nothing will happened except the dialog is closed.
5. you can view snapshot.gif in current directory to get more infomation.

#### Design
1. There are two tables, user_info and google_user, the relationship between them is one to many. it's easy to add other third party providers signIn and signIn with email and password, if someone signIn with email or username, you only operate user_info table.
![Login](login.png)


