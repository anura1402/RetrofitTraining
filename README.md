# <a name="AppName"></a> RetrofitTraining
Приложение для отработки навыков работы с API с помощью Retrofit и OkHttp. Все общение было с помощью сайта https://dummyjson.com/
Это приложение не следует чистой архитектуре и было разработано для практики с API.
## Content
- [Используемые технологии и библиотеки](#stack)
- [Описание](#description)
- [Скриншоты](#screenshots)
- [Экран авторизации](#auth)
- [Экран с продуктами](#products)

## <a name="description"></a> Описание
Это приложение не следует чистой архитектуре и было разработано для практики с API.
У приложения 2 фрагмента: 1 - авторизация, 2 - показ и поиск продуктов с доступом только для авторизованных пользователей(проверка токена)

## <a name="stack"></a> Используемые технологии и библиотеки
При разработке приложения использовались:
- Retrofit, OkHttp для API
- RecyclerView

## <a name="screenshots"></a> Screenshots
### <a name="auth"></a> Экран авторизации
*При входе в приложение предоставляется форма для авторизации:*

<img src="https://github.com/user-attachments/assets/907da14c-968e-4f51-996a-1443c0d92f9c" width="512">

*При неправильном вводе показывается соответсвующее сообщение об ошибке:*

<img src="https://github.com/user-attachments/assets/7cc4b3cb-ea8b-4886-8714-70c6c35406da" width="512">

*При правильном вводе загружается имя и аватарка:*

<img src="https://github.com/user-attachments/assets/b98b6e72-9389-4ec2-ad31-d045c4a3a8da" width="512">

### <a name="products"></a> Экран с продуктами
*При входе в приложение отображается список продуктов и SearchView:*

<img src="https://github.com/user-attachments/assets/7cb67284-d2e5-4363-92a4-b5cdd9e825ce" width="512">

*Поиск в SearchView:*

<img src="https://github.com/user-attachments/assets/21fa99bd-80ef-4f0b-8a63-1d706361696c" width="512">
